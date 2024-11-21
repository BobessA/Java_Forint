package org.example.fx_forint.controller.forex;

import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.models.Forex.TradeData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.fx_forint.config.AppConfig;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.Context;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import org.example.fx_forint.controller.forex.ActiveTradeService;


public class OpenTradeController {

    @FXML
    private TextField instrumentField;

    @FXML
    private TextField unitsField;
    @FXML
    private TableView<TradeData> activeTradesTable;
    @FXML
    private TableColumn<TradeData, String> idColumn;
    @FXML
    private TableColumn<TradeData, String> instrumentColumn;
    @FXML
    private TableColumn<TradeData, String> openTimeColumn;
    @FXML
    private TableColumn<TradeData, String> unitsColumn;
    @FXML
    private TableColumn<TradeData, String> priceColumn;
    @FXML
    private TableColumn<TradeData, String> plColumn;

    private final ActiveTradeService tradeService = new ActiveTradeService();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        openTimeColumn.setCellValueFactory(new PropertyValueFactory<>("openTime"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        plColumn.setCellValueFactory(new PropertyValueFactory<>("pl"));
        loadActiveTrades();
    }

    @FXML
    private void onOpenPositionButtonClicked() {
        try {
            Context ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                    .setToken(AppConfig.FOREX_API_TOKEN)
                    .setApplication("StepByStepOrder")
                    .build();

            String instrument = instrumentField.getText();
            String units = unitsField.getText();

            MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
            marketOrderRequest.setInstrument(new com.oanda.v20.primitives.InstrumentName(instrument));
            marketOrderRequest.setUnits(Double.parseDouble(units));
            OrderCreateRequest request = new OrderCreateRequest(AppConfig.FOREX_API_ACCOUNTID);
            request.setOrder(marketOrderRequest);

            ctx.order.create(request);
            loadActiveTrades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadActiveTrades() {
        ObservableList<TradeData> activeTrades = tradeService.loadActiveTrades();
        activeTradesTable.setItems(activeTrades);
    }
}
