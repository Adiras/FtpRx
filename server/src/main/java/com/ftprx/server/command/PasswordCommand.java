package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

public class PasswordCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        System.out.println("PasswordCommand.handleCommand");
    }

    @Override
    public CommandCode[] dependency() {
        return new CommandCode[] {
                CommandCode.USER
        };
    }
}
