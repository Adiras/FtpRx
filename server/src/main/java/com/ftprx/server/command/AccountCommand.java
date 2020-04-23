package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;
import com.ftprx.server.util.CommandConstants;

import javax.annotation.CheckForNull;

public class AccountCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {

    }

    @Override
    public String[] dependency() {
        return new String[] {
                CommandConstants.USER,
                CommandConstants.PASS
        };
    }
}
