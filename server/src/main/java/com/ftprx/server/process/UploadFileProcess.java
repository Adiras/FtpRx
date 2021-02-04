package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Objects;

public class UploadFileProcess extends DataTransferProcess {
    private final String pathname;

    public UploadFileProcess(@Nonnull Client client, @Nonnull String pathname) {
        super(client);
        this.pathname = Objects.requireNonNull(pathname, "Pathname should not be null");
    }

    @Override
    public void perform() {
        File file = new File(client.getWorkingDirectory() + File.separator + pathname);
        try {
            file.createNewFile();
            client.sendReply(150, "Ok to send data.");
            upload(file);
            client.sendReply(226, "Transfer complete.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    private void upload(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        OutputStream output = client.getDataConnection().getOutputStream();
        int data;
        while ((data = input.read()) != -1) {
            output.write((char) data);
        }
        output.flush();
    }
}
