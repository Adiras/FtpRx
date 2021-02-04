package com.ftprx.server;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/**
 * Interface for classes that can execute an FTP command.
 */
public interface CommandExecutor {

    /**
     * Contains the business logic for the command being executed.
     * @param command the <code>command</code> to handle
     * @param client the <code>client</code> who sent the command
     */
    void execute(Command command, Client client);
}
