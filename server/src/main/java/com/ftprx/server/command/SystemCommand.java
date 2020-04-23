package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

public class SystemCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(215, "System information. TODO: implement");
    }

    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
