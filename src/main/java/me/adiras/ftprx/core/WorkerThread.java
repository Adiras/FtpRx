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

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.adiras.ftprx.util.ControlCharacters.*;

public class WorkerThread implements Runnable, Connection {
    private static final Logger logger = Logger.getLogger(WorkerThread.class.getName());

    private Socket clientSocket;
    private NetworkListener listener;

    private DataInputStream reader;
    private BufferedWriter writer;

    public WorkerThread(Socket clientSocket, NetworkListener listener) {
        this.clientSocket = clientSocket;
        this.listener = listener;
    }

    private void handleReceiveData(byte[] data) {
        listener.onRequestReceive(this, data);
    }

    private void initializeStreams() throws IOException {
        reader = new DataInputStream(clientSocket.getInputStream());
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "New worker thread created [{0}]", Thread.currentThread().getName());
        try {
            initializeStreams();
            listener.onConnectionEstablishment(this);

            int bufferSize = Integer.parseInt(
                    ServerProperties.get(Property.BUFFER_SIZE));

            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (clientSocket != null && !clientSocket.isClosed()) {
                if (reader.available() == 0) continue;
                byte[] data = buffer.array();
                reader.read(data);
                for (byte b : data) {
                    if (b != 0) {
                        handleReceiveData(data);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendResponse(Response response) {
        try {
            writer.write(response.getCode() + SP + response.getArgument() + CRLF);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UUID getUUID() {
        return UUID.randomUUID();
    }
}
