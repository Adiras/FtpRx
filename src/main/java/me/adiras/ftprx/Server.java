package me.adiras.ftprx;

import java.util.logging.Logger;

import static me.adiras.ftprx.util.ControlCharacters.*;

public class Server extends MultithreadServer implements ServerContext, NetworkListener {
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private CommandDispatcher dispatcher;

    public Server() {
        super(new ServerSocketFactory(null));
        this.dispatcher = new CommandDispatcher(this);
        setListener(this);
    }

    @Override
    public void onRequestReceive(Connection connection, String request) {
        int indexOfSP = request.indexOf(SP);
        int pivot = (indexOfSP == -1 ? request.length() : indexOfSP);

        String command = request.substring(0, pivot);
        String argument = request.substring(pivot + 1);

        dispatcher.handleCommand(connection, command, argument);
    }

    @Override
    public void onConnectionEstablishment(Connection connection) {
        connection.sendResponse(Response.builder()
                .code("220") // Service ready for new user
                .argument("FTP Server ready")
                .build());
    }

    @Override
    public ServerConfig getConfig() {
        return null;
    }
}
