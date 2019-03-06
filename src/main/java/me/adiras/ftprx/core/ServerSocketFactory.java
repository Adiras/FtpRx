package me.adiras.ftprx.core;

import me.adiras.ftprx.ServerProperties;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketFactory {
    public ServerSocket createServerSocket() throws IOException {
        int port = Integer.parseInt(ServerProperties.getProperty("server.port"));
        return new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"));
    }
}
