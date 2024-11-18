package org.example.fx_forint.config;

import com.oanda.v20.account.AccountID;
import org.example.fx_forint.models.Params;

public class AppConfig {

    /*MNB*/
    private static String savedMNBFilePath;
    public static String getSavedMNBFilePath() {
        return savedMNBFilePath;
    }
    public static void setSavedMNBFilePath(String savedMNBFilePath) {
        AppConfig.savedMNBFilePath = savedMNBFilePath;
    }

    /*Forex OANDA*/
    public static final String FOREX_API_URL = "https://api-fxpractice.oanda.com";
    public static final String FOREX_API_TOKEN = "2fa4230ddce6e98c07ae73bbeff59c49-8c302df73327ed59bde384a7c9dcb847";
    public static final AccountID FOREX_API_ACCOUNTID = new AccountID("101-004-30380107-001");

}
