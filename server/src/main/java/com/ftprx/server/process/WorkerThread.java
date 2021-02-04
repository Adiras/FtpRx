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

package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;
import com.ftprx.server.channel.Reply;
import com.ftprx.server.CommandDispatcher;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkerThread implements Runnable {
    private final Client client;
    private final Socket connection;
    private final CommandDispatcher dispatcher;

    public WorkerThread(@Nonnull Client client) {
        this.client = Objects.requireNonNull(client);
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
                    Logger.debug("--> '{} {}'", reply.getCode(), reply.getText());
                }
                writer.flush();

                String line;
                while (reader.ready() && (line = reader.readLine()) != null) {
                    Logger.debug("<-- '{}'", line);
                    client.receiveCommand(Command.createCommand(line));
                }

                Command command;
                while ((command = commandBuffer.poll()) != null) {
                    dispatcher.execute(command);
                }
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        } finally {
            Logger.debug("Closing control connection");
            try {
                client.closeControlConnection();
            } catch (IOException e) {
                Logger.error(e.getMessage());
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