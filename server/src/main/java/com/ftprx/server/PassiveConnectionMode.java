package com.ftprx.server;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.function.Consumer;

public class PassiveConnectionMode implements ConnectionMode {
    private final int port;

    public PassiveConnectionMode(int port) {
        this.port = port;
    }

    @Override
    public void openConnection(@Nonnull Consumer<Socket> callback) {
        Objects.requireNonNull(callback);
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
