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

import me.adiras.ftprx.command.Command;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CommandResolver {
    /*
        The RFC 765 syntax of the argument fields (using BNF notation where applicable) is:

        <username> ::= <string>
        <password> ::= <string>
        <account-information> ::= <string>
        <string> ::= <char> | <char><string>
        <char> ::= any of the 128 ASCII characters except <CR> and <LF>
        <marker> ::= <pr-string>
        <pr-string> ::= <pr-char> | <pr-char><pr-string>
        <pr-char> ::= printable characters, any ASCII code 33 through 126
        <byte-size> ::= <number>
        <host-port> ::= <host-number>,<port-number>
        <host-number> ::= <number>,<number>,<number>,<number>
        <port-number> ::= <number>,<number>
        <number> ::= any decimal integer 1 through 255
        <form-code> ::= N | T | C
        <type-code> ::= A [<sp> <form-code>]
                      | E [<sp> <form-code>]
                      | I
                      | L <sp> <byte-size>
        <structure-code> ::= F | R | P
        <mode-code> ::= S | B | C
        <pathname> ::= <string>
        <decimal-integer> ::= any decimal integer
     */

    private static String normalize(String grammar) {
        return grammar
                .replace(" ", "")
                .replace("<marker>", "<pr-string>")
                .replace("<pr-string>", "<pr-char>*")
                .replace("<pr-char>", "[\\x21-7e]")
                .replace("<form-code>", "[NTC]")
                .replace("<structure-code>", "[FRP]")
                .replace("<mode-code>", "[SBC]")
                .replace("<sp>", "<SP>")
                .replace("<SP>", "\\x20")
                .replace("<CRLF>", "\\x0D\\x0A")
                .replace("<host-port>", "<host-number>,<host-number>")
                .replace("<host-number>", "<number>,<number>,<number>,<number>")
                .replace("<port-number>", "<number>,<number>")
                .replace("<byte-size>", "<number>")
                .replace("<number>", "[1-255]")
                .replace("<decimal-integer>", "[0-9]+(.[0-9]*)")
                .replace("<username>", "<string>")
                .replace("<password>", "<string>")
                .replace("<pathname>", "<string>")
                .replace("<account-information>", "<string>")
                .replace("<string>", "<char>*")
                .replace("<char>", "[\\x00-\\x7F&&[^\\x0D\\x0A]]");
    }

    public static Command resolve(String request) {
        return Stream.of(Command.values())
                .filter(command -> Pattern.compile(normalize(command.getGrammar()))
                        .matcher(request)
                        .find())
                .findFirst()
                .orElse(null);
    }
}
