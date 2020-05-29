package com.ftprx.application.controler;

import java.net.URL;
import java.util.ResourceBundle;

import com.ftprx.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolBarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button userListButton;

    @FXML
    void onPauseButtonClick(ActionEvent event) {
        Server.getInstance().pause();

        startButton.setDisable(false);
        stopButton.setDisable(false);
        pauseButton.setDisable(true);
    }

    @FXML
    void onStartButtonClick(ActionEvent event) {
        Server.getInstance().start();

        startButton.setDisable(true);
        stopButton.setDisable(false);
        pauseButton.setDisable(false);
    }

    @FXML
    void onStopButtonClick(ActionEvent event) {
        Server.getInstance().stop();

        startButton.setDisable(false);
        stopButton.setDisable(true);
        pauseButton.setDisable(true);
    }



    @FXML
    void onUserListButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }
}
