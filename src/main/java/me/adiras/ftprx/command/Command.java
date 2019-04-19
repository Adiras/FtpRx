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

package me.adiras.ftprx.command;

import me.adiras.ftprx.command.rfc959.UserCommandHandler;

public enum Command {
    USER("USER", "USER <SP> <username> <CRLF>", UserCommandHandler.class),
    PASS("PASS", "PASS <SP> <username> <CRLF>"),
    ACCT("ACCT", "ACCT <SP> <account-information> <CRLF>");

    private final String label;
    private final String grammar;
    private final Class<? extends CommandHandler> handler;

    Command(String label, String grammar) {
        this(label, grammar, NullCommandHandler.class);
    }

    Command(String label, String grammar, Class<? extends CommandHandler> handler) {
        this.label = label;
        this.grammar = grammar;
        this.handler = handler;
    }

    public String getLabel() {
        return label;
    }

    public String getGrammar() {
        return grammar;
    }

    public Class<? extends CommandHandler> getHandler() {
        return handler;
    }
}
