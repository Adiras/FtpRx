package com.ftprx.server;

import javax.annotation.Nonnull;
import java.net.Socket;
import java.util.function.Consumer;

public interface ConnectionMode {
    /**
     * Opens the data connection between client and server.
     * The method blocks thread until a connection is made.
     * @param callback operation that will be perform after a successful connection.
     */
    void openConnection(@Nonnull Consumer<Socket> callback);
}
