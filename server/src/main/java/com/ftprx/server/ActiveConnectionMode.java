package com.ftprx.server;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

public class ActiveConnectionMode implements ConnectionMode {
    private final String host;
    private final int port;

    /**
     * Create a new {@link ActiveConnectionMode} instance.
     * @param host the host name, or {@code null} for the loopback address.
     * @param port the port number.
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
     * @param callback operation that will be perform after a successful connection.
     */
    @Override
    public void openConnection(@Nonnull Consumer<Socket> callback) {
        Thread connectionThread = new Thread(() -> {
            try (Socket socket = new Socket(host, port)) {
                callback.accept(socket);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectionThread.start();
    }
}
