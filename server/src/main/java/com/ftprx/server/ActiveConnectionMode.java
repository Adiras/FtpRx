package com.ftprx.server;

import java.io.IOException;
import java.net.Socket;

public class ActiveConnectionMode implements ConnectionMode {
    private final String host;
    private final int port;

    public ActiveConnectionMode(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Socket getDataConnection() {
        try {
            return new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
