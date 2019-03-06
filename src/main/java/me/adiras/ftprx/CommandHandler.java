package me.adiras.ftprx;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.ServerContext;

public interface CommandHandler {
    void process(ServerContext context, Connection connection, String arguments);
}
