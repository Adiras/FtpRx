package com.ftprx.server.command;

import com.ftprx.server.Server;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/**
 * The interface definition for a FTP Command.
 * Your subclass should implement the <code>execute</code> method.
 */
public abstract class SimpleCommand {

    /**
     * Contains the business logic for the command being executed.
     *
     * @param command the <code>command</code> to handle.
     * @param client the <code>client</code> who sent the command.
     */
    public abstract void execute(Command command, Client client);

    protected Server getServerInstance() {
        return Server.getInstance();
    }
}
