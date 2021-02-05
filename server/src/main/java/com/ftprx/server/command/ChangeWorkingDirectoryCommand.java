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
import com.ftprx.server.channel.WorkingDirectoryChangeException;

import java.io.File;
import java.util.logging.Logger;

/*
 * This command allows the user to work with a different
 * directory or dataset for file storage or retrieval without
 * altering his login or accounting information. Transfer
 * parameters are similarly unchanged. The argument is a
 * pathname specifying a directory or other system dependent
 * file group designator.
 */
public class ChangeWorkingDirectoryCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final var pathname = command.getArgument();
        if (pathname.isEmpty()) {
            client.sendReply(501, "Syntax error in parameters or arguments."); // bug in mozilla ftp
            return;
        }
        try {
            client.changeWorkingDirectory(client.getRemotePath(pathname));
            client.sendReply(200, "Directory changed to " + client.getWorkingDirectory());
        } catch (WorkingDirectoryChangeException e) {
            client.sendReply(421, e.getMessage());
        }
    }
}
