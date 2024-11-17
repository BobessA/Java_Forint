package org.example.fx_forint.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane viewContainer;

    private void loadView(String fxmlFile) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlFile));
            viewContainer.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadAllDataView() {
        loadView("/org/example/fx_forint/allData-view.fxml");
    }

    @FXML
    private void loadSearchErmeView() {
        loadView("/org/example/fx_forint/searchErme-view.fxml");
    }

    @FXML
    private void loadNewErmeView() {
        loadView("/org/example/fx_forint/newErme-view.fxml");
    }

    @FXML
    private void loadModifiedErmeView() {
        loadView("/org/example/fx_forint/modifiedErme-view.fxml");
    }

    @FXML
    private void loadDeleteErmeView() {
        loadView("/org/example/fx_forint/deleteErme-view.fxml");
    }

    @FXML
    private void loadMultitaskView() {loadView("/org/example/fx_forint/multitask-view.fxml"); }

    @FXML
    private void loadMNBDataToFile() {loadView("/org/example/fx_forint/downloadMNBExchanges-view.fxml");}

}