package com.ftprx.server.util;

import java.net.ServerSocket;

public class SocketHelper {
    public static boolean isOpen(ServerSocket socket) {
        return socket != null && socket.isBound() && !socket.isClosed();
    }
}
