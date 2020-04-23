package com.ftprx.server.process;

import com.ftprx.server.channel.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DirectoryListingTask extends DataTransferProcess {
    private String pathname;

    public DirectoryListingTask(Client client, String pathname) {
        super(client);
        this.pathname = pathname;
    }

    @Override
    public void submit() {
        OutputStream out;
        try {
            out = client.getDataConnection().getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            writer.println("1000GB.zip");
            writer.println("100GB.zip");
            writer.println("10MB.zip");
            writer.println("500MB.zip");
            writer.println("upload");
            writer.flush();
            client.closeDataConnection();
            client.sendReply(226, "Directory send OK.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
