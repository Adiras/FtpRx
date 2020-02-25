package com.ftprx.application.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ftprx.server.Server;
import com.ftprx.server.channel.ControlConnection;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RootController {

    @Inject
    private Server server;

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
    private TextArea serverLogTextArea;

    @FXML
    private TableView<ControlConnection> connectionsTableView;

    @FXML
    private TableColumn<ControlConnection, String> usernameTableColumn;

    @FXML
    private TableColumn<ControlConnection, String> clientIpTableColumn;

    @FXML
    private TableColumn<ControlConnection, String> currentPathTableColumn;

    @FXML
    private Label serverStatusLabel;

    @FXML
    private Label ipAddressLabel;

    @FXML
    private Label portLabel;

    @FXML
    private Label currentConnectionsLabel;

    @FXML
    private Label osLabel;

    @FXML
    private Label usedMemoryLabel;

    @FXML
    private Label uptimeLabel;

    @FXML
    void pauseButtonOnMouseClicked(MouseEvent event) {
        serverStatusLabel.setTextFill(Color.BLACK);
        serverStatusLabel.setText("Waiting");
        server.pause();
        startButton.setDisable(false);
        stopButton.setDisable(false);
        pauseButton.setDisable(true);
    }

    @FXML
    void startButtonOnMouseClicked(MouseEvent event) {
        serverStatusLabel.setTextFill(Color.BLACK);
        serverStatusLabel.setText("Waiting");
        server.start();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        pauseButton.setDisable(false);
    }

    @FXML
    void stopButtonOnMouseClicked(MouseEvent event) {
        serverStatusLabel.setTextFill(Color.BLACK);
        serverStatusLabel.setText("Waiting");
        server.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        pauseButton.setDisable(true);
    }

    @FXML
    void initialize() {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleWithFixedDelay(() -> Platform.runLater(() -> {

            Color color = null;
            String text = null;
            switch (server.getStatus()) {
                case RUNNING:
                    text = "Running";
                    color = Color.GREEN;
                    ipAddressLabel.setText(server.getProtocolInterpreter().getAddress().get());
                    portLabel.setText(server.getProtocolInterpreter().getPort().get().toString());
                    break;
                case STOPPED:
                    text = "Stopped";
                    color = Color.RED;
                    ipAddressLabel.setText("");
                    portLabel.setText("");
                    break;
                case PAUSED:
                    text = "Paused";
                    color = Color.ROYALBLUE;
                    break;
            }
            serverStatusLabel.setTextFill(color);
            serverStatusLabel.setText(text);

            String osName = System.getProperty("os.name");
            String osVersion = System.getProperty("os.version");
            String osArch = System.getProperty("os.arch");
            String osText = String.format("%s v%s (%s)", osName, osVersion, osArch);
            osLabel.setText(osText);

            long freeMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long usedMemory = totalMemory - freeMemory;
            String usedMemoryText = String.format("%dMB", usedMemory / (1024 * 1024));
            usedMemoryLabel.setText(usedMemoryText);

            Duration.between(Instant.now(), Instant.now());

            String uptime = "";
            if (server.getStartTimestamp().isPresent()) {
                SimpleDateFormat format = new SimpleDateFormat("H:mm:ss", Locale.getDefault());
                Date now = new Date();
                Date start = Date.from(server.getStartTimestamp().get());
                uptime = format.format(new Date(Math.abs(now .getTime() - start.getTime())));
            }
            uptimeLabel.setText(uptime);

        }), 0L, 1L, TimeUnit.SECONDS);

        initializeConnectionsTableView();
//
//        Executors.newSingleThreadExecutor().execute(new ServerLogThread(serverLogTextArea));
    }

    private void initializeConnectionsTableView() {
        usernameTableColumn.setCellValueFactory(
                param -> new SimpleStringProperty("student"));

        clientIpTableColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue()
                        .getClient()
                        .getInetAddress()
                        .getHostName()));

        currentPathTableColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue()
                        .getClient()
                        .getInetAddress()
                        .getHostName()));

        connectionsTableView.setItems(server.getProtocolInterpreter().getConnections());
    }
}
