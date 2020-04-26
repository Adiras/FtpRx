package com.ftprx.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

public class ActiveConnectionMode implements ConnectionMode {
    private final String host;
    private final int port;

    public ActiveConnectionMode(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void openConnection(Consumer<Socket> callback) {
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
