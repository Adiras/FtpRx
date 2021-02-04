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

package com.ftprx.server.command;

import com.ftprx.server.ActiveConnectionMode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutionException;

/*
 * The argument is a HOST-PORT specification for the data port
 * to be used in data connection. There are defaults for both
 * the user and server data ports, and under normal
 * circumstances this command and its reply are not needed.
 * If this command is used, the argument is the concatenation of a
 * 32-bit internet host address and a 16-bit TCP port address.
 * This address information is broken into 8-bit fields and the
 * value of each field is transmitted as a decimal number (in
 * character string representation).
 * The fields are separated by commas. A port command would be:
 *      PORT h1,h2,h3,h4,p1,p2
 * where h1 is the high order 8 bits of the internet host address.
 */
public class DataPortCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final var hostPort = command.getArgument(); // "192,168,1,105,223,91"
        if (hostPort.isEmpty()) {
            client.sendReply(501, "Syntax error in parameters or arguments.");
            return;
        }

        final String[] fields = hostPort.split(","); // ["192","168","1","105","223","91"]
        client.openDataConnection(new ActiveConnectionMode(payloadHostname(fields), payloadPort(fields)));
        client.sendReply(200, "PORT command successful.");
    }

    private String payloadHostname(String[] fields) {
        return String.format("%s.%s.%s.%s", fields[0], fields[1], fields[2], fields[3]);
    }

    private int payloadPort(String[] fields) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        // by choosing big endian, high order bytes must be put
        // to the buffer before low order bytes
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        // since ints are 4 bytes (32 bit), you need to put all 4,
        // so put 0 for the high order bytes
        int p1 = Integer.parseInt(fields[4]);
        int p2 = Integer.parseInt(fields[5]);
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) p1);
        byteBuffer.put((byte) p2);
        byteBuffer.flip();
        return byteBuffer.getInt();
    }
}
