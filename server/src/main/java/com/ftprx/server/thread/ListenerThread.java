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

package com.ftprx.server.thread;

import com.ftprx.server.ClientManager;
import com.ftprx.server.ServerConfig;
import com.ftprx.server.util.SocketHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerThread extends Thread {
    private static final String THREAD_NAME = "Protocol Interpreter Listening Thread";
    private final Set<ClientConnectObserver> observers;
    private final ServerSocket server;
    private final ClientManager clientManager;
    private final ServerConfig config;

    public ListenerThread(@NotNull ServerSocket server,
                          @NotNull ClientManager clientManager,
                          @NotNull ServerConfig config) {
        this.server = Objects.requireNonNull(server, "Server must not be null");
        this.clientManager = clientManager;
        this.config = config;
        this.observers = Collections.newSetFromMap(new ConcurrentHashMap<>());
        setName(THREAD_NAME);
    }

    @Override
    public void run() {
        Logger.info("Listening for connections on port {}", server.getLocalPort());
        while (!Thread.currentThread().isInterrupted()) {
            if (SocketHelper.isServerSocketOpen(server)) {
                if (!canAcceptNewClient()) {
                    Logger.warn("Client concurrent connection limit exceeded");
                    continue;
                }
                try {
                    Socket client = server.accept();
                    notifyObservers(client);
                } catch (SocketTimeoutException ignore) {
                } catch (IOException e) {
                    Logger.error("Error while waiting for client: {}", e.getMessage());
                }
            }
        }
    }

    private boolean canAcceptNewClient() {
        if (config.concurrentConnectionLimit() != 0) {
            var currentConnections = clientManager.getClients().size();
            var isConnectionLimitExceeded = config.concurrentConnectionLimit() <= currentConnections;
            return !isConnectionLimitExceeded;
        }
        return true;
    }

    public void registerClientConnectObserver(@Nullable ClientConnectObserver observer) {
        Optional.ofNullable(observer).ifPresent(observers::add);
    }

    public void unregisterClientConnectObserver(@Nullable ClientConnectObserver observer) {
        Optional.ofNullable(observer).ifPresent(observers::remove);
    }

    private void notifyObservers(@NotNull Socket client) {
        for (ClientConnectObserver observer : observers) {
            observer.onClientConnected(client);
        }
    }
}
