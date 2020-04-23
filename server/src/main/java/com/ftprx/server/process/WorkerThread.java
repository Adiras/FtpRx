package com.ftprx.server.process;

import com.ftprx.server.CommandCode;
import com.ftprx.server.Server;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.command.CommandDispatcher;
import com.ftprx.server.util.ControlCharacters;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerThread implements Runnable {
    private final Client client;
    private final Socket connection;
    private final CommandDispatcher dispatcher;

    private BufferedWriter writer;
    private BufferedReader reader;

    public WorkerThread(Client client) {
        this.client = Objects.requireNonNull(client, "Client cannot be null");
        this.connection = client.getControlConnection();
        this.dispatcher = Objects.requireNonNull(Server.getInstance().getDispatcher(), "Dispatcher cannot be null");
    }

    @Override
    public void run() {
        try {
            writer = createWriter(client.getOutputStream());
            reader = createReader(client.getInputStream());
            while (client.isControlConnectionOpen()) {
                final ConcurrentLinkedQueue<Reply> replyBuffer = client.getBufferedReplies();
                final ConcurrentLinkedQueue<Command> commandBuffer = client.getBufferedCommands();

                Reply reply;
                while ((reply = replyBuffer.poll()) != null) {
                    writer.write(reply.toString());
                    System.out.println("SERVER ---> '" + reply.getCode() + " " + reply.getText() + "'"); //debug
                }
                writer.flush();

                String line;
                while (reader.ready() && (line = reader.readLine()) != null) {
                    System.out.println("SERVER <--- '" + line + "'");
                    client.receiveCommand(Command.valueOf(line));
                }

                Command command;
                while ((command = commandBuffer.poll()) != null) {
                    dispatcher.executeCommand(command, client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            /* Do nothing */
        } finally {
            System.out.println("closing control connection!"); //debug
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