package me.adiras.ftprx;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketFactory {
    private ServerConfig config;

    public ServerSocketFactory(ServerConfig config) {
        this.config = config;
    }

    public ServerSocket createServerSocket() throws IOException {
        return new ServerSocket(21, 50, InetAddress.getByName("127.0.0.1"));
    }
}
