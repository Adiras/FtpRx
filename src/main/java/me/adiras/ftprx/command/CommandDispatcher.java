package me.adiras.ftprx.command;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.command.rfc959.UserCommandHandler;
import me.adiras.ftprx.core.ServerContext;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandDispatcher {
    private static final Logger logger = Logger.getLogger(CommandDispatcher.class.getName());

    private final HashMap<String, CommandHandler> dispatcher = new HashMap<>();
    private ServerContext context;

    public CommandDispatcher(ServerContext context) {
        this.context = context;
        initializeCommands();
    }

    private void initializeCommands() {
        registerCommand("USER", UserCommandHandler.class);
    }

    public void handleCommand(Connection connection, String command, String argument) {
        CommandHandler handler = findHandlerByCommandName(command);
        if (Objects.isNull(handler)) {
            logger.log(Level.WARNING, "{0} command is not supported", command);
            return;
        }
        handler.process(context, connection, argument);
    }

    private CommandHandler findHandlerByCommandName(String commandName) {
        return dispatcher.get(commandName);
    }

    public void registerCommand(String command, Class<? extends CommandHandler> handlerClass) {
        try {
            dispatcher.put(command, handlerClass.newInstance());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to register command handler: {0}", e.getMessage());
        }
    }
}
