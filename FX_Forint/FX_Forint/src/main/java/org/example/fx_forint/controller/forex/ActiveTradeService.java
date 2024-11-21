package org.example.fx_forint.controller.forex;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.trade.Trade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.fx_forint.config.AppConfig;
import org.example.fx_forint.models.Forex.TradeData;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ActiveTradeService {

    public ObservableList<TradeData> loadActiveTrades() {
        ObservableList<TradeData> activeTrades = FXCollections.observableArrayList();
        try {
            Context ctx = new ContextBuilder(AppConfig.FOREX_API_URL)
                    .setToken(AppConfig.FOREX_API_TOKEN)
                    .setApplication("ActiveTradesApp")
                    .build();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (Trade trade : ctx.trade.listOpen(AppConfig.FOREX_API_ACCOUNTID).getTrades()) {
                String formattedTime = OffsetDateTime.parse(trade.getOpenTime().toString()).format(formatter);

                activeTrades.add(new TradeData(
                        String.valueOf(trade.getId()),
                        trade.getInstrument().toString(),
                        formattedTime,
                        trade.getCurrentUnits().toString(),
                        trade.getPrice().toString(),
                        trade.getUnrealizedPL().toString()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activeTrades;
    }
}
