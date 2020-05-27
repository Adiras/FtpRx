package com.ftprx.application.controler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class UserListController {

    private AccountRepository accountRepository;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Account> userList;

    @FXML
    void onCreateAccountButtonClick(ActionEvent event) {
        Server.getInstance().getAccountRepository().insert(new Account("new_account@" + UUID.randomUUID(), "", ""));
        refreshData();
        userList.getSelectionModel().selectLast(); // select last item
        userList.scrollTo(userList.getItems().size()); // scroll to last item
    }

    @FXML
    void onDeleteAccountButtonClick(ActionEvent event) {
        Account selected = userList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            accountRepository.delete(selected.getUsername());
            refreshData();
        }
    }

    @FXML
    void onEditAccountButtonClick(ActionEvent event) {
        Account selected = userList.getSelectionModel().getSelectedItem();
        if (selected == null)
            return;



        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/user-edit.fxml"));
            Parent root = fxmlLoader.load();
            UserEditController controller = fxmlLoader.getController();
            controller.inject(selected);
            Stage stage = new Stage();
            stage.setTitle("Edit account information");
            stage.setScene(new Scene(root, 510, 280));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshData();
    }

    void refreshData() {
        userList.setItems(FXCollections.observableArrayList(accountRepository.findAll()));
    }

    @FXML
    void initialize() {
        accountRepository = Server.getInstance().getAccountRepository();
        refreshData();

    }
}
