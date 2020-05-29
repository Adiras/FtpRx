package com.ftprx.server;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class CommandDispatcher {
    private final CommandLookupTable commandLookupTable;
    private final Client client;

    /**
     * Create a new {@link CommandDispatcher} instance.
     * @param client the client that is assigned for this dispatcher.
     */
    public CommandDispatcher(@Nonnull Client client) {
        this.client = requireNonNull(client, "Client should not be null");
        this.commandLookupTable = CommandLookupTable.bootstrap();
    }

    public void execute(Command command) {
        if (commandLookupTable.isCommandNotRegistered(command)) {
            onExecuteUnknownCommand(command, client);
            return;
        }
        commandLookupTable.getCommand(command).onCommand(command, client);
//        if (handler.isRequireDependency()) {
//            final Command clientLastCommand = client.getLastCommand();
//            if (clientLastCommand == null) {
//                onExecuteWithoutDependCommand(command, client);
//            } else {
//                boolean atLeastOneDependency = false;
//                for (CommandCode dependency : handler.dependency()) {
//                    if (dependency.name().equals(clientLastCommand.getCode())) {
//                        atLeastOneDependency = true;
//                    }
//                }
//                if (!atLeastOneDependency) {
//                    onExecuteWithoutDependCommand(command, client);
//                }
//            }
//        }
    }

    public void onExecuteWithoutDependCommand(Command command, Client client) {
        client.sendReply(503, "Bad sequence of commands.");
    }

    public void onExecuteUnknownCommand(Command command, Client client) {
        client.sendReply(502, "Command not implemented.");
    }
}
