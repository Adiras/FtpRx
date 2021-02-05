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
import java.net.Socket;

public class ActiveConnectionMode implements ConnectionMode {
    private final String host;
    private final int port;

    /**
     * Create a new {@link ActiveConnectionMode} instance.
     * @param host the host name, or {@code null} for the loopback address
     * @param port the port number
     */
    public ActiveConnectionMode(String host, int port) {
        if (!(port > 0 && port < 65535))
            throw new IllegalArgumentException("Invalid port number");
        this.host = host;
        this.port = port;
    }

    /**
     * Opens the data connection between client and server.
     * The method blocks thread until a connection is made.
     */
    @Override
    public void openConnection(@Nonnull Client client) {
        try {
            Socket socket = new Socket(host, port);
            client.establishDataConnection(socket);
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }
}
