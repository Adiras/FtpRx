package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import javax.annotation.CheckForNull;

public class PrintWorkingDirectoryCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        String dir = client.getWorkingDirectory();
        if (dir != null)
        client.sendReply(257, dir);
    }

    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
