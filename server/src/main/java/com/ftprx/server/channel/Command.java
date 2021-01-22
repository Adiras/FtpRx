package com.ftprx.server.channel;

import com.ftprx.server.CommandCode;
import com.ftprx.server.account.AccountRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 * The commands are Telnet character strings transmitted over the control connections.
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
    private final String argument;

    public Command(@Nonnull String code, @Nullable String arguments) {
        this.code = requireNonNull(code);
        this.argument = arguments;
    }

    public @Nonnull String getCode() {
        return code;
    }

    public @Nullable String getArgument() {
        return argument;
    }

    public boolean hasArgument() {
        return argument != null;
    }

    public boolean equalsCode(CommandCode e) {
        return code.equals(e.name());
    }

    public static Command valueOf(String input) {
        int codeMaxLength = 4;
        int endIndex = Math.min(input.length(), codeMaxLength);
        String code = input.substring(0, endIndex).trim();
        String argument = null;
        if (input.trim().length() != code.length()) {
            argument = input.substring(code.length() + 1).trim();
        }
        return new Command(code, argument);
    }
}
