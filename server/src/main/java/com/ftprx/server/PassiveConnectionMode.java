package com.ftprx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class PassiveConnectionMode implements ConnectionMode {
    private final int port;

    public PassiveConnectionMode(int port) {
        this.port = port;
    }

    @Override
    public void openConnection(DataConnectionHandler handler) {
        Thread connectionThread = new Thread(() -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                handler.acceptDataConnection(socket.accept());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectionThread.start();
    }
}
