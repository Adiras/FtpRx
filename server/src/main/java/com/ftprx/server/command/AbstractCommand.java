package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import javax.annotation.CheckForNull;

public abstract class AbstractCommand {
    protected static final String[] ANY_COMMAND = null;

    public void onRegister() {
        // Empty method
    }

    public abstract void onCommand(Command command, Client client);

    @CheckForNull
    public abstract String[] dependency();

    public boolean hasAnyDependency() {
        return dependency() != ANY_COMMAND;
    }
}
