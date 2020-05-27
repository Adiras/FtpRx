package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

public class PrintWorkingDirectoryCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        String dir = client.getWorkingDirectory();
        if (dir != null)
        client.sendReply(257, dir);
    }
}
