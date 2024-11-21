package org.example.fx_forint.controller.forex;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountInstrumentsRequest;
import com.oanda.v20.account.AccountInstrumentsResponse;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.config.AppConfig;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.oanda.v20.instrument.CandlestickGranularity.H1;

public class HistoricPrices {

    @FXML
    private TableView<CandleData> candleTable;

    @FXML
    private TableColumn<CandleData, String> timeColumn;

    @FXML
    private TableColumn<CandleData, String> closeColumn;

    @FXML
    private ComboBox<String> instrumentComboBox; // ComboBox hozzáadása

    private ObservableList<String> instruments;

    private Context ctx;

    public static class CandleData {
        private final String time;
        private final String closePrice;

        public CandleData(String time, String closePrice) {
            this.time = time;
            this.closePrice = closePrice;
        }

        public String getTime() {
            return time;
        }

        public String getClosePrice() {
            return closePrice;
        }
    }

    @FXML
    public void initialize() {
        try {
            ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                    .setToken(AppConfig.FOREX_API_TOKEN)
                    .setApplication("HistorikusAdatok")
                    .build();

            AccountInstrumentsRequest instrumentsRequest = new AccountInstrumentsRequest(AppConfig.FOREX_API_ACCOUNTID);
            AccountInstrumentsResponse instrumentsResponse = ctx.account.instruments(instrumentsRequest);

            instruments = FXCollections.observableArrayList(
                    instrumentsResponse.getInstruments()
                            .stream()
                            .map(instrument -> instrument.getName().toString())
                            .collect(Collectors.toList())
            );

            instrumentComboBox.setItems(instruments);
            instrumentComboBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLoadHistoricPricesButtonClicked() {
        try {

            String selectedInstrument = instrumentComboBox.getValue();
            if (selectedInstrument == null) {
                return;
            }

            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(selectedInstrument));
            request.setGranularity(H1);
            request.setCount(10L);

            InstrumentCandlesResponse resp = ctx.instrument.candles(request);

            ObservableList<CandleData> data = FXCollections.observableArrayList();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Candlestick candle : resp.getCandles()) {
                String formattedTime = OffsetDateTime.parse(candle.getTime().toString()).format(formatter);
                data.add(new CandleData(formattedTime, candle.getMid().getC().toString()));
            }

            candleTable.setItems(data);

            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            closeColumn.setCellValueFactory(new PropertyValueFactory<>("closePrice"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
