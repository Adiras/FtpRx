package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

public class UsernameCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        final String username = command.getArgument();

        if (username == null) {
            client.sendReply(530, "Need parameter");
            return;
        }

        client.sendReply(331, "Password required for " + username);
    }
}
