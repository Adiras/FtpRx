package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.process.DataTransferListingProcess;

import java.util.concurrent.Executors;

public class ListCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(150, "Here comes the directory listing.");
        final String pathname = null;
        Executors.newCachedThreadPool()
                .execute(new DataTransferListingProcess(client, pathname));
    }
}
