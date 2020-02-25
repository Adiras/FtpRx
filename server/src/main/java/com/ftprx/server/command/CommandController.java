package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.ControlConnection;

@FunctionalInterface
public interface CommandController {
    void handle(Command command, ControlConnection connection);
}
