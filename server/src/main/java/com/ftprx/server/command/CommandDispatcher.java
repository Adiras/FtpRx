package com.ftprx.server.command;

import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountRepository;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.ControlConnection;
import com.ftprx.server.channel.Reply;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ftprx.server.util.CommandConstants.*;

public class CommandDispatcher {
    private final AccountRepository accountRepository;
    private final Map<String, CommandController> controllers;

    public CommandDispatcher(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.controllers = new HashMap<>();

        bind(OPTS, this::commandNotImplemented);
        bind(USER, this::user);
        bind(XPWD, this::pwd);
        bind(PWD , this::pwd);
        bind(NOOP, this::noop);
        bind(PASS, this::pass);
    }

    public void execute(Command command, ControlConnection connection) {
        final String code = command.getCode();
        if (controllers.containsKey(code.toUpperCase())) {
            controllers.get(code).handle(command, connection);
        } else {
            commandNotImplemented(null, connection);
        }
    }

    private void bind(String code, CommandController controller) {
        controllers.put(code, controller);
    }

    private void commandNotImplemented(Command command, ControlConnection connection) {
        connection.sendReply(Reply.ReplyBuilder.aReply()
                .withCode("502")
                .withText("Command not implemented")
                .build());
    }

    private void user(Command command, ControlConnection connection) {
        UserCommand user = new UserCommand(command);
        if (user.getUsername() == null) {
            connection.sendReply(Reply.ReplyBuilder.aReply()
                    .withCode("501")
                    .withText("Syntax error in parameters or arguments")
                    .build());
            return;
        }

        if (connection.isLogged()) {
            connection.sendReply(530, "You are already logged");
            return;
        }

        Optional<Account> account = accountRepository.findAccountByUsername(user.getUsername());
        if (account.isPresent()) {
            connection.login(account.get());
            connection.sendReply(230, "User logged in, proceed");
        } else {
            connection.sendReply(530, "Wrong username");
        }
    }

    private void pass(Command command, ControlConnection connection) {

    }

    /**
     * This command causes the name of the current
     * working directory to be returned in the reply.
     *
     * Command syntax: PWD <CRLF>
     */
    private void pwd(Command command, ControlConnection connection) {
        connection.getAccount().ifPresent(account -> {
            String directory = String.format("\"%s\"", connection.getWorkingDirectory().orElse("null"));
            connection.sendReply(257, directory);
        });
    }

    /**
     * This command does not affect any parameters or previously
     * entered commands. It specifies no action other than that the
     * server send an OK reply.
     *
     * Command syntax: NOOP <CRLF>
     */
    private void noop(Command command, ControlConnection connection) {
        connection.sendReply(200, "Command okay");
    }
}
