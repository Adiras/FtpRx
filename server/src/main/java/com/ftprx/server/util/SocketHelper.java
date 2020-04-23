package com.ftprx.server.util;

import jdk.internal.jline.internal.Nullable;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketHelper {
    public static boolean isServerSocketOpen(@Nullable ServerSocket socket) {
        return socket != null && socket.isBound() && !socket.isClosed();
    }

    public static boolean isSocketOpen(@Nullable Socket socket) {
        return socket != null && socket.isBound() && !socket.isClosed();
    }
}
