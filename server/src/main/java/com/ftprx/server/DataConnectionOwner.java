package com.ftprx.server;

import java.net.Socket;

public interface DataConnectionOwner {
    void establishDataConnection(Socket socket);
}
