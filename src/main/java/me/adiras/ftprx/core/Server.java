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

package me.adiras.ftprx.core;

import me.adiras.ftprx.*;
import me.adiras.ftprx.command.RequestDispatcher;
import me.adiras.ftprx.core.threading.ServerListenerRunnable;
import me.adiras.ftprx.core.threading.WorkerThread;
import me.adiras.ftprx.core.threading.WorkerThreadManager;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import static org.tinylog.Logger.*;

/**
 * Main Server class. Listening on a port for client. If there is a client,
 * starts a new Thread and goes back to listening for further clients.
 */
public class Server implements ServerContext, NetworkListener {
    private static final int MAXIMUM_WORKER_THREAD_POOL_SIZE = 2;
    private static final int PORT = 21;

    private WorkerThreadManager workerThreadPool = new WorkerThreadManager(MAXIMUM_WORKER_THREAD_POOL_SIZE);
    private RequestDispatcher requestDispatcher = new RequestDispatcher(this);
    private ServerSocket serverSocket;
    private Thread listenerThread;

    private AccountService accountService = new AccountService();

    public void start() {
        info("Starting server...");

        try {
            // Creates a server socket, bound to the specified port.
            serverSocket = new ServerSocket(PORT);
            info("Server running at port {}", PORT);

            launchListenerThread();

        } catch (Exception e) {
            error("Unable to start server: {}", e.getMessage());
        }
    }

    private void launchListenerThread() {
        listenerThread = new Thread(new ServerListenerRunnable(this, serverSocket));
        listenerThread.start();
    }

    /**
     * The method is called if the new client connects to the sockets
     * @param socket client socket to be handled
     */
    public void connectionRequest(Socket socket) {
        trace("Connection request: {}", socket.getInetAddress().getHostAddress());
        workerThreadPool.launchThread(new WorkerThread(socket, this));
    }

    @Override
    public void onDataReceive(Connection connection, byte[] data) {
        String request = new String(data, Charset.forName("UTF-8"));
        requestDispatcher.handleRequest(connection, request);
    }

    @Override
    public void onConnectionEstablishment(Connection connection) {
        connection.sendResponse(Response.builder()
                .code("220")
                .argument("Service ready for new user.")
                .build());
    }

    @Override
    public AccountService getAccountService() {
        return accountService;
    }
}
