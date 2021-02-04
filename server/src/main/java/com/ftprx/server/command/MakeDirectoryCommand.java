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
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * This command causes the directory specified in the pathname
 * to be created as a directory (if the pathname is absolute)
 * or as a subdirectory of the current working directory (if
 * the pathname is relative).
 */
public class MakeDirectoryCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final String pathname = command.getArgument();
        if (pathname == null || pathname.equals("")) {
            client.sendReply(501, "Syntax error in parameters or arguments.");
        } else {
            try {
                Files.createDirectory(Paths.get(pathname));
                client.sendReply(257, "Directory created " + pathname);
            } catch (IOException e) {
                Logger.error(e.getMessage());
            }
        }
    }
}
