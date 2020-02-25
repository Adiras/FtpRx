package com.ftprx.server.thread;

import com.ftprx.server.util.ServerHelper;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ListenerThread extends Thread {
    private static final String THREAD_NAME = "Protocol Interpreter Listening Thread";

    private final Set<ClientConnectObserver> observers;
    private final ServerSocket server;

    public ListenerThread(ServerSocket server) {
        this.server = Objects.requireNonNull(server, "Server cannot be null");
        this.observers = Collections.newSetFromMap(new ConcurrentHashMap<>());
        setName(THREAD_NAME);
    }

    @Override
    public void run() {
        Logger.info("Listening for connections on por " + server.getLocalPort());
        while (!Thread.currentThread().isInterrupted()) {
            if (ServerHelper.isOpen(server)) {
                try {
                    Socket client = server.accept();
                    notifyObservers(client);
                } catch (SocketTimeoutException ignore) {
                    /* Do nothing */
                } catch (IOException e) {
                    Logger.error("Error while waiting for client: {}", e.getMessage());
                }
            }
        }
    }

    public void registerClientConnectObserver(ClientConnectObserver observer) {
        Optional.ofNullable(observer).ifPresent(observers::add);
    }

    public void unregisterClientConnectObserver(ClientConnectObserver observer) {
        Optional.ofNullable(observer).ifPresent(observers::remove);
    }

    private void notifyObservers(Socket client) {
        observers.forEach(observer -> observer.onClientConnected(client));
    }
}
