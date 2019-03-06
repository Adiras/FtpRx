package me.adiras.ftprx.core;

import me.adiras.ftprx.*;
import me.adiras.ftprx.command.CommandDispatcher;
import me.adiras.ftprx.command.UserService;

import java.lang.reflect.Array;
import java.util.logging.Logger;

import static me.adiras.ftprx.util.ControlCharacters.*;

public class Server extends MultithreadServer implements ServerContext, NetworkListener {
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private UserService userService = new UserService();
    private CommandDispatcher dispatcher;

    public Server() {
        super(new ServerSocketFactory());
        this.dispatcher = new CommandDispatcher(this);
        setListener(this);
    }

    @Override
    public void onRequestReceive(Connection connection, String request) {
        int indexOfSP = request.indexOf(SP);
        int pivot = (indexOfSP == -1 ? request.length() : indexOfSP);

        String command = request.substring(0, pivot);
        String argument = request.substring(pivot + 1);

        // ---------- DEBUGGING ----------
        System.out.println("[" + command + "] " + argument);
        // -------------------------------
        dispatcher.handleCommand(connection, command, argument);
    }

    @Override
    public void onConnectionEstablishment(Connection connection) {
        connection.sendResponse(Response.builder()
                .code("220")
                .argument("Service ready for new user.")
                .build());
    }

    @Override
    public UserService getUserService() {
        return userService;
    }
}
