package me.adiras.ftprx.command;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.core.ServerContext;

public interface CommandHandler {
    void process(ServerContext context, Connection connection, String argument);
}
