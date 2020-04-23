package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/*
 * The argument specifies the representation type as described
 * in the Section on Data Representation and Storage.  Several
 * types take a second parameter. The first parameter is
 * denoted by a single Telnet character, as is the second
 * Format parameter for ASCII and EBCDIC; the second parameter
 * for local byte is a decimal integer to indicate Bytesize.
 * The parameters are separated by a <SP> (Space, ASCII code 32).
 *
 *      A - ASCII |    | N - Non-print
 *                |-><-| T - Telnet format effectors
 *      E - EBCDIC|    | C - Carriage Control (ASA)
 *                /    \
 *      I - Image
 *
 *      L <byte size> - Local byte Byte size
 *
 * The default representation type is ASCII Non-print.  If the
 * Format parameter is changed, and later just the first
 * argument is changed, Format then returns to the Non-print default.
 */
public class RepresentationTypeCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
        client.sendReply(200, "Command okay.");
    }

    @Override
    public CommandCode[] dependency() {
        return ANY;
    }
}
