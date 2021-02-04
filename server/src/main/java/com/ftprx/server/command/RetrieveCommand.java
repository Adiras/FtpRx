package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.process.DownloadFileProcess;
import com.ftprx.server.thread.ThreadManager;

/**
 * This command causes the server-DTP to transfer a copy of the
 * file, specified in the pathname, to the server or user-DTP
 * at the other end of the data connection. The status and
 * contents of the file at the server site shall be unaffected.
 */
public class RetrieveCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final var pathname = command.getArgument();
        if (pathname.isEmpty()) {
            client.sendReply(501, "Syntax error in parameters or arguments.");
            return;
        }
        var file = client.getRemotePath(pathname).toFile();
        ThreadManager.launchDataTransferProcess(new DownloadFileProcess(client, file));
    }
}
