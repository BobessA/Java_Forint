package org.example.fx_forint.controller.forex;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.Context;

public class accountController {

    @FXML
    private GridPane accountGrid;

    @FXML
    private void initialize() {
        try {
            // OANDA API Kapcsolat
            Context ctx = new Context(
                    "https://api-fxpractice.oanda.com",
                    "2fa4230ddce6e98c07ae73bbeff59c49-8c302df73327ed59bde384a7c9dcb847"
            );
            AccountSummary summary = ctx.account.summary(
                    new AccountID("101-004-30380107-001")
            ).getAccount();

            // Az adatok hozzáadása a GridPane-hez
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

        accountGrid.add(labelNode, 0, row); // 1. oszlop: Label
        accountGrid.add(valueNode, 1, row); // 2. oszlop: Érték
    }
}
