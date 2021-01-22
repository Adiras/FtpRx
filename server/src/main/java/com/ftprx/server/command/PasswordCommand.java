package com.ftprx.server.command;

import com.ftprx.server.Server;
import com.ftprx.server.account.Account;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/**
 * The argument field is a Telnet string specifying the user's
 * password. This command must be immediately preceded by the
 * user name command, and, for some sites, completes the user's
 * identification for access control.
 */
public class PasswordCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        // Password may be empty so null is acceptable
        String password = command.getArgument();

        // Login failed because user has not enter the USER command before
        if (!client.hasSelectedUsername()) {
            client.sendReply(530, "Please input your username first");
            return;
        }

        Account selectedAccount = getServerInstance()
                .getAccountRepository()
                .findByUsername(client.getSelectedUsername());

        // Login failed because an account with the specified username doesn't exist
        if (selectedAccount == null) {
            client.sendReply(530, "Permission denied");
            return;
        }

        if (!selectedAccount.isPasswordRequired()) {
            // Login should succeed because password checking is not required
            client.login(selectedAccount);
            client.sendReply(230, "User logged in");
        } else {
            if (selectedAccount.verifyPassword(password)) {
                // Login should succeed
                client.login(selectedAccount);
                client.sendReply(230, "User logged in");
            } else {
                // login should fail because of wrong password
                client.sendReply(530, "Permission denied");
            }
        }
    }
}
