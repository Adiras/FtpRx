package com.ftprx.server;

import com.ftprx.server.channel.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ActiveConnectionMode implements ConnectionMode {
    private final String host;
    private final int port;

    public ActiveConnectionMode(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void openConnection(DataConnectionHandler handler) {
        Thread connectionThread = new Thread(() -> {
            try (Socket socket = new Socket(host, port)) {
                handler.acceptDataConnection(socket);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectionThread.start();
    }
}
