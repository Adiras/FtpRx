package com.ftprx.server.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The commands are Telnet character strings transmitted over the control connections.
 * Example: 'USER <SP> admin <CRLF>' -> Command{code="USER", arguments="[admin]"}
 */
public class Command {
    /**
     * Command codes are four or fewer alphabetic characters.
     * Upper and lower case alphabetic characters are to be treated identically.
     */
    private final String code;

    /**
     * The arguments field consists of a variable length character string.
     * All characters in the arguments field are ASCII characters including any ASCII represented decimal integers.
     */
    private final List<String> arguments;

    private Command(String code, List<String> arguments) {
        this.code = code;
        this.arguments = arguments;
    }

    public String getCode() {
        return code;
    }

    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Command{" +
                "code='" + code + '\'' +
                ", arguments='" + arguments + '\'' +
                '}';
    }

    public static final class CommandBuilder {
        private String code;
        private List<String> argument;

        private CommandBuilder() {
            this.argument = new ArrayList<>();
        }

        public static CommandBuilder aCommand() {
            return new CommandBuilder();
        }

        public CommandBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public CommandBuilder withArgument(String argument) {
            this.argument.add(argument);
            return this;
        }

        public Command build() {
            Objects.requireNonNull(code, "command must contain code");
            return new Command(code, argument);
        }
    }
}
