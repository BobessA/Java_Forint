package org.example.fx_forint.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.example.fx_forint.models.Erme;

import static org.example.fx_forint.helper.Utils.*;
import static org.example.fx_forint.helper.databaseHelper.executeSql;

public class NewErmeController {

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
    private void initialize() {
        dpKezd.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> event.consume());
        dpBevonas.getEditor().addEventFilter(KeyEvent.KEY_TYPED, event -> event.consume());
    }

    @FXML
    private void addErme() {

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

        Erme newErme = new Erme(Integer.parseInt(
                tfCimlet.getText()),
                Double.parseDouble((tfTomeg.getText()).replace(",",".")),
                Integer.parseInt(tfDarab.getText()),
                dpKezd.getValue(),
                (dpBevonas.getValue() != null ? dpBevonas.getValue() : null)
        );

        String sql = "Insert into Erme (cimlet,tomeg,darab,kiadas,bevonas)" +
                "Select "+ newErme.getCimlet() +",Round("+newErme.getTomeg()+",2),"+newErme.getDarab()+",'"+newErme.getKiadas()+"','"+newErme.getBevonas()+"'";

        executeSql(sql);
        showMessage(Alert.AlertType.INFORMATION,"Az adat rögzítése sikeres volt.","Rögzítve");
        controlsReset();
    }

    private void controlsReset() {
        tfCimlet.clear();
        tfDarab.clear();
        tfTomeg.clear();
        dpKezd.setValue(null);
        dpBevonas.setValue(null);
    }
}
