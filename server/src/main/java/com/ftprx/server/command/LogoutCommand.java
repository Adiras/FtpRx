package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import javax.annotation.CheckForNull;
import java.io.IOException;

public class LogoutCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        try {
            client.closeControlConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @CheckForNull
    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
