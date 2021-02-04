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

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/**
 * The argument field is a Telnet string identifying the user.
 * The user identification is that which is required by the
 * server for access to its file system. This command will
 * normally be the first command transmitted by the user after
 * the control connections are made (some servers may require
 * this). Additional identification information in the form of
 * a password and/or an account command may also be required by
 * some servers. Servers may allow a new USER command to be
 * entered at any point in order to change the access control
 * and/or accounting information. This has the effect of
 * flushing any user, password, and account information already
 * supplied and beginning the login sequence again. All
 * transfer parameters are unchanged and any file transfer in
 * progress is completed under the old access control
 * parameters.
 */
public class UsernameCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final var username = command.getArgument();
        if (username.isEmpty()) {
            client.sendReply(530, "Need parameter");
            return;
        }
        client.setSelectedUsername(username);
        client.sendReply(331, "Password required for " + username);
    }
}
