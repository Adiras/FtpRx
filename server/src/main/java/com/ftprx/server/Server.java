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

import com.ftprx.server.account.AccountRepository;
import com.ftprx.server.account.InMemoryAccountRepository;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.command.BootstrapCommands;
import com.ftprx.server.command.CommandDispatcher;
import com.ftprx.server.channel.Client;
import com.ftprx.server.thread.ListenerThread;
import com.ftprx.server.thread.ThreadManager;
import com.ftprx.server.util.SocketHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.tinylog.Logger;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * The server protocol interpreter listens on specified port
 * for a connection from a user and establishes a control communication connection.
 * It receives commands from the client, sends replies,
 * and manages the server data transfer process.
 */
public class Server {
    private static final int PORT = 21;
    private static final int SO_TIMEOUT = 3000;
    private static final String HOSTNAME = "127.0.0.1";
    private static Server instance = null;
    private final ObservableList<Client> clients;
    private Instant startTimestamp;
    private ServerSocket server;
    private ListenerThread listenerThread;
    private ServerStatus status;
    private CommandDispatcher dispatcher;
    private AccountRepository accountRepository;
    private ThreadManager threadManager;

    public Server() {
        this.clients = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
        this.status = ServerStatus.STOPPED;
        this.dispatcher = new CommandDispatcher();
        this.accountRepository = new InMemoryAccountRepository();
        this.threadManager = new ThreadManager();
        new BootstrapCommands(dispatcher);

        Executors.newSingleThreadScheduledExecutor()
            .scheduleWithFixedDelay(() -> {
                Iterator<Client> iterator = clients.iterator();
                while (iterator.hasNext()) {
                    Client connection = iterator.next();
                    if (!connection.isControlConnectionOpen()) {
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
        if (!SocketHelper.isOpen(server)) {
            try {
                server = new ServerSocket();
                server.setSoTimeout(SO_TIMEOUT);
                server.bind(new InetSocketAddress(HOSTNAME, PORT));
                listenerThread = new ListenerThread(server);
                listenerThread.registerClientConnectObserver(this::acceptClient);
                listenerThread.start();
                status = ServerStatus.RUNNING;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startTimestamp = Instant.now();
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
    public void pause() {
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

    public ObservableList<Client> getClients() {
        return clients;
    }

    private void acceptClient(Socket connection) {
        Logger.debug("New client accepted");
        Client client = new Client(connection);
        registerNewClient(client);
        threadManager.launchWorkerThread(client);
        client.sendReply(Reply.ReplyBuilder
                .aReply()
                .withCode("220")
                .withText("Server welcome")
                .build());
    }

    private void disconnectAllClients() {
        for (Client client : clients) {
            try {
                client.closeControlConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public CommandDispatcher getDispatcher() {
        return dispatcher;
    }

    private void registerNewClient(Client connection) {
        clients.add(connection);
    }

    public Optional<Instant> getStartTimestamp() {
        return Optional.ofNullable(startTimestamp);
    }

    public synchronized static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }
}
