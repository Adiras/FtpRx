/*
 * Copyright 2019, FtpRx Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftprx.server.channel;

import com.ftprx.server.CommandCode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import java.util.Objects;

/**
 * The commands are Telnet character strings transmitted over the control connections.
 * Objects of this class are immutable.
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

    public Command(@Nonnull String code) {
        this(code, null);
    }

    public Command(@Nonnull String code, @Nullable String arguments) {
        this.code = Objects.requireNonNull(code);
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

    public static Command createCommand(String input) {
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
