package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.WorkingDirectoryChangeException;

/**
 * This command is a special case of CWD, and is included to
 * simplify the implementation of programs for transferring
 * directory trees between operating systems having
 * different syntaxes for naming the parent directory.
 * The reply codes shall be identical to the reply codes of CWD.
 */
public class ChangeToParentDirectoryCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        try {
            client.changeWorkingDirectory(client.getRemotePath(".."));
            client.sendReply(200, "Directory changed to " + client.getWorkingDirectory());
        } catch (WorkingDirectoryChangeException e) {
            client.sendReply(421, e.getMessage());
        }
    }
}
