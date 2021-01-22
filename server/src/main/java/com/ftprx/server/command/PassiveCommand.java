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

import com.ftprx.server.PassiveConnectionMode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.util.SocketHelper;

public class PassiveCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        final int port = SocketHelper.findAvailableTcpPort(51000, 60000);

        byte[] portBytes = new byte[4];
        portBytes[0] = (byte) ((port & 0xFF000000) >> 24);
        portBytes[1] = (byte) ((port & 0x00FF0000) >> 16);
        portBytes[2] = (byte) ((port & 0x0000FF00) >> 8);
        portBytes[3] = (byte) ((port & 0x000000FF) >> 0);

        String text = String.format("Entering Passive Mode (127,0,0,1,%d,%d).",
            Byte.toUnsignedInt(portBytes[2]), Byte.toUnsignedInt(portBytes[3]));

        client.sendReply(227, text);
        client.openDataConnection(new PassiveConnectionMode(port));
    }
}
