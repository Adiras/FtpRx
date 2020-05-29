package com.ftprx.application.controler;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class UserEditController {

    private Account account;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField homeDirectoryField;

    @FXML
    private CheckBox loginWithoutPasswordCheckBox;

    @FXML
    void onBrowseButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        File directory = new DirectoryChooser().showDialog(stage);
        if (directory != null) {
            homeDirectoryField.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        closeStage(event);
    }

    @FXML
    void onChangePasswordButtonClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter new password");
        dialog.setHeaderText("Change account password");
        dialog.setContentText("Please enter new password:");
        Optional<String> result = dialog.showAndWait();
//        result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        account.setUsername(usernameField.getText());
        Server.getInstance().getAccountRepository().update(account);
        closeStage(event);
    }

    @FXML
    void initialize() {
    }

    void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    void inject(Account account) {
        this.account = account;
        usernameField.setText(account.getUsername());
        homeDirectoryField.setText(account.getHomeDirectory());
    }
}
