package com.ftprx.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onOkButtonClick(ActionEvent event) {
        closeStage(event);
    }

    @FXML
    void onLicenseButtonClick(ActionEvent actionEvent) {
        openBrowser("https://opensource.org/licenses/Apache-2.0");
    }

    @FXML
    void onWebsiteButtonClick(ActionEvent actionEvent) {
        openBrowser("https://github.com/Adiras/FtpRx");
    }

    @FXML
    void initialize() {
    }

    void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    void openBrowser(String webpage) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI.create(webpage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}