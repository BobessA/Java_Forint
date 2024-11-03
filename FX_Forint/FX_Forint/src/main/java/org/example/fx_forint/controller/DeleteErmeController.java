package org.example.fx_forint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.fx_forint.helper.databaseHelper;
import org.example.fx_forint.models.Erme;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.fx_forint.helper.Utils.showMessage;
import static org.example.fx_forint.helper.databaseHelper.executeSql;

public class DeleteErmeController {

    @FXML
    private ComboBox cbErmeid;
    @FXML
    private TextField tfConfirm;

    @FXML
    private void initialize() {
        loadCbItems();
        cbErmeid.setVisibleRowCount(5);
    }

    @FXML
    private void deleteErme() {

        if (cbErmeid.getValue() == null) {
            showMessage(Alert.AlertType.ERROR, "Az azonosító kiválasztása kötelező.","Hibás érték");
            return;
        }

        if (!tfConfirm.getText().equals("Érme Törlése!")) {
            showMessage(Alert.AlertType.ERROR, "Hibás biztonsági szöveg miatt, a törlés sikertelen.", "Hibás adat");
            return;
        }

        Integer ermeId = Integer.parseInt(cbErmeid.getValue().toString());
        String sql = "Delete from Erme where ermeid="+ermeId;
        executeSql(sql);
        cbErmeid.setValue(null);
        tfConfirm.clear();
    }

    private void loadCbItems() {
        List<Erme> details = databaseHelper.getRecords("Select * from Erme", Erme.class);

        List<Integer> ermeids = details.stream().map(Erme::getErmeid).collect(Collectors.toList());
        ObservableList<Integer> observableErmeids = FXCollections.observableArrayList(ermeids);
        cbErmeid.setItems(observableErmeids);
    }

}
