package org.example.fx_forint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.example.fx_forint.helper.databaseHelper;
import org.example.fx_forint.models.Erme;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.fx_forint.helper.Utils.*;
import static org.example.fx_forint.helper.Utils.showMessage;
import static org.example.fx_forint.helper.databaseHelper.executeSql;

public class ModifiedErmeController {

    @FXML
    private TextField tfCimlet;
    @FXML
    private TextField tfDarab;
    @FXML
    private TextField tfTomeg;
    @FXML
    private DatePicker dpKezd;
    @FXML
    private DatePicker dpBevonas;
    @FXML
    private ComboBox cbErmeid;

    @FXML
    private TableView<Erme> ermeTable;
    @FXML
    private TableColumn<Erme, Integer> ermeid;
    @FXML
    private TableColumn<Erme, String> cimlet;
    @FXML
    private TableColumn<Erme, Double> tomeg;
    @FXML
    private TableColumn<Erme, Integer> darab;
    @FXML
    private TableColumn<Erme, Date> kiadas;
    @FXML
    private TableColumn<Erme, Date> bevonas;

    public void initialize() {
        loadErme("Select * from Erme");
        cbErmeid.setVisibleRowCount(5);
        dpKezd.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> event.consume());
        dpBevonas.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> event.consume());

        Tooltip tableTooltip = new Tooltip("Dupla kattintásra kiválasztható a szerkesztendő adatsor.");
        ermeTable.setTooltip(tableTooltip);

        ermeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Erme selectedErme = ermeTable.getSelectionModel().getSelectedItem();
                if (selectedErme != null) {
                    loadErmeDetailsToForm(selectedErme);
                }
            }
        });
    }

    @FXML
    private void modifiedErme() {

        if (cbErmeid.getValue() == null) {
            showMessage(Alert.AlertType.ERROR, "Az azonosító kiválasztása kötelező.","Hibás érték");
            return;
        }

        if (!isInteger(tfCimlet.getText())) {
            showMessage(Alert.AlertType.ERROR, "A Címlet mező értéke üres, vagy nem megfelelő.","Hibás érték");
            return;
        }

        if (!isInteger(tfDarab.getText())) {
            showMessage(Alert.AlertType.ERROR,"A Darab mező értéke üres, vagy nem megfelelő.","Hibás érték");
            return;
        }

        if (!isDouble(tfTomeg.getText())) {
            showMessage(Alert.AlertType.ERROR,"A Tömeg mező értéke üres, vagy nem megfelelő.","Hibás érték");
            return;
        }

        if (dpKezd.getValue() == null) {
            showMessage(Alert.AlertType.ERROR,"A Kiadás kezdete üres.", "Hibás érték");
            return;
        }

        Erme modErme = new Erme(Integer.parseInt(
                tfCimlet.getText()),
                Double.parseDouble((tfTomeg.getText()).replace(",",".")),
                Integer.parseInt(tfDarab.getText()),
                dpKezd.getValue(),
                (dpBevonas.getValue() != null ? dpBevonas.getValue() : null)
        );
        Integer ermeId = Integer.parseInt(cbErmeid.getValue().toString());
        String bevonas = modErme.getBevonas() == null ? " null " : "'" + modErme.getBevonas() + "'";

        String sql = "Update Erme set " +
                " cimlet = " + modErme.getCimlet() + "," +
                " tomeg = " + modErme.getTomeg() + "," +
                " darab = " + modErme.getDarab() + "," +
                " kiadas = '" + modErme.getKiadas() + "'," +
                " bevonas = " + bevonas + " " +
                " where ermeid="+ermeId;

        executeSql(sql);
        controlsReset();
        loadErme("Select * from Erme");
    }

    @FXML
    private void loadErme(String sql) {

        ermeTable.getItems().clear();

        ermeid.setCellValueFactory(new PropertyValueFactory<>("ermeid"));
        cimlet.setCellValueFactory(new PropertyValueFactory<>("cimlet"));
        tomeg.setCellValueFactory(new PropertyValueFactory<>("tomeg"));
        darab.setCellValueFactory(new PropertyValueFactory<>("darab"));
        kiadas.setCellValueFactory(new PropertyValueFactory<>("kiadas"));
        bevonas.setCellValueFactory(new PropertyValueFactory<>("bevonas"));

        List<Erme> details = databaseHelper.getRecords(sql, Erme.class);

        ObservableList<Erme> ermeList = FXCollections.observableList(details);
        ermeTable.setItems(ermeList);

        List<Integer> ermeIds = details.stream().map(Erme::getErmeid).collect(Collectors.toList());
        ObservableList<Integer> observableErmeids = FXCollections.observableArrayList(ermeIds);
        cbErmeid.setItems(observableErmeids);
    }

    private void loadErmeDetailsToForm(Erme erme) {
        cbErmeid.setValue(erme.getErmeid());
        tfCimlet.setText(String.valueOf(erme.getCimlet()));
        tfTomeg.setText(String.valueOf(erme.getTomeg()));
        tfDarab.setText(String.valueOf(erme.getDarab()));
        dpKezd.setValue(erme.getKiadas());
        dpBevonas.setValue(erme.getBevonas());
    }

    private void controlsReset() {
        tfCimlet.clear();
        tfDarab.clear();
        tfTomeg.clear();
        dpKezd.setValue(null);
        dpBevonas.setValue(null);
        cbErmeid.setValue(null);
    }

}
