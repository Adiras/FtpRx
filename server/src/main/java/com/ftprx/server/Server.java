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
import com.ftprx.server.channel.Client;
import com.ftprx.server.repository.FileAccountRepository;
import com.ftprx.server.thread.ListenerThread;
import com.ftprx.server.thread.ThreadManager;
import com.ftprx.server.util.SocketHelper;
import com.google.inject.internal.cglib.core.$MethodWrapper;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

/**
 * The server protocol interpreter listens on specified port
 * for a connection from a user and establishes a control communication connection.
 * It receives commands from the client, sends replies,
 * and manages the server data transfer process.
 */
public class Server {

    private static final String HOSTNAME   = "127.0.0.1";
    private static final int    PORT       = 21;
    private static final int    SO_TIMEOUT = 3000;

    private static Server instance = null;

    private static List<Client> clients;
    private Instant startTimestamp;
    private ServerSocket server;
    private ListenerThread listenerThread;
    private ServerStatus status;
    private AccountRepository accountRepository;
    private ThreadManager threadManager;

    public Server() {
        this.clients = new CopyOnWriteArrayList<>();
        this.status = ServerStatus.STOPPED;
        this.accountRepository = new FileAccountRepository("C:\\Users\\wkacp\\Desktop\\ftprx\\server\\src\\main\\resources\\accounts.json");
        this.threadManager = new ThreadManager();
    }

    /**
     * Start the server.
     * Initialize main socket and thread listening for incoming connections.
     */
    public synchronized void start() {
        if (!SocketHelper.isServerSocketOpen(server)) {
            try {
//                server = ServerSocketFactory.getDefault().createServerSocket(PORT, 1, InetAddress.getByName("localhost"));
                server = new ServerSocket();
                server.setSoTimeout(SO_TIMEOUT);
                server.bind(new InetSocketAddress(HOSTNAME, PORT));
                listenerThread = new ListenerThread(server);
                listenerThread.registerClientConnectObserver(this::acceptClient);
                listenerThread.start();
                status = ServerStatus.RUNNING;
            } catch (BindException e) {
                Logger.warn(e.getMessage());
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
    public synchronized void stop() {
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

    public ServerStatus getStatus() {
        return status;
    }

    public Optional<String> getAddress() {
        if (server == null) {
            return Optional.empty();
        }
        return Optional.of(server.getInetAddress().getHostAddress());
    }

    public Optional<Integer> getPort() {
        if (server == null) {
            return Optional.empty();
        }
        return Optional.of(server.getLocalPort());
    }

    public Optional<ServerSocket> getServer() {
        return Optional.ofNullable(server);
    }

    public List<Client> getClients() {
        return clients;
    }

    private void acceptClient(Socket socket) {
        final Client client = new Client(socket);
        threadManager.launchWorkerThread(client);
        clients.add(client);
        client.sendReply(220, "Server welcome");
        Logger.debug("New client accepted");
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

//    private void registerNewClient(Client connection) {
//        clients.add(connection);
//    }

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
