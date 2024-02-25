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

package com.ftprx.server;

import com.ftprx.server.account.AccountFileFormat;
import com.ftprx.server.account.ObservableAccountRepository;
import com.ftprx.server.channel.Client;
import com.ftprx.server.repository.FileAccountRepository;
import com.ftprx.server.thread.ListenerThread;
import com.ftprx.server.thread.ThreadManager;
import com.ftprx.server.util.SocketHelper;
import org.aeonbits.owner.ConfigFactory;
import org.jetbrains.annotations.NotNull;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The server protocol interpreter listens on specified port
 * for a connection from a user and establishes a control communication connection.
 * It receives commands from the client, sends replies,
 * and manages the server data transfer process.
 */
public class Server implements ClientManager {
    private static final int SO_TIMEOUT = 3000;
    private static Server instance = null;
    private final List<Client> clients;
    private Instant startTimestamp;
    private ServerSocket server;
    private ListenerThread listenerThread;
    private ServerStatus status;
    private ServerConfig config;
    private ObservableAccountRepository accountRepository;

    private Server() {
        this.clients = new CopyOnWriteArrayList<>();
        this.accountRepository = new FileAccountRepository("accounts.json", AccountFileFormat.JSON);
        this.status = ServerStatus.STOPPED;
        this.config = ConfigFactory.create(ServerConfig.class);

        createConfigFileIfNotExists();
    }

    /**
     * Start the server.
     * Initialize main socket and thread listening for incoming connections.
     */
    public synchronized void start() {
        if (!SocketHelper.isServerSocketOpen(server)) {
            try {
                server = new ServerSocket();
                server.setSoTimeout(SO_TIMEOUT);
                server.bind(new InetSocketAddress(config.hostname(), config.port()));
                listenerThread = new ListenerThread(server, this, config);
                listenerThread.registerClientConnectObserver(this::acceptClient);
                listenerThread.start();
                status = ServerStatus.RUNNING;
            } catch (Exception e) {
                Logger.error(e);
            }
        }
        startTimestamp = Instant.now();
    }

    /**
     * Stop the server.
     * New clients will not be accepted and stops existing connections.
     */
    public synchronized void stop() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
        }
        disconnectAllClients();
        status = ServerStatus.STOPPED;
        startTimestamp = null;
    }

    /**
     * Pause the server.
     * When a server is paused, it will not accept any new clients,
     * but current connected clients will be kept.
     */
    public synchronized void pause() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
        }
        status = ServerStatus.PAUSED;
    }

    @NotNull
    public ServerStatus getStatus() {
        return status;
    }

    public InetAddress getAddress() {
        return server.getInetAddress();
    }

    public int getPort() {
        return server.getLocalPort();
    }

    public Optional<ServerSocket> getServer() {
        return Optional.ofNullable(server);
    }

    @NotNull
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Method will perform the reload of all properties.
     * If the configuration files have been altered, after the reload invocation,
     * those changes will be reflected in the config object.
     */
    public void reloadConfig() {
        config.reload();
    }

    private void acceptClient(Socket socket) {
        final Client client = new Client(socket);
        registerNewClient(client);
        ThreadManager.launchWorkerThread(client);
        client.sendReply(220, "Server welcome");
        Logger.debug("New client accepted");
    }

    private void disconnectAllClients() {
        for (Client client : clients) {
            try {
                client.closeControlConnection();
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        }
    }

    public ObservableAccountRepository getAccountRepository() {
        return accountRepository;
    }

    private void registerNewClient(Client connection) {
        clients.add(connection);
    }

    @NotNull
    public synchronized static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    private void createConfigFileIfNotExists() {
        try {
            File configFile = new File("server.properties");
            if (!configFile.exists()) {
                if (configFile.createNewFile()) {
                    Logger.info("Configuration file created: " + configFile.getAbsolutePath());
                    config.store(new FileOutputStream(configFile), "FtpRx server properties");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
