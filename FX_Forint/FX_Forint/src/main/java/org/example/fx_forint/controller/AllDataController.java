package org.example.fx_forint.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.fx_forint.models.*;
import org.example.fx_forint.helper.databaseHelper;

import java.time.LocalDate;
import java.util.List;

public class AllDataController {

    @FXML
    private TableView<Object> dataTable;

    @FXML
    public void initialize() {
        loadAnyag();
    }

    private void clearTable() {
        dataTable.getColumns().clear();
        dataTable.getItems().clear();
    }

    @FXML
    private void loadAnyag() {
        clearTable();
        TableColumn<Object, Integer> femid = new TableColumn<>("Anyag Id");
        femid.setCellValueFactory(new PropertyValueFactory<>("femid"));

        TableColumn<Object, String> femnev = new TableColumn<>("Megnevezés");
        femnev.setCellValueFactory(new PropertyValueFactory<>("femnev"));

        dataTable.getColumns().addAll(femid, femnev);

        List<Anyag> details = databaseHelper.getRecords("Select * from anyag", Anyag.class);

        ObservableList<Object> observableDetails = FXCollections.observableArrayList(details);
        dataTable.setItems(observableDetails);
    }

    @FXML
    private void loadAkod() {
        clearTable();
        TableColumn<Object, Integer> ermeid = new TableColumn<>("Érme Id");
        ermeid.setCellValueFactory(new PropertyValueFactory<>("ermeid"));

        TableColumn<Object, Integer> femid = new TableColumn<>("Anyag Id");
        femid.setCellValueFactory(new PropertyValueFactory<>("femid"));

        dataTable.getColumns().addAll(ermeid, femid);

        List<Akod> details = databaseHelper.getRecords("Select * from akod", Akod.class);

        ObservableList<Object> observableDetails = FXCollections.observableArrayList(details);
        dataTable.setItems(observableDetails);
    }

    @FXML
    private void loadTkod() {
        clearTable();
        TableColumn<Object, Integer> femid = new TableColumn<>("Érme Id");
        femid.setCellValueFactory(new PropertyValueFactory<>("ermeid"));

        TableColumn<Object, Integer> tervezoid = new TableColumn<>("Tervező Id");
        tervezoid.setCellValueFactory(new PropertyValueFactory<>("tervezoid"));

        dataTable.getColumns().addAll(femid, tervezoid);

        List<Tkod> details = databaseHelper.getRecords("Select * from tkod", Tkod.class);

        ObservableList<Object> observableDetails = FXCollections.observableArrayList(details);
        dataTable.setItems(observableDetails);
    }

    @FXML
    private void loadTervezo() {
        clearTable();
        TableColumn<Object, Integer> tid = new TableColumn<>("Tervező Id");
        tid.setCellValueFactory(new PropertyValueFactory<>("tid"));

        TableColumn<Object, String> nev = new TableColumn<>("Név");
        nev.setCellValueFactory(new PropertyValueFactory<>("nev"));

        dataTable.getColumns().addAll(tid, nev);

        List<Tervezo> details = databaseHelper.getRecords("Select * from tervezo", Tervezo.class);

        ObservableList<Object> observableDetails = FXCollections.observableArrayList(details);
        dataTable.setItems(observableDetails);
    }

    @FXML
    private void loadErme() {
        clearTable();
        TableColumn<Object, Integer> ermeid = new TableColumn<>("Érme Id");
        ermeid.setCellValueFactory(new PropertyValueFactory<>("ermeid"));

        TableColumn<Object, Integer> cimlet = new TableColumn<>("Címlet");
        cimlet.setCellValueFactory(new PropertyValueFactory<>("cimlet"));

        TableColumn<Object, Double> tomeg = new TableColumn<>("Tömeg");
        tomeg.setCellValueFactory(new PropertyValueFactory<>("tomeg"));

        TableColumn<Object, Integer> darab = new TableColumn<>("Darab");
        darab.setCellValueFactory(new PropertyValueFactory<>("darab"));

        TableColumn<Object, LocalDate> kiadas = new TableColumn<>("Kiadás dátuma");
        kiadas.setCellValueFactory(new PropertyValueFactory<>("kiadas"));

        TableColumn<Object, LocalDate> bevonas = new TableColumn<>("Bevonás dátuma");
        bevonas.setCellValueFactory(new PropertyValueFactory<>("bevonas"));

        dataTable.getColumns().addAll(ermeid, cimlet, tomeg, darab, kiadas, bevonas);

        List<Erme> details = databaseHelper.getRecords("Select * from erme", Erme.class);

        ObservableList<Object> observableDetails = FXCollections.observableArrayList(details);
        dataTable.setItems(observableDetails);
    }

}
