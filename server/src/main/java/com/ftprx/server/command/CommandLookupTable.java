package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Command;

import javax.annotation.Nonnull;
import java.util.HashMap;

import static java.util.Objects.requireNonNull;

public class CommandLookupTable extends HashMap<CommandCode, AbstractCommand> {

    public void registerCommand(@Nonnull CommandCode code, @Nonnull AbstractCommand command) {
        requireNonNull(code);
        requireNonNull(command);
        put(code, command);
    }

    public AbstractCommand getCommand(@Nonnull Command command) {
        requireNonNull(command);
        for (Entry<CommandCode, AbstractCommand> entry : entrySet()) {
            if (command.equalsCode(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean isCommandNotRegistered(@Nonnull Command command) {
        requireNonNull(command);
        return !isCommandRegistered(command);
    }

    public boolean isCommandRegistered(@Nonnull Command command) {
        requireNonNull(command);
        for (Entry<CommandCode, AbstractCommand> entry : entrySet()) {
            if (command.equalsCode(entry.getKey())) {
                return true;
            }
        }
        return false;
    }
}
