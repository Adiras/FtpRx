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

import com.ftprx.server.channel.Client;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class PassiveConnectionMode implements ConnectionMode {
    private final int port;

    /**
     * Create a new {@link PassiveConnectionMode} instance.
     * @param port the port number.
     */
    public PassiveConnectionMode(int port) {
        if (!(port > 0 && port < 65535))
            throw new IllegalArgumentException("Invalid port number");
        this.port = port;
    }

    /**
     * Opens the data connection between client and server.
     * The method blocks thread until a connection is made.
     */
    @Override
    public void openConnection(@Nonnull Client client) {
        Thread connectionThread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                client.establishDataConnection(socket.accept());
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        });
        connectionThread.start();
    }
}
