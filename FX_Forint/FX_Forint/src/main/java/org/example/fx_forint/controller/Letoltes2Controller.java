package org.example.fx_forint.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.example.fx_forint.helper.MNBSoapClientHelper;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Letoltes2Controller {

    @FXML
    private TextField yearMonthField;

    @FXML
    private ListView<String> currencyListView;

    @FXML
    private Label resultText;

    private MNBSoapClientHelper soapHelper;

    @FXML
    public void initialize() {
        soapHelper = new MNBSoapClientHelper();

        // Devizák betöltése az oldal indításakor
        try {
            List<String> allCurrencies = soapHelper.getAvailableCurrencies();
            currencyListView.setItems(FXCollections.observableArrayList(allCurrencies));
            currencyListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        } catch (Exception e) {
            showError("Hiba a devizák betöltésekor: " + e.getMessage());
        }

        Tooltip tableTooltip = new Tooltip("CTRL billentyűt nyomva tartva több sor is kijelölhető.");
        currencyListView.setTooltip(tableTooltip);
    }

    @FXML
    private void onDownloadAndSave() {
        String yearMonth = yearMonthField.getText();
        List<String> selectedCurrencies = currencyListView.getSelectionModel().getSelectedItems();

        if (yearMonth.isEmpty()) {
            showError("Kérjük, adjon meg egy dátumot (YYYY-MM formátumban)!");
            return;
        }

        if (selectedCurrencies.isEmpty()) {
            showError("Kérjük, válasszon legalább egy devizát!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialFileName("MNB_Monthly_Rates.txt");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                saveSelectedDataToFile(file, yearMonth, selectedCurrencies);
                resultText.setText("Sikeres letöltés és mentés:\n" + file.getAbsolutePath());
            } catch (Exception e) {
                showError("Hiba történt az adatok mentése során.");
                e.printStackTrace();
            }
        }
    }

    private void saveSelectedDataToFile(File file, String yearMonth, List<String> currencies) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            for (String currency : currencies) {
                String startDate = yearMonth + "-01";
                String endDate = yearMonth + "-31";
                String response = soapHelper.getExchangeRatesByDateAndCurrency(startDate, endDate, currency);

                String formattedResponse = MNBSoapClientHelper.formatExchangeResult(response);
                writer.write("Valuta: " + currency + "\n");
                writer.write(formattedResponse);
                writer.write("\n\n");
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
