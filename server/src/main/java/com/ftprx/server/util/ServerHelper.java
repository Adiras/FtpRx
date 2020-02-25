package com.ftprx.server.util;

import java.net.ServerSocket;

public class ServerHelper {
    public static boolean isOpen(ServerSocket server) {
        return server != null && server.isBound() && !server.isClosed();
    }
}
