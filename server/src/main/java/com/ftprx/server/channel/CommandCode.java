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

/**
 * Contains shortcuts for predefined server command codes.
 * If possible, they should be used in place of text values.
 */
public enum CommandCode {
    USER ("USER <SP> <username> <CRLF>"),
    PASS ("PASS <SP> <password> <CRLF>"),
    ACCT ("ACCT <SP> <account-information> <CRLF>"),
    CWD  ("CWD  <SP> <pathname> <CRLF>"),
    CDUP ("CDUP <CRLF>"),
    SMNT ("SMNT <SP> <pathname> <CRLF>"),
    QUIT ("QUIT <CRLF>"),
    REIN ("REIN <CRLF>"),
    PORT ("PORT <SP> <host-port> <CRLF>"),
    PASV ("PASV <CRLF>"),
    TYPE ("TYPE <SP> <type-code> <CRLF>"),
    STRU ("STRU <SP> <structure-code> <CRLF>"),
    MODE ("MODE <SP> <mode-code> <CRLF>"),
    RETR ("RETR <SP> <pathname> <CRLF>"),
    STOR ("STOR <SP> <pathname> <CRLF>"),
    STOU ("STOU <CRLF>"),
    APPE ("APPE <SP> <pathname> <CRLF>"),
    ALLO ("ALLO <SP> <decimal-integer> [<SP> R <SP> <decimal-integer>] <CRLF>"),
    REST ("REST <SP> <marker> <CRLF>"),
    RNFR ("RNFR <SP> <pathname> <CRLF>"),
    RNTO ("RNTO <SP> <pathname> <CRLF>"),
    ABOR ("ABOR <CRLF>"),
    DELE ("DELE <SP> <pathname> <CRLF>"),
    RMD  ("RMD  <SP> <pathname> <CRLF>"),
    MKD  ("MKD  <SP> <pathname> <CRLF>"),
    PWD  ("PWD  <CRLF>"),
    LIST ("LIST [<SP> <pathname>] <CRLF>"),
    NLST ("NLST [<SP> <pathname>] <CRLF>"),
    SITE ("SITE <SP> <string> <CRLF>"),
    SYST ("SYST <CRLF>"),
    STAT ("STAT [<SP> <pathname>] <CRLF>"),
    HELP ("HELP [<SP> <string>] <CRLF>"),
    NOOP ("NOOP <CRLF>");

    private final String syntax;

    CommandCode(String syntax) {
        this.syntax = syntax;
    }

    public String getSyntax() {
        return syntax;
    }
}
