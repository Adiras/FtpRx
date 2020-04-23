package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import com.ftprx.server.process.DataTransferProcess;
import jdk.internal.jline.internal.Nullable;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.PrintWriter;

public class DataTransferListingProcess extends DataTransferProcess {
    private final String pathname;

    public DataTransferListingProcess(@Nonnull Client client, @Nullable String pathname) {
        super(client);
        this.pathname = pathname;
    }

    @Override
    public void perform() {
        try {
            PrintWriter writer = new PrintWriter(client.getDataConnection().getOutputStream());
            writer.println("1000GB.zip");
            writer.println("100GB.zip");
            writer.println("10MB.zip");
            writer.println("500MB.zip");
            writer.println("upload");
            writer.flush();
            client.sendReply(226, "Directory send OK.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
