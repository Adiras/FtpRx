package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import org.jetbrains.annotations.NotNull;
import org.tinylog.Logger;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;

public class UploadFileProcess extends DataTransferProcess {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private final File file;

    public UploadFileProcess(@NotNull Client client, @NotNull File file) {
        super(client);
        this.file = Objects.requireNonNull(file, "File must not be null");
    }

    @Override
    public void perform() {
        try {
            file.createNewFile();
            client.sendReply(150, "Ok to send data.");
            copyInputStreamToFile(client.getDataConnection().getInputStream(), file);
            client.sendReply(226, "Transfer complete.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    private void copyInputStreamToFile(InputStream input, File file) throws IOException {
        FileOutputStream output = new FileOutputStream(file);
        int data;
        while ((data = input.read()) != -1) {
            output.write(data);
        }
        output.flush();
        output.close();
    }
}
