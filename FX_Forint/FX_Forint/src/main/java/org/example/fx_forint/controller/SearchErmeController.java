package org.example.fx_forint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.helper.databaseHelper;
import org.example.fx_forint.models.Erme;
import org.example.fx_forint.models.Tervezo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchErmeController {

    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;
    @FXML
    private RadioButton radio3;
    @FXML
    private RadioButton radio4;
    @FXML
    private RadioButton radio5;
    @FXML
    private RadioButton radio6;
    @FXML
    private ToggleGroup toggleGroup;

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

    private ObservableList<Erme> ermeList;

    @FXML
    private TextField tfCimlet;
    @FXML
    private CheckBox chbBevonas;
    @FXML
    private ComboBox<Tervezo> cbTervezo;

    @FXML
    public void initialize() {
        toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        radio3.setToggleGroup(toggleGroup);
        radio4.setToggleGroup(toggleGroup);
        radio5.setToggleGroup(toggleGroup);
        radio6.setToggleGroup(toggleGroup);

        loadErme("Select * from Erme");
        loadCbItems();


    }

    @FXML
    private void loadErme(String sql) {

        ermeTable.getItems().clear();

        ermeid.setCellValueFactory(new PropertyValueFactory<>("ermeid"));
        cimlet.setCellValueFactory(new PropertyValueFactory<>("cimlet"));
        tomeg.setCellValueFactory(new PropertyValueFactory<>("tomeg"));
        darab.setCellValueFactory(new PropertyValueFactory<>("darab"));
        kiadas.setCellValueFactory(new PropertyValueFactory<>("kiadas"));
        kiadas.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText((new SimpleDateFormat("yyyy-MM-dd")).format(item));
                }
            }
        });
        bevonas.setCellValueFactory(new PropertyValueFactory<>("bevonas"));
        bevonas.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText((new SimpleDateFormat("yyyy-MM-dd")).format(item));
                }
            }
        });

        List<Erme> details = getDetails(sql, Erme.class);

        ermeList = FXCollections.observableList(details);
        ermeTable.setItems(ermeList);
    }

    @FXML
    private void loadCbItems() {
        List<Tervezo> tervezok = getDetails("Select * from tervezo", Tervezo.class);
        ObservableList<Tervezo> observableTervezok = FXCollections.observableArrayList(tervezok);

        cbTervezo.setItems(observableTervezok);
        cbTervezo.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Tervezo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNev());
                }
            }
        });

    }

    @FXML
    private void setFilter() {
        List<String> filters = new ArrayList<>();
        String sql = "Select * from Erme";

        String cimletValue = tfCimlet.getText();
        if (tfCimlet.getText() !=  "") {
            try {
                int numb = Integer.parseInt(cimletValue);
                filters.add("cimlet=" + cimletValue);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hibás érték");
                alert.setHeaderText(null);
                alert.setContentText("A megadott címlet nem szám érték, módosítsa az értéket.");
                alert.showAndWait();
                return;
            }
        }

        if (chbBevonas.isSelected()) {
            filters.add("bevonas is null");
        }

        //System.out.println("a kivalasztott tervezo: " + cbTervezo.getValue());
        if (cbTervezo.getValue() != null) {
            filters.add("exists (select 1 from tkod where tkod.ermeid=erme.ermeid and tervezoid="+ cbTervezo.valueProperty().get().tid +")");
        }

        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null && selectedRadioButton.getId() != null) {
            filters.add("exists (select 1 from akod where akod.ermeid=erme.ermeid and femid=" + (selectedRadioButton.getId()).replace("radio","") + ")");
        }

        for(int i = 0; i<filters.size(); i++) {
            if (i == 0) {
                sql += " where " + filters.get(i);
            } else {
                sql += " and " + filters.get(i);
            }
        }
        System.out.println(sql);

        loadErme(sql);
        controlsReset();
    }

    private void controlsReset() {
        tfCimlet.clear();
        chbBevonas.setSelected(false);
        cbTervezo.setValue(null);
        toggleGroup.selectToggle(null);
    }

    private <T> List<T> getDetails(String sql, Class<T> model) {
        return databaseHelper.getRecords(sql, model);
    }
}
