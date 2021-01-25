package com.ftprx.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.ftprx.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

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
    private Button userManagerButton;

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
    void onUserManagerButtonClick(ActionEvent event) {
        showAddUserWindow();
    }

    private void showAddUserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user-manager.fxml"));
            Parent root = loader.load();
            UserManagerController controller = loader.getController();
            Stage stage = new Stage();
            controller.setStage(stage);
            stage.setOnCloseRequest(event -> controller.onCloseRequest());
            stage.setScene(new Scene(root, 600, 370));
            stage.setTitle("User Manager");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
    }
}
