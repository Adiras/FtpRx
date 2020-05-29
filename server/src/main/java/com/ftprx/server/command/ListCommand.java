package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.process.ListingProcess;
import com.ftprx.server.thread.ThreadManager;

import java.util.concurrent.Executors;

public class ListCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(150, "Here comes the directory listing.");
        final String pathname = null;
        ThreadManager.launchDataTransferProcess(new ListingProcess(client, pathname));
    }
}
