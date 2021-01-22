package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

public class UsernameCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final String username = command.getArgument();
        if (username == null) {
            client.sendReply(530, "Need parameter");
            return;
        }
        client.setSelectedUsername(username);
        client.sendReply(331, "Password required for " + username);
    }
}
