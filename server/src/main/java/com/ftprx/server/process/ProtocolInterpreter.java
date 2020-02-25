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

package com.ftprx.server.process;

import com.ftprx.server.ServerStatus;
import com.ftprx.server.account.InMemoryAccountRepository;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.command.CommandDispatcher;
import com.ftprx.server.thread.WorkerThread;
import com.ftprx.server.channel.ControlConnection;
import com.ftprx.server.thread.ListenerThread;
import com.ftprx.server.util.ServerHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.tinylog.Logger;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * The server protocol interpreter listens on specified port
 * for a connection from a user and establishes a control communication connection.
 * It receives commands from the client, sends replies,
 * and manages the server data transfer process.
 */
public class ProtocolInterpreter {
    private static final int PORT = 21;
    private static final int SO_TIMEOUT = 3000;
    private static final String HOSTNAME = "127.0.0.1";

    private final ObservableList<ControlConnection> connections;
    private final ExecutorService threadPool;

    private ServerSocket server;
    private ListenerThread listener;
    private ServerStatus status;
    private CommandDispatcher dispatcher;

    public ProtocolInterpreter() {
        this.threadPool = Executors.newFixedThreadPool(10);
        this.connections = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
        this.status = ServerStatus.STOPPED;
        this.dispatcher = new CommandDispatcher(new InMemoryAccountRepository());

        Executors.newSingleThreadScheduledExecutor()
            .scheduleWithFixedDelay(() -> {
                Iterator<ControlConnection> iterator = connections.iterator();
                while (iterator.hasNext()) {
                    ControlConnection connection = iterator.next();
                    if (!connection.isOpen()) {
                        iterator.remove();
                    }
                }
            }, 2L, 2L, TimeUnit.SECONDS);
    }

    /**
     * Start the server.
     * Initialize main socket and thread listening for incoming connections.
     */
    public void start() {
        if (!ServerHelper.isOpen(server)) {
            try {
                server = new ServerSocket();
                server.setSoTimeout(SO_TIMEOUT);
                server.bind(new InetSocketAddress(HOSTNAME, PORT));
                listener = new ListenerThread(server);
                listener.registerClientConnectObserver(this::acceptClient);
                listener.start();
                status = ServerStatus.RUNNING;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stop the server.
     * New clients will not be accepted and stops existing connections.
     */
    public void stop() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listener != null && listener.isAlive()) {
            listener.interrupt();
        }
        disconnectAllClients();
        status = ServerStatus.STOPPED;
    }

    /**
     * Pause the server.
     * When a server is paused, it will not accept any new clients,
     * but current connected clients will be kept.
     */
    public void pause() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listener != null && listener.isAlive()) {
            listener.interrupt();
        }
        status = ServerStatus.PAUSED;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(server.getInetAddress().getHostAddress());
    }

    public Optional<Integer> getPort() {
        return Optional.ofNullable(server.getLocalPort());
    }

    public Optional<ServerSocket> getServer() {
        return Optional.ofNullable(server);
    }

    public ObservableList<ControlConnection> getConnections() {
        return connections;
    }

    private void acceptClient(Socket client) {
        Logger.debug("New client accepted");
        ControlConnection connection = new ControlConnection(client);
        registerNewConnection(connection);
        launchWorkerThread(connection);
        connection.sendReply(Reply.ReplyBuilder
                .aReply()
                .withCode("220")
                .withText("Server welcome")
                .build());
    }

    private void disconnectAllClients() {
        for (ControlConnection connection : connections) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerNewConnection(ControlConnection connection) {
        connections.add(connection);
    }

    private void launchWorkerThread(ControlConnection connection) {
        threadPool.execute(new WorkerThread(connection, dispatcher));
    }
}
