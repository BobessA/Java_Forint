package org.example.fx_forint.controller.forex;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.models.Forex.TradeData;
import org.example.fx_forint.controller.forex.ActiveTradeService;

public class ActiveTradesController {

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
    private void loadActiveTradesButtonClicked(){
        loadActiveTrades();
    }
    private void loadActiveTrades() {
        ObservableList<TradeData> activeTrades = tradeService.loadActiveTrades();
        activeTradesTable.setItems(activeTrades);
    }
}
