package com.ftprx.server.thread;

import com.ftprx.server.util.SocketHelper;
import jdk.internal.jline.internal.Nullable;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class ListenerThread extends Thread {
    private static final String ERROR_SERVER_NULL = "Argument 'server' must not be null";
    private static final String THREAD_NAME = "Protocol Interpreter Listening Thread";

    private final Set<ClientConnectObserver> observers;
    private final ServerSocket server;

    public ListenerThread(@Nonnull ServerSocket server) {
        this.server = requireNonNull(server, ERROR_SERVER_NULL);
        this.observers = Collections.newSetFromMap(new ConcurrentHashMap<>());
        setName(THREAD_NAME);
    }

    @Override
    public void run() {
        Logger.info("Listening for connections on por " + server.getLocalPort());
        while (!Thread.currentThread().isInterrupted()) {
            if (SocketHelper.isServerSocketOpen(server)) {
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

    public void registerClientConnectObserver(@Nullable ClientConnectObserver observer) {
        Optional.ofNullable(observer).ifPresent(observers::add);
    }

    public void unregisterClientConnectObserver(@Nullable ClientConnectObserver observer) {
        Optional.ofNullable(observer).ifPresent(observers::remove);
    }

    private void notifyObservers(@Nonnull Socket client) {
        observers.forEach(observer -> observer.onClientConnected(client));
    }
}
