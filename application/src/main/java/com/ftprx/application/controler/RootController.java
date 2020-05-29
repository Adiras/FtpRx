package com.ftprx.application.controler;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ftprx.server.Server;
import com.ftprx.server.channel.Client;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class RootController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
//        ipColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Client, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Client, String> param) {
//                final Client client = param.getValue();
//                return new SimpleStringProperty(client.getControlConnection().getInetAddress().getHostAddress());
//            }
//        });


//        Executors.newSingleThreadScheduledExecutor()
//                .scheduleWithFixedDelay(() -> {
//                    final ObservableList<Client> clients = FXCollections.observableArrayList(Server.getInstance().getClients());
//                    connectionsTable.setItems(clients);
//                }, 0L, 1L, TimeUnit.SECONDS);

//        final List<Runnable> tasks = createRefreshViewTasks();
//        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
//            for (Runnable task : tasks) {
//                Platform.runLater(task);
//            }
//        }, 0L, 1L, TimeUnit.SECONDS);
    }
}
