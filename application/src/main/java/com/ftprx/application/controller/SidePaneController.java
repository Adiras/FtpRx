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
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.ftprx.server.Server;
import com.ftprx.server.ServerStatus;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static java.lang.String.format;

public class SidePaneController {

    private static final long MEGABYTE_FACTOR = 1024L * 1024L;

    private final Server server = Server.getInstance();

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
    void initialize() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(() -> {
                    Platform.runLater(() -> {
                        refreshServerStatus();
                        refreshIpAddress();
                        refreshPort();
                        refreshCurrentConnections();
                        refreshUsedMemory();
                    });
                }, 0L, 1L, TimeUnit.SECONDS);
    }

    private void refreshServerStatus() {
        Color color = Color.DARKGRAY;
        String text = "null";

        switch (server.getStatus()) {
            case RUNNING:
                text = "Running";
                color = Color.GREEN;
                break;
            case STOPPED:
                text = "Stopped";
                color = Color.RED;
                break;
            case PAUSED:
                text = "Paused";
                color = Color.DARKORANGE;
                break;
        }
        serverStatusLabel.setTextFill(color);
        serverStatusLabel.setText(text);
    }

    private void refreshIpAddress() {
        if (server.getStatus() != ServerStatus.STOPPED) {
            final String ip = server.getAddress().getHostAddress();
            ipAddressLabel.setText(ip);
        } else {
            ipAddressLabel.setText("");
        }
    }

    private void refreshPort() {
        if (server.getStatus() != ServerStatus.STOPPED) {
            final String port = String.valueOf(server.getPort());
            portLabel.setText(port);
        } else {
            portLabel.setText("");
        }
    }

    private void refreshCurrentConnections() {
        currentConnectionsLabel.setText(String.valueOf(server.getClients().size()));
    }

    private void refreshUsedMemory() {
        final Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        usedMemoryLabel.setText(format("%dMB", memory / MEGABYTE_FACTOR));
    }
}
