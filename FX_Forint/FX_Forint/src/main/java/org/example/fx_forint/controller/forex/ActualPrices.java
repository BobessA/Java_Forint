package org.example.fx_forint.controller.forex;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountInstrumentsRequest;
import com.oanda.v20.account.AccountInstrumentsResponse;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.config.AppConfig;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.stream.Collectors;

public class ActualPrices {
    @FXML
    private TableView<PriceData> priceTable;
    @FXML
    private TableColumn<PriceData, String> instrumentColumn;
    @FXML
    private TableColumn<PriceData, Double> bidColumn;
    @FXML
    private TableColumn<PriceData, Double> askColumn;
    @FXML
    private TextField searchField;

    private final ObservableList<PriceData> prices = FXCollections.observableArrayList();
    private FilteredList<PriceData> filteredPrices;
    @FXML
    public void initialize() {
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        bidColumn.setCellValueFactory(new PropertyValueFactory<>("bidPrice"));
        askColumn.setCellValueFactory(new PropertyValueFactory<>("askPrice"));

        filteredPrices = new FilteredList<>(prices, p -> true);
        priceTable.setItems(filteredPrices);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPrices.setPredicate(priceData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                return priceData.getInstrument().toLowerCase().contains(newValue.toLowerCase());
            });
        });
    }

    @FXML
    protected void onLoadActualPricesButtonClicked() {
        try {
            Context ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                    .setToken(AppConfig.FOREX_API_TOKEN)
                    .setApplication("PricePolling")
                    .build();
            AccountID accountId = AppConfig.FOREX_API_ACCOUNTID;

            AccountInstrumentsRequest instrumentsRequest = new AccountInstrumentsRequest(accountId);
            AccountInstrumentsResponse instrumentsResponse = ctx.account.instruments(instrumentsRequest);
            List<String> instruments = instrumentsResponse.getInstruments()
                    .stream()
                    .map(instrument -> instrument.getName().toString())
                    .collect(Collectors.toList());

            PricingGetRequest pricingRequest = new PricingGetRequest(accountId, instruments);
            PricingGetResponse pricingResponse = ctx.pricing.get(pricingRequest);

            prices.clear();
            for (ClientPrice price : pricingResponse.getPrices()) {
                prices.add(new PriceData(
                        price.getInstrument().toString(),
                        price.getBids().get(0).getPrice().doubleValue(),
                        price.getAsks().get(0).getPrice().doubleValue()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PriceData {
        private final String instrument;
        private final Double bidPrice;
        private final Double askPrice;

        public PriceData(String instrument, Double bidPrice, Double askPrice) {
            this.instrument = instrument;
            this.bidPrice = bidPrice;
            this.askPrice = askPrice;
        }

        public String getInstrument() {
            return instrument;
        }

        public Double getBidPrice() {
            return bidPrice;
        }

        public Double getAskPrice() {
            return askPrice;
        }
    }
}
