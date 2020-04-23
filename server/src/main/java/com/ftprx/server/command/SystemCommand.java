package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/*
 * This command is used to find out the type of operating
 * system at the server. The reply shall have as its first
 * word one of the system names listed in the current version
 * of the Assigned Numbers document
 */
public class SystemCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        //TODO Implement code
        client.sendReply(215, "UNIX Type: L8");
    }

    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
