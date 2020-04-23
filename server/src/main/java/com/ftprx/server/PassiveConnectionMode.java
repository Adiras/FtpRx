package com.ftprx.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PassiveConnectionMode implements ConnectionMode {
    private final int port;

    public PassiveConnectionMode(int port) {
        this.port = port;
    }

    @Override
    public Socket getDataConnection() {
        ServerSocket listenerSocket = null;
        try {
            listenerSocket = new ServerSocket(port);
            listenerSocket.setSoTimeout(15000);
            return listenerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (listenerSocket != null) {
                    listenerSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
