package org.example.fx_forint.controller.forex;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.Context;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.config.AppConfig;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import static com.oanda.v20.instrument.CandlestickGranularity.H1;

public class HistoricPrices {

    @FXML
    private TableView<CandleData> candleTable;

    @FXML
    private TableColumn<CandleData, String> timeColumn;

    @FXML
    private TableColumn<CandleData, String> closeColumn;

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
    protected void onLoadHistoricPricesButtonClicked() {
        try {
            Context ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                    .setToken(AppConfig.FOREX_API_TOKEN)
                    .setApplication("HistorikusAdatok")
                    .build();

            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName("EUR_USD"));
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
