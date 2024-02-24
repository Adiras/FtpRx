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

package com.ftprx.server.util;

import org.jetbrains.annotations.Nullable;

import javax.net.ServerSocketFactory;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketHelper {

    public static boolean isServerSocketOpen(@Nullable ServerSocket socket) {
        return socket != null && socket.isBound() && !socket.isClosed();
    }

    public static boolean isSocketOpen(@Nullable Socket socket) {
        return socket != null && socket.isBound() && !socket.isClosed();
    }

    public static int findAvailableTcpPort(int minPort, int maxPort) {
        return SocketType.TCP.findAvailablePort(minPort, maxPort);
    }

    public static int findAvailableUdpPort(int minPort, int maxPort) {
        return SocketType.UDP.findAvailablePort(minPort, maxPort);
    }

    private enum SocketType {
        TCP {
            @Override
            protected boolean isPortAvailable(int port) {
                try {
                    ServerSocket serverSocket = ServerSocketFactory.getDefault()
                            .createServerSocket(port, 1, InetAddress.getByName("localhost"));
                    serverSocket.close();
                    return true;
                } catch (Exception ignore) {
                    return false;
                }
            }
        },

        UDP {
            @Override
            protected boolean isPortAvailable(int port) {
                try {
                    DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
                    socket.close();
                    return true;
                } catch (Exception ignore) {
                    return false;
                }
            }
        };

        protected abstract boolean isPortAvailable(int port);

        private int findRandomPort(int minPort, int maxPort) {
            int portRange = maxPort - minPort;
            return minPort + new Random().nextInt(portRange + 1);
        }

        int findAvailablePort(int minPort, int maxPort) {
            int portRange = maxPort - minPort;
            int candidatePort;
            int searchCounter = 0;
            do {
                if (searchCounter > portRange) {
                    throw new IllegalStateException(String.format(
                            "Could not find an available %s port in the range [%d, %d] after %d attempts",
                            name(), minPort, maxPort, searchCounter));
                }
                candidatePort = findRandomPort(minPort, maxPort);
                searchCounter++;
            }
            while (!isPortAvailable(candidatePort));

            return candidatePort;
        }
    }
}
