package com.ftprx.server;

import com.ftprx.server.channel.Command;
import com.ftprx.server.command.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Supplier;

import static com.ftprx.server.CommandCode.*;
import static com.ftprx.server.CommandCode.LIST;
import static java.util.Objects.requireNonNull;

public class CommandLookupTable extends HashMap<CommandCode, SimpleCommand> {

    /**
     * Create a new {@link CommandLookupTable} instance with pre-registered commands.
     */
    @Nonnull
    public static CommandLookupTable bootstrap() {
        return new CommandLookupTable() {{
            registerCommand(USER, UsernameCommand::new);
            registerCommand(PASS, PasswordCommand::new);
            registerCommand(ACCT, AccountCommand::new);
            registerCommand(PWD,  PrintWorkingDirectoryCommand::new);
            registerCommand(XPWD, PrintWorkingDirectoryCommand::new);
            registerCommand(PORT, DataPortCommand::new);
            registerCommand(STOR, StoreCommand::new);
            registerCommand(NLST, NameListCommand::new);
            registerCommand(QUIT, LogoutCommand::new);
            registerCommand(PASV, PassiveCommand::new);
            registerCommand(CWD,  ChangeWorkingDirectoryCommand::new);
            registerCommand(MKD,  MakeDirectoryCommand::new);
            registerCommand(SYST, SystemCommand::new);
            registerCommand(TYPE, RepresentationTypeCommand::new);
            registerCommand(NOOP, NoopCommand::new);
            registerCommand(LIST, ListCommand::new);
        }};
    }

    /**
     * Assign the {@link CommandCode} with given {@link SimpleCommand}.
     */
    public void registerCommand(@Nonnull CommandCode code,
                                @Nonnull Supplier<SimpleCommand> commandSupplier) {
        registerCommand(code, commandSupplier.get());
    }

    /**
     * Assign the {@link CommandCode} with given {@link SimpleCommand}.
     */
    public void registerCommand(@Nonnull CommandCode code,
                                @Nonnull SimpleCommand command) {
        put(requireNonNull(code), requireNonNull(command));
    }

    /**
     * @param command the command which {@link CommandCode} is looking for.
     * @return the {@link SimpleCommand} associated with the given command,
     *         or null if the command is not registered.
     */
    @Nullable
    public SimpleCommand getCommand(@Nonnull Command command) {
        for (Entry<CommandCode, SimpleCommand> entry : entrySet()) {
            if (command.equalsCode(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * @param command the command which {@link CommandCode} is looking for.
     * @return true if the command is not registered in this {@link CommandLookupTable}.
     */
    public boolean isCommandNotRegistered(@Nonnull Command command) {
        return !isCommandRegistered(command);
    }

    /**
     * @param command the command which {@link CommandCode} is looking for.
     * @return true if the command is registered in this {@link CommandLookupTable}.
     */
    public boolean isCommandRegistered(@Nonnull Command command) {
        for (Entry<CommandCode, SimpleCommand> entry : entrySet()) {
            if (command.equalsCode(entry.getKey())) {
                return true;
            }
        }
        return false;
    }
}
