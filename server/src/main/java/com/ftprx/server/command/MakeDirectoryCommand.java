package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/*
 * This command causes the directory specified in the pathname
 * to be created as a directory (if the pathname is absolute)
 * or as a subdirectory of the current working directory (if
 * the pathname is relative).
 */
public class MakeDirectoryCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final String pathname = command.getArgument();
        if (pathname == null || pathname.equals("")) {
            client.sendReply(501, "Syntax error in parameters or arguments.");
        } else {
            client.sendReply(257, "Directory created " + pathname);
//            try {
//                Files.createDirectory(Paths.get(pathname));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
