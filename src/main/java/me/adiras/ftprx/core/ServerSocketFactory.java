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

import me.adiras.ftprx.Property;
import me.adiras.ftprx.ServerProperties;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketFactory {
    public ServerSocket createServerSocket() throws IOException {
        int port    = Integer.parseInt(ServerProperties.get(Property.PORT));
        int backlog = Integer.parseInt(ServerProperties.get(Property.SERVER_SOCKET_BACKLOG));
        return new ServerSocket(port, backlog, InetAddress.getByName("127.0.0.1"));
    }
}
