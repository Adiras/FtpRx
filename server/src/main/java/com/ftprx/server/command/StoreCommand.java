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
import com.ftprx.server.process.UploadFileProcess;
import com.ftprx.server.thread.ThreadManager;

/**
 * This command causes the server-DTP to accept the data
 * transferred via the data connection and to store the data as
 * a file at the server site. If the file specified in the
 * pathname exists at the server site, then its contents shall
 * be replaced by the data being transferred. A new file is
 * created at the server site if the file specified in the
 * pathname does not already exist.
 */
public class StoreCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final String pathname = command.getArgument();
        if (pathname.isEmpty()) {
            client.sendReply(553, "Could not create file.");
            return;
        }
        var file = client.getRemotePath(pathname).toFile();
        ThreadManager.launchDataTransferProcess(new UploadFileProcess(client, file));
    }
}
