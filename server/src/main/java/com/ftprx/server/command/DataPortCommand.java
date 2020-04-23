package com.ftprx.server.command;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DataPortCommand extends AbstractCommand {
    @Override
    public void onCommand(Command command, Client client) {
        String hostname = "127.0.0.1";

        String[] args = command.getArguments().get(0).split(",");
        int p1 = Integer.parseInt(args[4]);
        int p2 = Integer.parseInt(args[5]);

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
        // by choosing big endian, high order bytes must be put
        // to the buffer before low order bytes
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        // since ints are 4 bytes (32 bit), you need to put all 4,
        // so put 0 for the high order bytes
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) 0x00);
        byteBuffer.put((byte) p1);
        byteBuffer.put((byte) p2);
        byteBuffer.flip();
        int port = byteBuffer.getInt();

        client.openActiveDataConnection(hostname, port);
        if (client.isDataConnectionOpen()) {
            client.sendReply(200, "PORT command successful.");
        }
    }

    @Override
    public String[] dependency() {
        return ANY_COMMAND;
    }
}
