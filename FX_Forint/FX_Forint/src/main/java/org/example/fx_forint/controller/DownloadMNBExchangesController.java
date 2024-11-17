package org.example.fx_forint.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.fx_forint.helper.MNBSoapClientHelper;
import org.example.fx_forint.config.AppConfig;
import org.example.fx_forint.models.Params;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.example.fx_forint.helper.Utils.showMessage;

public class DownloadMNBExchangesController {

    private Stage stage;
    private MNBSoapClientHelper soapHelper;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label resultText;

    @FXML
    private void saveMNBDataToFile() {

        soapHelper = new MNBSoapClientHelper();
        System.out.println(soapHelper.getCurrentExchangeRates());

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialFileName("MNB.txt");

        File file = fileChooser.showSaveDialog(stage);
        try {
            if (file != null) {
                AppConfig.setSavedMNBFilePath(file.getAbsolutePath());
                Params param = new Params("savedMNBFilePath",AppConfig.getSavedMNBFilePath());
                Params.saveParam(param);

                saveExchangesToFile(file);
                resultText.setText("A mentés sikeres volt, a fájl elérhető a\n"+file.getAbsolutePath()+"\ncímen");
            }
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "A fájl mentése sikertelen.","Hiba");
        }


    }

    private void saveExchangesToFile(File file) {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            writer.write(soapHelper.getCurrentExchangeRates());
            System.out.println("Árfolyamok mentve a fájlba: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Hiba a fájl mentésekor: " + e.getMessage());
        }
    }
}