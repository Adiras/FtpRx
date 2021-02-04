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
import com.ftprx.server.process.ListingProcess;
import com.ftprx.server.thread.ThreadManager;

import java.io.File;

/**
 * This command causes a directory listing to be sent from
 * server to user.  The pathname should specify a
 *             directory or other system-specific file group descriptor; a
 *             null argument implies the current directory.  The server
 *             will return a stream of names of files and no other
 *             information.  The data will be transferred in ASCII or
 *             EBCDIC type over the data connection as valid pathname
 *             strings separated by <CRLF> or <NL>.  (Again the user must
 *             ensure that the TYPE is correct.)  This command is intended
 *             to return information that can be used by a program to
 *             further process the files automatically.  For example, in
 *             the implementation of a "multiple get" function.
 */
public class NameListCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        var pathname = command.getArgument();
        File directory = pathname.isEmpty()
                ? client.getWorkingDirectory().toFile()
                : client.getRemotePath(pathname).toFile();
        client.sendReply(150, "Here comes the directory listing.");
        ThreadManager.launchDataTransferProcess(new ListingProcess(client, directory));
    }
}
