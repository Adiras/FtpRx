package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import java.io.*;
import java.util.concurrent.Executors;

public class StoreCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
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
                        System.out.println("data = " + data);

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
