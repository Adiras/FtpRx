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

import me.adiras.ftprx.*;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static org.tinylog.Logger.*;

/**
 * The class is responsible to service one client connection.
 */
public class WorkerThread implements Runnable, Connection {
    private NetworkListener listener;
    private Socket clientSocket;

    // Will be used to get data from client.
    private DataInputStream reader;

    // Will be used to send data to client.
    private BufferedWriter writer;

    private long idleTimeout = 15000;
    private long lastDataReceivedTime;

    public WorkerThread(Socket clientSocket, NetworkListener listener) {
        this.clientSocket = clientSocket;
        this.listener = listener;
        lastDataReceivedTime = System.currentTimeMillis();
    }

    private void initializeStreams() throws IOException {
        reader = new DataInputStream(clientSocket.getInputStream());
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    /**
     * Called after each received of data through the socket.
     * @param data the buffer into which the data is stored
     */
    private void handleReceiveData(byte[] data) {
        listener.onDataReceive(this, data);
    }

    private boolean running() {
        return clientSocket != null && !clientSocket.isClosed();
    }

    @Override
    public void run() {
        debug("Client connected: {}", clientSocket.getInetAddress().getHostAddress());

        try {
            initializeStreams();

            listener.onConnectionEstablishment(this);

            while (running()) {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastDataReceivedTime) > idleTimeout) break;

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int read;
                while((read = reader.read(buffer)) != -1) {
                    handleReceiveData(Arrays.copyOf(buffer, read));
                }
            }
        } catch (Exception e) {
            warn("Client disconnected: {} - {}", clientSocket.getInetAddress().getHostAddress(), e.getMessage());
        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendResponse(Response response) {
        try {
            writer.write(response.getCode() + ' ' + response.getArgument() + '\r' + '\n');
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
