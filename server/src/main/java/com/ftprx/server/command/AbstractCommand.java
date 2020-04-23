package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

public abstract class AbstractCommand {
    protected static final CommandCode[] ANY = null;

    public void onRegister() {
        // Empty method
    }

    public abstract void onCommand(Command command, Client client);

    public abstract CommandCode[] dependency();

    public boolean isRequireDependency() {
        return dependency() != ANY;
    }
}
