package org.example.fx_forint.controller.forex;

import org.example.fx_forint.models.Forex.TradeData;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.config.AppConfig;
import com.oanda.v20.Context;
import org.example.fx_forint.controller.forex.ActiveTradeService;



public class CloseTradeController {
    @FXML
    private TextField tradeIdField;

    @FXML
    private Button closeTradeButton;
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
    private void onCloseTradeButtonClick() {
        Context ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                .setToken(AppConfig.FOREX_API_TOKEN)
                .setApplication("CloseTradeApp")
                .build();


        String tradeId = tradeIdField.getText();

        if (tradeId == null || tradeId.isEmpty()) {
            /* Hiba IDE*/
            return;
        }

        try {
            ctx.trade.close(new TradeCloseRequest(AppConfig.FOREX_API_ACCOUNTID, new TradeSpecifier(tradeId)));
            /* Success szöveg IDE*/

            loadActiveTrades();
        } catch (Exception e) {
            /* Hiba IDE*/

        }
    }

    private void loadActiveTrades() {
        ObservableList<TradeData> activeTrades = tradeService.loadActiveTrades();
        activeTradesTable.setItems(activeTrades);
    }

}
