package com.ftprx.server;

import java.net.Socket;

public interface DataConnectionHandler {
    void acceptDataConnection(Socket socket);
}
