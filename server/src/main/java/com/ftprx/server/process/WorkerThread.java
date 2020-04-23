package com.ftprx.server.process;

import com.ftprx.server.Server;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.command.CommandDispatcher;

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
        System.out.println("worker thread created '" + client.getControlConnection().getInetAddress().getHostAddress() + "'");
        try {
            writer = createWriter(client.getOutputStream());
            reader = createReader(client.getInputStream());
            while (client.isControlConnectionOpen()) {
                final ConcurrentLinkedQueue<Reply> replyBuffer = client.getBufferedReplies();
                final ConcurrentLinkedQueue<Command> commandBuffer = client.getBufferedCommands();

                Reply reply;
                while ((reply = replyBuffer.poll()) != null) {
                    writer.write(reply.getCode() + " " + reply.getText() + "\r\n");
                    System.out.println("SERVER ---> '" + reply.getCode() + " " + reply.getText() + "'");
                }
                writer.flush();

                String line;
                while (reader.ready() && (line = reader.readLine()) != null) {
                    System.out.println("SERVER <--- '" + line + "'");

                    String SP = " ";
                    Command.CommandBuilder command = Command.CommandBuilder.aCommand();
                    if (line.contains(SP)) {
                        String[] tokens = line.split(SP);
                        command.withCode(tokens[0]);
                        for (int i = 1; i < tokens.length; i++) {
                            command.withArgument(tokens[i].trim());
                        }
                    } else {
                        command.withCode(line);
                    }
                    client.receiveCommand(command.build());
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
            System.out.println("closing control connection!");
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