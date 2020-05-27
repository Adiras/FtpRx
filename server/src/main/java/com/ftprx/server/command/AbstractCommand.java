package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

public abstract class AbstractCommand {
    public abstract void onCommand(Command command, Client client);
}
