package org.example.fx_forint.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultitaskController {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label timeLabel;

    @FXML
    private Button startButton;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    @FXML
    private void startMultiTaskAction() {
        startButton.setDisable(true);

        Runnable task1 = new Runnable() {
            int counter = 1;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    timeLabel.setText("Eltelt idő: " + formatTime(counter));
                    label1.setText("Task1 számláló: " + counter++);
                });

            }
        };

        // Task 2: Label2 frissítése 2 másodpercenként
        Runnable task2 = new Runnable() {
            int counter = 1;

            @Override
            public void run() {
                Platform.runLater(() -> label2.setText("Task2 számláló: " + counter++));
            }
        };

        // Feladatok ütemezése
        executorService.scheduleAtFixedRate(task1, 0, 1, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(task2, 0, 2, TimeUnit.SECONDS);
    }
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    // Tisztítás a program bezárásakor
    public void shutdown() {
        executorService.shutdown();
    }
}
