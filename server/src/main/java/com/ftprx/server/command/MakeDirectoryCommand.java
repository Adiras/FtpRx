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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        final var pathname = command.getArgument();
        if (pathname.isEmpty()) {
            client.sendReply(501, "No specified pathname.");
            return;
        }
        try {
            var directory = client.getRemotePath(pathname).toFile();
            if (directory.exists()) {
                client.sendReply(521, directory + "directory already exists");
                return;
            }
            Files.exists(Paths.get("sdf"));
            Files.createDirectory(directory.toPath());
            // upon successful completion of an MKD command,
            // the server should return a line of the form:
            // 257<space>"<directory-name>"<space><commentary>
            client.sendReply(257, directory + "directory created");
        } catch (IOException e) {
            Logger.error(e.getMessage());
            client.sendReply(550, "Requested action not taken, file unavailable.");
        }
    }
}
