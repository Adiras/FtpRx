package com.ftprx.server.command;

import com.ftprx.server.CommandCode;
import com.ftprx.server.PassiveConnectionMode;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.util.SocketHelper;

import javax.annotation.CheckForNull;

public class PassiveCommand extends AbstractCommand {

    @Override
    public void onCommand(Command command, Client client) {
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
