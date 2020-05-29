package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.CommandDispatcher;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.requireNonNull;

public class WorkerThread implements Runnable {
    private final Client client;
    private final Socket connection;
    private final CommandDispatcher dispatcher;

    public WorkerThread(@Nonnull Client client) {
        this.client = requireNonNull(client);
        this.connection = client.getControlConnection();
        this.dispatcher = new CommandDispatcher(client);
    }

    @Override
    public void run() {
        try {
            final BufferedWriter writer = createWriter(client.getOutputStream());
            final BufferedReader reader = createReader(client.getInputStream());

            while (client.isControlConnectionOpen()) {
                final ConcurrentLinkedQueue<Reply> replyBuffer = client.getBufferedReplies();
                final ConcurrentLinkedQueue<Command> commandBuffer = client.getBufferedCommands();

                Reply reply;
                while ((reply = replyBuffer.poll()) != null) {
                    writer.write(reply.toString());
                    Logger.debug("Server -> '{} {}'", reply.getCode(), reply.getText());
                }
                writer.flush();

                String line;
                while (reader.ready() && (line = reader.readLine()) != null) {
                    Logger.debug("Server <- '{}'", line);
                    client.receiveCommand(Command.valueOf(line));
                }

                Command command;
                while ((command = commandBuffer.poll()) != null) {
                    dispatcher.execute(command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Logger.debug("Closing control connection");
            try {
                client.closeControlConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private BufferedWriter createWriter(OutputStream stream) {
        return new BufferedWriter(new OutputStreamWriter(stream));
    }

    private BufferedReader createReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream));
    }
}