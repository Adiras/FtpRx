package com.ftprx.server;

import javax.annotation.Nonnull;
import java.net.Socket;
import java.util.function.Consumer;

public interface ConnectionMode {
    void openConnection(@Nonnull Consumer<Socket> callback);
}
