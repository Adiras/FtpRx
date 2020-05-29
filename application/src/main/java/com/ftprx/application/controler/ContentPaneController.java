package com.ftprx.application.controler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.channel.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ContentPaneController {

    private final Server server = Server.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, String> ipColumn;

    @FXML
    private TableColumn<Client, String> usernameColumn;

    @FXML
    private TableColumn<Client, String> workingDirectoryColumn;


    @FXML
    void initialize() {
        ipColumn.setCellValueFactory(createIpCellValueFactory());
        usernameColumn.setCellValueFactory(createUsernameCellValueFactory());
        workingDirectoryColumn.setCellValueFactory(createWorkingDirectoryCellValueFactory());

        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(() -> {
                    Platform.runLater(this::refreshClientsInfo);
                }, 0L, 1L, TimeUnit.SECONDS);
    }

    private Callback<TableColumn.CellDataFeatures<Client,String>,
            ObservableValue<String>> createIpCellValueFactory() {
            return param -> new SimpleStringProperty(param.getValue()
                    .getControlConnection()
                    .getInetAddress()
                    .getHostAddress());
    }

    private Callback<TableColumn.CellDataFeatures<Client,String>,
            ObservableValue<String>> createUsernameCellValueFactory() {
            return param -> {
                final Client client = param.getValue();
                final Account account = client.getAccount();
                return new SimpleStringProperty(account == null ? "null" : account.getUsername());
            };
    }

    private Callback<TableColumn.CellDataFeatures<Client,String>,
            ObservableValue<String>> createWorkingDirectoryCellValueFactory() {
            return param -> {
                final Client client = param.getValue();
                final Account account = client.getAccount();
                return new SimpleStringProperty(account == null ? "null" : account.getHomeDirectory());
            };
    }

    private void refreshClientsInfo() {
        ObservableList<Client> values = FXCollections.observableList(server.getClients());
        clientsTable.setItems(values);
    }
}
