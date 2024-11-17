package org.example.fx_forint.config;

import org.example.fx_forint.models.Params;

public class AppConfig {

    private static String savedMNBFilePath;

    public static String getSavedMNBFilePath() {
        return savedMNBFilePath;
    }

    public static void setSavedMNBFilePath(String savedMNBFilePath) {
        AppConfig.savedMNBFilePath = savedMNBFilePath;
    }

}
