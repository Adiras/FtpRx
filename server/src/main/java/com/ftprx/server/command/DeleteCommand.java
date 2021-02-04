package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This command causes the file specified in the pathname to be
 * deleted at the server site. If an extra level of protection
 * is desired (such as the query, "Do you really wish to delete?"),
 * it should be provided by the user-FTP process.
 */
public class DeleteCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final var pathname = command.getArgument();
        if (pathname.isEmpty()) {
            client.sendReply(501, "No specified pathname.");
            return;
        }
        try {
            String path = client.getWorkingDirectory() + File.separator + pathname;
            Files.delete(Path.of(path));
            client.sendReply(250, "Requested file action okay, completed.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
            client.sendReply(550, "Requested action not taken, file unavailable.");
        }
    }
}
