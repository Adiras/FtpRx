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

package com.ftprx.server.channel;

import com.ftprx.server.account.Account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The communication path between the user protocol interpreter and
 * server protocol interpreter for the exchange of commands and replies.
 */
public class ControlConnection {
    /**
     * The client socket on which the current connection is operating.
     */
    private final Socket client;

    /**
     * Queues that store commands and replies that should be handled by worker thread.
     */
    private ConcurrentLinkedQueue<Command> commandBuffer;
    private ConcurrentLinkedQueue<Reply> replyBuffer;

    private Account account;
    private String workingDirectory;
    private Command lastCommand;

    public ControlConnection(Socket client) {
        this.client = Objects.requireNonNull(client, "Client cannot be null");
        commandBuffer = new ConcurrentLinkedQueue<>();
        replyBuffer = new ConcurrentLinkedQueue<>();
    }

    public boolean isLogged() {
        return getAccount().isPresent();
    }

    public void login(Account account) {
        this.account = account;
        changeWorkingDirectory(account.getHomeDirectory());
    }

    public void logout() {
        this.account = null;
    }

    public Optional<Command> getLastCommand() {
        return Optional.ofNullable(lastCommand);
    }

    public void changeWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public Optional<String> getWorkingDirectory() {
        return Optional.ofNullable(workingDirectory);
    }

    public Optional<Account> getAccount() {
        return Optional.ofNullable(account);
    }

    public void receiveCommand(Command command) {
        Objects.requireNonNull(command, "Command cannot be null");
        commandBuffer.add(command);
        lastCommand = command;
    }

    public void sendReply(Integer code, String text) {
        sendReply(Reply.ReplyBuilder
                .aReply()
                .withCode(code.toString())
                .withText(text)
                .build());
    }

    public void sendReply(Reply reply) {
        Objects.requireNonNull(reply, "Reply cannot be null");
        replyBuffer.add(reply);
    }

    public InputStream getInputStream() throws IOException {
        return client.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return client.getOutputStream();
    }

    public Socket getClient() {
        return client;
    }

    public void close() throws IOException {
        client.close();
    }

    public boolean isOpen() {
        return !client.isClosed() && client.isConnected();
    }

    public ConcurrentLinkedQueue<Command> getBufferedCommands() {
        return commandBuffer;
    }

    public ConcurrentLinkedQueue<Reply> getBufferedReplies() {
        return replyBuffer;
    }
}
