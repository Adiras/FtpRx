package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import javax.annotation.Nonnull;
import java.util.*;

public class CommandDispatcher {
    private Map<CommandCode, AbstractCommand> commandLookupTable;

    public CommandDispatcher() {
        this.commandLookupTable = new HashMap<>();
    }

    public void executeCommand(Command command, Client client) {
        if (isCommandNotRegistered(command)) {
            onExecuteUnknownCommand(command, client);
            return;
        }

        final AbstractCommand handler = getHandlerAssignTo(command);
        handler.onCommand(command, client);
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

    public void registerCommand(@Nonnull CommandCode code, @Nonnull AbstractCommand handler) {
        commandLookupTable.put(code, handler);
        handler.onRegister();
    }

    private AbstractCommand getHandlerAssignTo(@Nonnull Command command) {
        for (Map.Entry<CommandCode, AbstractCommand> entry : commandLookupTable.entrySet()) {
            if (command.equalsCode(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean isCommandNotRegistered(@Nonnull Command command) {
        return !isCommandRegistered(command);
    }

    public boolean isCommandRegistered(@Nonnull Command command) {
        for (Map.Entry<CommandCode, AbstractCommand> entry : commandLookupTable.entrySet()) {
            if (command.equalsCode(entry.getKey())) {
                return true;
            }
        }
        return false;
    }
}
