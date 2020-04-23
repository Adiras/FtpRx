package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import java.util.*;

public class CommandDispatcher {
    private Map<String, AbstractCommand> commandLookupTable;

    public CommandDispatcher() {
        this.commandLookupTable = new HashMap<>();
    }

    public void registerCommand(String commandCode, AbstractCommand handler) {
        commandLookupTable.put(commandCode, handler);
        handler.onRegister();
    }

    private AbstractCommand getHandlerAssignTo(Command command) {
        return commandLookupTable.get(command.getCode());
    }

    public void executeCommand(Command command, Client connection) {
        if (isCommandNotRegistered(command)) {
            onExecuteUnknownCommand(command, connection);
            return;
        }

        final AbstractCommand handler = getHandlerAssignTo(command);
        if (handler.hasAnyDependency()) {
            final Command clientLastCommand = connection.getLastCommand();
            if (clientLastCommand == null) {
                onExecuteWithoutDependCommand(command, connection);
            } else {
                boolean atLeastOneDependency = false;
                for (String dependency : handler.dependency()) {
                    if (dependency.equals(clientLastCommand.getCode())) {
                        atLeastOneDependency = true;
                    }
                }
                if (!atLeastOneDependency) {
                    onExecuteWithoutDependCommand(command, connection);
                }
            }
        }
        handler.onCommand(command, connection);
    }

    public void onExecuteWithoutDependCommand(Command command, Client connection) {
        connection.sendReply(503, "Bad sequence of commands");
    }

    public void onExecuteUnknownCommand(Command command, Client connection) {
        connection.sendReply(502, "Command not implemented");
    }

    public boolean isCommandNotRegistered(Command command) {
        return !isCommandRegistered(command);
    }

    public boolean isCommandRegistered(Command command) {
        return commandLookupTable.containsKey(command.getCode());
    }
}
