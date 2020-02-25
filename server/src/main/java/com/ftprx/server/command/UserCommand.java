package com.ftprx.server.command;

import com.ftprx.server.channel.Command;

public class UserCommand {
    private final String username;

    public UserCommand(Command command) {
        this.username = command.getArguments().get(0);
    }

    public String getUsername() {
        return username;
    }
}
