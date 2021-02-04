package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Objects;

public class DownloadFileProcess extends DataTransferProcess {
    private final String pathname;

    public DownloadFileProcess(@Nonnull Client client, @Nonnull String pathname) {
        super(client);
        this.pathname = Objects.requireNonNull(pathname, "Pathname should not be null");
    }

    @Override
    public void perform() {
        File file = new File(client.getWorkingDirectory() + File.separator + pathname);
        try {
            file.createNewFile();
            client.sendReply(150, "Ok to send data.");
            download(file);
            client.sendReply(226, "Transfer complete.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }

    private void download(File file) throws IOException {
        InputStream input = client.getDataConnection().getInputStream();
        InputStreamReader reader = new InputStreamReader(input);
        FileWriter writer = new FileWriter(file);
        int data;
        while ((data = reader.read()) != -1) {
            writer.append((char) data);
        }
        writer.flush();
    }
}
