package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Objects;

public class DownloadFileProcess extends DataTransferProcess {
    private final File file;

    public DownloadFileProcess(@Nonnull Client client, @Nonnull File file) {
        super(client);
        this.file = Objects.requireNonNull(file, "File must not be null");
    }

    @Override
    public void perform() {
        try {
            client.sendReply(150, "Ok to send data.");
            copyFileToOutputStream(file, client.getDataConnection().getOutputStream());
            client.sendReply(226, "Transfer complete.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    private void copyFileToOutputStream(File file, OutputStream output) throws IOException {
        FileInputStream input = new FileInputStream(file);
        int data;
        while ((data = input.read()) != -1) {
            output.write(data);
        }
        output.flush();
        output.close();
    }
}
