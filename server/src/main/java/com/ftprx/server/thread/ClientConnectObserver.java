package com.ftprx.server.thread;

import java.net.Socket;

@FunctionalInterface
public interface ClientConnectObserver {
    void onClientConnected(Socket client);
}
