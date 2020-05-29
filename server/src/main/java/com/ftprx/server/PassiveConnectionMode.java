package com.ftprx.server;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

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
     * @param callback operation that will be perform after a successful connection.
     */
    @Override
    public void openConnection(@Nonnull Consumer<Socket> callback) {
        Thread connectionThread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                callback.accept(socket.accept());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectionThread.start();
    }
}
