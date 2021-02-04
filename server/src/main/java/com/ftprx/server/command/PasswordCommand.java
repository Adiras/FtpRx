/*
 * Copyright 2019, FtpRx Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftprx.server.command;

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
        // password may be empty so null is acceptable
        String password = command.getArgument();
        // login failed because user has not enter the USER command before
        if (!client.hasSelectedUsername()) {
            client.sendReply(530, "Please input your username first");
            return;
        }
        Account selectedAccount = getServerInstance().getAccountRepository()
                .findByUsername(client.getSelectedUsername());
        // login failed because an account with the specified username doesn't exist
        if (selectedAccount == null) {
            client.sendReply(530, "Permission denied");
            return;
        }
        if (!selectedAccount.isPasswordRequired()) {
            // login should succeed because password checking is not required
            client.login(selectedAccount);
            client.sendReply(230, "User logged in");
        } else {
            if (selectedAccount.verifyPassword(password)) {
                // login should succeed
                client.login(selectedAccount);
                client.sendReply(230, "User logged in");
            } else {
                // login should fail because of wrong password
                client.sendReply(530, "Permission denied");
            }
        }
    }
}
