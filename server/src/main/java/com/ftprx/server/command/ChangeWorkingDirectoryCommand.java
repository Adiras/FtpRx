package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/*
 *
 * This command allows the user to work with a different
 * directory or dataset for file storage or retrieval without
 * altering his login or accounting information. Transfer
 * parameters are similarly unchanged. The argument is a
 * pathname specifying a directory or other system dependent
 * file group designator.
 */
public class ChangeWorkingDirectoryCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        final String pathname = command.getArguments().get(0);
        if (pathname == null || pathname.equals("")) {
            client.sendReply(501, "Syntax error in parameters or arguments.");
        } else {
            client.changeWorkingDirectory(pathname);
            client.sendReply(200, "Directory changed to " + pathname);
        }
    }

    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
