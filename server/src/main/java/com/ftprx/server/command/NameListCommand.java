package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.process.DirectoryListingTask;

import javax.annotation.CheckForNull;
import java.util.concurrent.Executors;

public class NameListCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(150, "Here comes the directory listing.");
        Executors.newCachedThreadPool().execute(new DirectoryListingTask(client, "ad"));

//        if (!client.isDataConnectionOpen()) {
//            client.sendReply(425, "Use PORT or PASV first.");
//        } else {
//            Executors.newCachedThreadPool().execute(() -> {
//                OutputStream out;
//                try {
//                    out = client.getDataConnection().getOutputStream();
//                    PrintWriter writer = new PrintWriter(out);
//                    writer.println("1000GB.zip");
//                    writer.println("100GB.zip");
//                    writer.println("10MB.zip");
//                    writer.println("500MB.zip");
//                    writer.println("upload");
//                    writer.flush();
//                    client.closeDataConnection();
//                    client.sendReply(226, "Directory send OK.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }

    @CheckForNull
    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
