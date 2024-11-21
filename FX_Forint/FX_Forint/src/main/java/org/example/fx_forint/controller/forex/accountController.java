package org.example.fx_forint.controller.forex;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.Context;
import org.example.fx_forint.config.AppConfig;

public class accountController {

    @FXML
    private GridPane accountGrid;

    @FXML
    private void initialize() {
        try {
            Context ctx = new Context(
                    AppConfig.FOREX_API_URL,
                    AppConfig.FOREX_API_TOKEN
            );
            AccountSummary summary = ctx.account.summary(
                    new AccountID(AppConfig.FOREX_API_ACCOUNTID)
            ).getAccount();

            int row = 0;

            addRow("ID", summary.getId().toString(), row++);
            addRow("Alias", summary.getAlias(), row++);
            addRow("Pénznem", summary.getCurrency().toString(), row++);
            addRow("Egyenleg", summary.getBalance().toString(), row++);
            addRow("NAV", summary.getNAV().toString(), row++);
            addRow("Kereskedések száma", String.valueOf(summary.getOpenTradeCount()), row++);
            addRow("Hedging Engedélyezve", String.valueOf(summary.getHedgingEnabled()), row++);
            addRow("Pozíciók száma", String.valueOf(summary.getOpenPositionCount()), row++);
            addRow("Garantált Stop Loss Mód", summary.getGuaranteedStopLossOrderMode().toString(), row++);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRow(String label, String value, int row) {
        Label labelNode = new Label(label + ":");
        labelNode.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
        Label valueNode = new Label(value);
        valueNode.setStyle("-fx-padding: 5;");

        accountGrid.add(labelNode, 0, row);
        accountGrid.add(valueNode, 1, row); 
    }
}
