package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.PassiveConnectionMode;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import javax.annotation.CheckForNull;

public class PassiveCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(227, "Entering Passive Mode (127,0,0,1,204,174)."); // port 52398
        client.openDataConnection(new PassiveConnectionMode(52398));
    }

    @CheckForNull
    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
