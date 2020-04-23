package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * This command causes the directory specified in the pathname
 * to be created as a directory (if the pathname is absolute)
 * or as a subdirectory of the current working directory (if
 * the pathname is relative).
 */
public class MakeDirectoryCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        final String pathname = command.getArguments().get(0);
        if (pathname == null || pathname.equals("")) {
            client.sendReply(501, "Syntax error in parameters or arguments.");
        } else {
            try {
                Files.createDirectory(Paths.get(pathname));
                client.sendReply(257, "Directory created " + pathname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
