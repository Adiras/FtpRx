package com.ftprx.server.process;

import com.ftprx.server.channel.Client;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class DataTransferListingProcess extends DataTransferProcess {
    private final String pathname;

    public DataTransferListingProcess(@Nonnull Client client, @Nullable String pathname) {
        super(requireNonNull(client));
        this.pathname = requireNonNull(pathname);
    }

    @Override
    public void perform() {
        System.out.println("data connection status: " + client.isDataConnectionOpen()); //debug
        System.out.println("listning process pathname = " + pathname); //debug
        try {
            PrintWriter writer = new PrintWriter(client.getDataConnection().getOutputStream());
            Files.walk(Paths.get(client.getWorkingDirectory()))
                    .forEach(path -> writer.write(path.toString()));
            writer.println("_1000GB.zip");
            writer.println("_100GB.zip");
            writer.println("_10MB.zip");
            writer.println("_500MB.zip");
            writer.println("_upload");
            writer.flush();
            client.sendReply(226, "Directory send OK.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
