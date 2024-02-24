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

package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import org.jetbrains.annotations.NotNull;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * The data transfer process, in its normal "active" state,
 * establishes the data connection with the listening data port.
 * It sets up parameters for transfer and storage,
 * and transfers data on command from its PI.
 * The DTP can be placed in a "passive" state to listen for,
 * rather than initiate a connection on the data port.
 */
public abstract class DataTransferProcess implements Runnable {
    private static final long CONNECTION_TIMEOUT = 2_000; // ms
    protected final Client client;

    public DataTransferProcess(@NotNull Client client) {
        Objects.requireNonNull(client, "Client must not be null");
        this.client = client;
    }

    public abstract void perform();

    @Override
    public void run() {
        waitForConnection();
        if (!client.isDataConnectionOpen()) {
            client.sendReply(425, "Use PORT or PASV first.");
        } else {
            final Socket connection = client.getControlConnection();
            if (connection.isInputShutdown()) {
                client.sendReply(551, "Requested action aborted: DTP input stream is closed.");
                return;
            }
            if (connection.isOutputShutdown()) {
                client.sendReply(551, "Requested action aborted: DTP output stream is closed.");
                return;
            }
            perform();
            postComplete();
        }
    }

    private void postComplete() {
        try {
            client.closeDataConnection();
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    private void waitForConnection() {
        try {
            TimeUnit.MILLISECONDS.sleep(CONNECTION_TIMEOUT);
        } catch (InterruptedException e) {
            Logger.error(e.getMessage());
        }
    }
}
