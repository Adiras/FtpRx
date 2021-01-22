package com.ftprx.server.process;

import com.ftprx.server.channel.Client;

import javax.annotation.Nonnull;
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
    private static final long CONNECTION_TIMEOUT = 1000; //ms
    protected final Client client;

    public DataTransferProcess(@Nonnull Client client) {
        this.client = Objects.requireNonNull(client, "Client should not be null");
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
            e.printStackTrace();
        }
    }

    private void waitForConnection() {
        try {
            TimeUnit.MILLISECONDS.sleep(CONNECTION_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
