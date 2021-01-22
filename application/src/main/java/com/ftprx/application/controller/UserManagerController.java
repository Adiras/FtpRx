package com.ftprx.application.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountRepository;
import com.ftprx.server.account.AccountRepositoryChangeListener;
import com.ftprx.server.account.ObservableAccountRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javax.annotation.Nonnull;

public class UserManagerController implements AccountRepositoryChangeListener {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Account> userList;

    private ObservableList<Account> data;

    // Allows us to access the stage in controller,
    // should be injected after controller initialization.
    private Stage stage;

    private final ObservableAccountRepository accounts = Server.getInstance().getAccountRepository();

    @FXML
    void onAddButtonClick(ActionEvent event) {
        createAndShowUserAddWindow();
    }

    private void createAndShowUserAddWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user-add.fxml"));
            Parent root = loader.load();
            UserAddController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 300));
            stage.setTitle("New user");
            stage.setResizable(false);
            controller.setStage(stage);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEditButtonClick(ActionEvent event) {

    }

    @FXML
    void onRemoveButtonClick(ActionEvent event) {
        Optional.ofNullable(userList.getSelectionModel().getSelectedItem()).ifPresent(selected -> {
            Alert alert = createRemoveAlert(selected);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                AccountRepository accounts = Server.getInstance().getAccountRepository();
                accounts.delete(selected);
            }
        });
    }

    private Alert createRemoveAlert(Account account) {
        return new Alert(Alert.AlertType.CONFIRMATION, "Delete " + account.getUsername() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    }

    @FXML
    void initialize() {
        data = FXCollections.observableArrayList(accounts.findAll());
        userList.setItems(data);

        accounts.addListener(this);
    }

    public void onCloseRequest() {
        accounts.removeListener(this);
    }

    @Override
    public void onInsertAccount(@Nonnull Account account) {
        data.add(account);
        userList.refresh();
    }

    @Override
    public void onDeleteEvent(@Nonnull Account account) {
        data.remove(account);
        userList.refresh();
    }

    @Override
    public void onUpdateEvent(@Nonnull Account account) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
