package com.ftprx.server;

import java.net.Socket;
import java.util.function.Consumer;

public interface ConnectionMode {
    void openConnection(Consumer<Socket> callback); //TODO: zamiast void niech coś zwraca, np wątek
}
