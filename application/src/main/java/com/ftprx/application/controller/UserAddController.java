/*
 * Copyright 2019, FtpRx Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftprx.application.controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountCreateException;
import com.ftprx.server.account.AccountInsertException;
import com.ftprx.server.account.AccountRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

public class UserAddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password;

    @FXML
    private TextField homeDirectory;

    @FXML
    private TextField username;

    // Allows us to access the stage in controller,
    // should be injected after controller initialization.
    private Stage stage;

    private final AccountRepository accounts = Server.getInstance().getAccountRepository();

    @FXML
    void onBrowseButtonClick(ActionEvent event) {
        var directoryChooser = new DirectoryChooser();
        var selectedDirectory = directoryChooser.showDialog(stage);
        homeDirectory.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        stage.close();
    }

    @FXML
    void onCreateButtonClick(ActionEvent event) {
        try {
            Account newAccount = new Account(username.getText(), homeDirectory.getText(), password.getText());
            try {
                accounts.insert(newAccount);
                stage.close();
            } catch (AccountInsertException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(e.getMessage());
                alert.showAndWait();
            }
        } catch (AccountCreateException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
