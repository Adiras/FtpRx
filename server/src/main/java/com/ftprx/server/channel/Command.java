package com.ftprx.server.channel;

import com.ftprx.server.CommandCode;
import com.ftprx.server.util.ControlCharacters;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The commands are Telnet character strings transmitted over the control connections.
 * Example: 'USER <SP> admin <CRLF>' -> Command{code=CommandCode.USER, arguments="[admin]"}
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

    public Command(String code, List<String> arguments) {
        this.code = code;
        this.arguments = arguments;
    }

    public String getCode() {
        return code;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public String getFirstArgument() {
        return getArgument(0);
    }

    public String getArgument(int index) {
        return arguments.get(index);
    }

    public boolean equalsCode(CommandCode c) {
        return code.equals(c.name());
    }

    public static Command valueOf(String str) {
        CommandBuilder command = CommandBuilder.aCommand();
        int codeMaxLength = 4;
        int endIndex = Math.min(str.length(), codeMaxLength);
        final String code = str.substring(0, endIndex).trim();
        command.withCode(code.toUpperCase());

        if (!str.contains(ControlCharacters.SP)) {
            return command.build();
        }

        final String args = str.substring(code.length() + 1);
        for (String arg : args.split(ControlCharacters.SP)) {
            if (!arg.equals(ControlCharacters.SP)) {
                command.withArgument(arg);
            }
        }
        return command.build();
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
