package com.ftprx.server.command;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import javax.annotation.CheckForNull;

public class UsernameCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        Server facade = Server.getInstance();
        final Account adminAccount = facade.getAccountRepository()
                .findAccountByUsername("admin");

        if (adminAccount != null) {
            client.login(adminAccount);
            client.sendReply(230, "User logged in");
        }
    }

    @CheckForNull
    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
