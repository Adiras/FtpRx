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
import java.util.*;

import javafx.fxml.FXML;

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
