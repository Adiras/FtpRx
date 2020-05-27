package com.ftprx.application.controler;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ftprx.application.UpdateUsedMemoryTask;
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

    private Server server;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label serverStatusLabel;

    @FXML
    private Label ipAddressLabel;

    @FXML
    private Label portLabel;

    @FXML
    private Label currentConnectionsLabel;

    @FXML
    private Label usedMemoryLabel;

    @FXML
    private Label uptimeLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button pauseButton;

    @FXML
    private TableView<Client> connectionsTable;

    @FXML
    private TableColumn<Client, String> ipColumn;

    @FXML
    private TableColumn<Client, String> usernameColumn;

    @FXML
    void onAboutButtonClick(ActionEvent actionEvent) {
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
    void onPauseButtonClick(ActionEvent event) {
        server.pause();

        startButton.setDisable(false);
         stopButton.setDisable(false);
        pauseButton.setDisable(true);
    }

    @FXML
    void onStartButtonClick(ActionEvent event) {
        server.start();

        startButton.setDisable(true);
         stopButton.setDisable(false);
        pauseButton.setDisable(false);
    }

    @FXML
    void onStopButtonClick(ActionEvent event) {
        server.stop();

        startButton.setDisable(false);
         stopButton.setDisable(true);
        pauseButton.setDisable(true);
    }

    @FXML
    void onUserListButtonClick(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/user-list.fxml"));
            Stage stage = new Stage();
            stage.setTitle("User List");
            stage.setScene(new Scene(root, 360, 345));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        server = new Server();

        ipColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Client, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Client, String> param) {
                final Client client = param.getValue();
                return new SimpleStringProperty(client.getControlConnection().getInetAddress().getHostAddress());
            }
        });


        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(() -> {
                    final ObservableList<Client> clients = FXCollections.observableArrayList(Server.getInstance().getClients());
                    connectionsTable.setItems(clients);
                }, 0L, 1L, TimeUnit.SECONDS);

        ScheduledExecutorService updateUI = Executors.newSingleThreadScheduledExecutor();
        updateUI.scheduleWithFixedDelay(() -> {
            List<Runnable> tasks = new ArrayList<>();
            tasks.add(new UpdateUsedMemoryTask(server, usedMemoryLabel));
            for (Runnable task : tasks) {
                Platform.runLater(task);
            }
        }, 0L, 1L, TimeUnit.SECONDS);
    }
}
