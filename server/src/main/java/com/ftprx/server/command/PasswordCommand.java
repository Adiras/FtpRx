package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;
import com.ftprx.server.util.CommandConstants;

public class PasswordCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        System.out.println("PasswordCommand.handleCommand");
    }

    @Override
    public String[] dependency() {
        return new String[] {
                CommandConstants.USER
        };
    }
}
