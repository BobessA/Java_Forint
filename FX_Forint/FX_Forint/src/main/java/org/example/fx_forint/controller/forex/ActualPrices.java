package org.example.fx_forint.controller.forex;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.Context;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.config.AppConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActualPrices {
    @FXML
    private TableView<PriceData> priceTable;
    @FXML
    private TableColumn<PriceData, String> instrumentColumn;
    @FXML
    private TableColumn<PriceData, Double> bidColumn;
    @FXML
    private TableColumn<PriceData, Double> askColumn;

    private final ObservableList<PriceData> prices = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Oszlopok összekapcsolása az adatokkal
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        bidColumn.setCellValueFactory(new PropertyValueFactory<>("bidPrice"));
        askColumn.setCellValueFactory(new PropertyValueFactory<>("askPrice"));

        // Adatok hozzáadása a táblázathoz
        priceTable.setItems(prices);
    }

    @FXML
    protected void onHelloButtonClick() {
        try {
            // OANDA API kapcsolódás
            Context ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                    .setToken(AppConfig.FOREX_API_TOKEN)
                    .setApplication("PricePolling")
                    .build();
            AccountID accountId = AppConfig.FOREX_API_ACCOUNTID;

            // Instrumentumok listája
            List<String> instruments = new ArrayList<>(Arrays.asList("EUR_USD", "USD_JPY", "GBP_USD", "USD_CHF"));
            PricingGetRequest request = new PricingGetRequest(accountId, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);

            // Táblázat frissítése
            prices.clear(); // Régi adatok törlése
            for (ClientPrice price : resp.getPrices()) {
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

    // Belső osztály az adatok reprezentálására
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
