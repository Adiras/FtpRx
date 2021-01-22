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

import java.io.*;
import java.util.concurrent.Executors;

public class StoreCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final String pathname = command.getArgument();
        if (pathname == null || pathname.equals("")) {
            client.sendReply(553, "Could not create file.");
        } else {
            if (!client.isDataConnectionOpen()) {
                client.sendReply(425, "Use PORT or PASV first.");
            } else {
                File file = new File(client.getWorkingDirectory() + File.separator + pathname);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                client.sendReply(150, "Ok to send data.");
                Executors.newCachedThreadPool().execute(() -> {
                    try {
                        InputStream input = client.getDataConnection().getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);
                        FileWriter writer = new FileWriter(file);

                        int character;
                        StringBuilder data = new StringBuilder();

                        while ((character = reader.read()) != -1) {
                            writer.append((char) character);
                        }
                        writer.flush();

                        client.closeDataConnection();
                        client.sendReply(226, "Transfer complete.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
