package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

public class PasswordCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        // TODO: executing 'PASS ' command gives empty argument instead of null
//        Command previousCommand = client.getLastCommand();
//        if (!(previousCommand != null || previousCommand.equalsCode(CommandCode.USER))) {
//            client.sendReply(530, "Permission denied");
//            return;
//        }

//        String username = previousCommand.getArgument();
//        if (username == null) {
//            client.sendReply(530, "Permission denied");
//            return;
//        }

        Server facade = Server.getInstance();
        final Account account = facade.getAccountRepository().findByUsername("admin");

//        if (account == null) {
//            client.sendReply(530, "Permission denied");
//            return;
//        }

        client.login(account);
        client.sendReply(230, "User logged in");
    }
}
