package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

public class AccountCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {

    }

    @Override
    public CommandCode[] dependency() {
        return new CommandCode[] {
                CommandCode.USER,
                CommandCode.PASS
        };
    }
}
