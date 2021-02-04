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
 *  This command causes a list to be sent from the server to the
 *  passive DTP. If the pathname specifies a directory or other
 *  group of files, the server should transfer a list of files
 *  in the specified directory. If the pathname specifies a
 *  file then the server should send current information on the
 *  file. A null argument implies the user's current working or
 *  default directory. The data transfer is over the data
 *  connection in type ASCII or type EBCDIC. (The user must
 *  ensure that the TYPE is appropriately ASCII or EBCDIC).
 *  Since the information on a file may vary widely from system
 *  to system, this information may be hard to use automatically
 *  in a program, but may be quite useful to a human user.
 */
public class ListCommand extends SimpleCommand {

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
