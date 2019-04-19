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

package me.adiras.ftprx.core.threading;

import me.adiras.ftprx.core.Server;

import java.io.IOException;
import java.net.ServerSocket;

import static org.tinylog.Logger.*;

/**
 * Listens for a connection to be made to this socket and accepts it.
 */
public class ServerListenerRunnable implements Runnable {
    private Server server;
    private ServerSocket serverSocket;

    public ServerListenerRunnable(Server server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;
    }

    private boolean listening() {
        return serverSocket != null && !serverSocket.isClosed();
    }

    @Override
    public void run() {
        trace("Listener thread started");

        while (listening()) {
            try {
                // This method waits until a client connects to the server on the given port
                server.connectionRequest(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
