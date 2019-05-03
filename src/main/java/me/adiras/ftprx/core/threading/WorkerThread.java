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

import com.google.inject.Inject;
import me.adiras.ftprx.*;

import java.net.Socket;
import java.util.Arrays;

import static org.tinylog.Logger.*;

/**
 * The class is responsible to service one client connection.
 */
public class WorkerThread extends Connection implements Runnable {
    private NetworkListener listener;

    private ServerConfig config;

    private long idleTimeout = 15000;
    private long lastDataReceivedTime;

    public WorkerThread(Socket socket, NetworkListener listener, ServerConfig config) {
        super(socket);
        this.listener = listener;
        this.config = config;
        lastDataReceivedTime = System.currentTimeMillis();
    }

    /**
     * Called after each received of data through the socket.
     * @param data the buffer into which the data is stored
     */
    private void handleReceiveData(byte[] data) {
        listener.onDataReceive(this, data);
    }

    private boolean running() {
        return socket != null && !socket.isClosed();
    }

    @Override
    public void run() {
        debug("Client connected: {}", socket.getInetAddress().getHostAddress());

        try {
            listener.onConnectionEstablishment(this);

            while (running()) {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastDataReceivedTime) > idleTimeout) break;

                int bufferSize = config.requestBufferSize();
                byte[] buffer = new byte[bufferSize];
                int read;
                while((read = reader.read(buffer)) != -1) {
                    handleReceiveData(Arrays.copyOf(buffer, read));
                }
            }
        } catch (Exception e) {
            warn("Client disconnected: {} - {}", socket.getInetAddress().getHostAddress(), e.getMessage());
        } finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
