package org.example.fx_forint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static org.example.fx_forint.helper.databaseHelper.setDbFilePath;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        setDbFilePath("jdbc:sqlite:C:\\adatok\\adatok.db");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1040, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }



}