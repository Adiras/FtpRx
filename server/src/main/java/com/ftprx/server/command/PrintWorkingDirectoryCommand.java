package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

public class PrintWorkingDirectoryCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        String dir = client.getWorkingDirectory();
        if (dir != null)
        client.sendReply(257, dir);
    }

    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
