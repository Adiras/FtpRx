package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/*
 * This command does not affect any parameters or previously
 * entered commands. It specifies no action other than that
 * the server send an OK reply.
 */
public class NoopCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(200, "OK.");
    }

    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
