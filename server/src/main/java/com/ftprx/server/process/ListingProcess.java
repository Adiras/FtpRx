package com.ftprx.server.process;

import com.ftprx.server.channel.Client;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class ListingProcess extends DataTransferProcess {
    private final String pathname;

    public ListingProcess(@Nonnull Client client, @Nullable String pathname) {
        super(requireNonNull(client));
        this.pathname = pathname;
    }

    @Override
    public void perform() {
        try {
            PrintWriter writer = new PrintWriter(client.getDataConnection().getOutputStream());
            try {
                Files.list(Paths.get(client.getWorkingDirectory()))
                        .limit(10)
                        .forEach(path -> {
                            writer.write(path.getFileName().toString());
                            System.out.println("path = " + path.getFileName().toString());
                        });
                writer.println("_EOF_");
                writer.flush();
                client.sendReply(226, "Directory send OK.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
