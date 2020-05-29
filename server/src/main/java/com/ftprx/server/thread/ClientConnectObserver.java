package com.ftprx.server.thread;

import java.net.Socket;

@FunctionalInterface
public interface ClientConnectObserver {
    /**
     * Called when new client has been connected to the server.
     * @param client the new client which has been connected.
     */
    void onClientConnected(Socket client);
}
