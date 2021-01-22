package com.ftprx.application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuBarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onAboutButtonClick(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/about.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 420, 280));
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("About FtpRx");
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
