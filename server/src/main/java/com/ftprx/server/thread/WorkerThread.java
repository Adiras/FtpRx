package com.ftprx.server.thread;

import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.ControlConnection;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.command.CommandDispatcher;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerThread implements Runnable {
    /**
     * Connection to which the thread is assigned.
     */
    private final ControlConnection connection;
    private final CommandDispatcher dispatcher;

    private BufferedWriter writer;
    private BufferedReader reader;

    public WorkerThread(ControlConnection connection, CommandDispatcher dispatcher) {
        this.connection = Objects.requireNonNull(connection, "Client cannot be null");
        this.dispatcher = Objects.requireNonNull(dispatcher, "Dispatcher cannot be null");
    }

    @Override
    public void run() {
        System.out.println("WORKER THREAD CREATED '" + connection.getClient().getInetAddress().getHostAddress() + "'");
        try {
            writer = createWriter(connection.getOutputStream());
            reader = createReader(connection.getInputStream());
            while (connection.isOpen()) {
                final ConcurrentLinkedQueue<Reply> replyBuffer = connection.getBufferedReplies();
                final ConcurrentLinkedQueue<Command> commandBuffer = connection.getBufferedCommands();

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
                    connection.receiveCommand(command.build());
                }

                Command command;
                while ((command = commandBuffer.poll()) != null) {
                    dispatcher.execute(command, connection);
                }
            }
        } catch (IOException e) {
            /* Do nothing */
        } finally {
            System.out.println("ControlConnection.close");
            try {
                connection.close();
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