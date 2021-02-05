package com.ftprx.server.command;

import com.ftprx.server.channel.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import java.util.Arrays;

/**
 * This command causes the file specified in the pathname to be
 * deleted at the server site. If an extra level of protection
 * is desired (such as the query, "Do you really wish to delete?"),
 * it should be provided by the user-FTP process.
 */
public class HelpCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        client.sendReply(200,
                "Support the following ftp commands:" + '\n' +
                Arrays.toString(CommandCode.values()) + '\n' +
                "Help OK.");
    }
}
