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

package me.adiras.ftprx;

import java.util.regex.Pattern;

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

    public static String normalize(String grammar) {
        return grammar                                                                                // You can use expressions on the right and below
                .replace(" ", "")                                                   // <marker>
                .replace("<marker>", "<pr-string>")                                 // <pr-string>
                .replace("<pr-string>", "<pr-char>*")                               // <pr-char>
                .replace("<pr-char>", "[\\x21-7e]")                                 // <form-code>
                .replace("<form-code>", "[NTC]")                                    // <structure-code>
                .replace("<structure-code>", "[FRP]")                               // <mode-code>
                .replace("<mode-code>", "[SBC]")                                    // <sp>
                .replace("<sp>", "<SP>")                                            // <SP>
                .replace("<SP>", "\\x20")                                           // <CRLF>
                .replace("<CRLF>", "\\x0D\\x0A")                                    // <host-port>
                .replace("<host-port>", "<host-number>,<host-number>")              // <host-number>
                .replace("<host-number>", "<number>,<number>,<number>,<number>")    // <port-number>
                .replace("<port-number>", "<number>,<number>")                      // <byte-size>
                .replace("<byte-size>", "<number>")                                 // <number>
                .replace("<number>", "[1-255]")                                     // <decimal-integer>
                .replace("<decimal-integer>", "[0-9]+(.[0-9]*)")                    // <username>
                .replace("<username>", "<string>")                                  // <password>
                .replace("<password>", "<string>")                                  // <pathname>
                .replace("<pathname>", "<string>")                                  // <account-information>
                .replace("<account-information>", "<string>")                       // <string>
                .replace("<string>", "<char>*")                                     // <char>
                .replace("<char>", "[\\x00-\\x7F&&[^\\x0D\\x0A]]");                 // ...
    }

    public static Command resolve(String request) {
        for (Command c : Command.values()) {
            if (Pattern.compile(normalize(c.getGrammar()))
                    .matcher(request).find()) {
                return c;
            }
        }
        return null;
    }
}
