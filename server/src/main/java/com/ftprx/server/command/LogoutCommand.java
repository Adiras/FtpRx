package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import java.io.IOException;

public class LogoutCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        try {
            client.sendReply(221, "Goodbye.");
            client.closeControlConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
