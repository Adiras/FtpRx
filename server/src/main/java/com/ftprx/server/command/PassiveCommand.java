package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import javax.annotation.CheckForNull;

public class PassiveCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        // wysłanie polecenia z tym kodem powoduje rozłącznie się klienta FTP
        client.sendReply(200, "=127,0,0,1,204,174");
    }

    @CheckForNull
    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
