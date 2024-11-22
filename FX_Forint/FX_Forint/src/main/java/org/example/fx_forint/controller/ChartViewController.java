package org.example.fx_forint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import org.example.fx_forint.helper.MNBSoapClientHelper;
import org.example.fx_forint.models.DevizaChartItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChartViewController {

    @FXML
    private ComboBox<String> currencyComboBox;

    @FXML
    private TextField startDateField;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private MNBSoapClientHelper soapHelper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        soapHelper = new MNBSoapClientHelper();

        // Példa devizák listája (dinamikusan töltheted be a listát is)
        currencyComboBox.getItems().addAll("EUR", "USD", "GBP", "JPY", "HUF");
        currencyComboBox.getSelectionModel().select("EUR"); // Alapértelmezett érték
        startDateField.setText("2024-10");
        onDisplayChart();
    }

    @FXML
    private void onDisplayChart() {
        String selectedCurrency = currencyComboBox.getValue();
        String month = startDateField.getText();

        if (selectedCurrency == null || month.isEmpty()) {
            showError("Kérjük, válasszon devizát és adjon meg egy kezdő dátumot!");
            return;
        }

        try {
            String startDate = month + "-01";
            LocalDate startLocalDate = LocalDate.parse(startDate, FORMATTER);
            LocalDate endLocalDate = startLocalDate.withDayOfMonth(startLocalDate.lengthOfMonth());

            String endDate = endLocalDate.format(FORMATTER);
            String response = soapHelper.getExchangeRatesByDateAndCurrency(startDate,endDate, selectedCurrency);
            List<DevizaChartItem> devizaList = MNBSoapClientHelper.formatChartData(response);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(selectedCurrency);

            for (DevizaChartItem item : devizaList) {
                Double rate = item.getDeviza().getArfolyam();
                series.getData().add(new XYChart.Data<>(item.getDate(), rate));
            }
            ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList(series);

            lineChart.getData().clear();
            lineChart.setData(chartData);

        } catch (Exception e) {
            showError("Hiba történt az adatok lekérdezésekor: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
