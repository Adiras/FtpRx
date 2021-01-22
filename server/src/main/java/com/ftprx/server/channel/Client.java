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

import com.ftprx.server.CommandCode;
import com.ftprx.server.ConnectionMode;
import com.ftprx.server.account.Account;
import com.ftprx.server.util.SocketHelper;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.requireNonNull;

/**
 * The communication path between the user protocol interpreter and
 * server protocol interpreter for the exchange of commands and replies.
 */
public class Client {
    /**
     * The client socket on which the current connection is operating.
     */
    private final Socket controlConnection;

    private Socket dataConnection;

    /**
     * Queues that store commands and replies that should be handled by worker thread.
     */
    private ConcurrentLinkedQueue<Command> commandBuffer;
    private ConcurrentLinkedQueue<Reply> replyBuffer;

    private Account account;
    private String workingDirectory;

    private String selectedUsername;

    public Client(@Nonnull Socket controlConnection) {
        this.controlConnection = requireNonNull(controlConnection);
        commandBuffer = new ConcurrentLinkedQueue<>();
        replyBuffer = new ConcurrentLinkedQueue<>();
    }

    public void login(@Nonnull Account account) {
        this.account = requireNonNull(account);
        changeWorkingDirectory(account.getHomeDirectory());
    }

    public boolean hasSelectedUsername() {
        return selectedUsername != null;
    }

    public String getSelectedUsername() {
        return selectedUsername;
    }

    public void setSelectedUsername(String selectedUsername) {
        this.selectedUsername = selectedUsername;
    }

    public void logout() {
        account = null;
        changeWorkingDirectory(null);
    }

    public boolean isLoggedIn() {
        return account != null;
    }

    public void changeWorkingDirectory(@Nullable String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    @CheckForNull
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    @CheckForNull
    public Account getAccount() {
        return account;
    }

    public void receiveCommand(@Nullable Command command) {
        if (command != null) {
            commandBuffer.add(command);
//            if (!command.equalsCode(CommandCode.PASS)) {
//                selectedUsername = null;
//            }
        }
    }

    public void sendReply(Integer code, String text) {
        sendReply(new Reply(code.toString(), text));
    }

    public void sendReply(@Nullable Reply reply) {
        Optional.ofNullable(reply).ifPresent(replyBuffer::add);
    }

    public InputStream getInputStream() throws IOException {
        return controlConnection.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return controlConnection.getOutputStream();
    }

    @Nullable
    public Socket getDataConnection() {
        return dataConnection;
    }

    public void openDataConnection(@Nonnull ConnectionMode mode) {
        mode.openConnection(this::setDataConnection);
    }

    public void closeDataConnection() throws IOException {
        dataConnection.close();
    }

    @Nonnull
    public Socket getControlConnection() {
        return controlConnection;
    }

    public void closeControlConnection() throws IOException {
        controlConnection.close();
    }

    public void setDataConnection(@Nonnull Socket dataConnection) {
        this.dataConnection = requireNonNull(dataConnection);
    }

    public ConcurrentLinkedQueue<Command> getBufferedCommands() {
        return commandBuffer;
    }

    public ConcurrentLinkedQueue<Reply> getBufferedReplies() {
        return replyBuffer;
    }

    public boolean isControlConnectionOpen() {
        return SocketHelper.isSocketOpen(controlConnection);
    }

    public boolean isDataConnectionOpen() {
        return SocketHelper.isSocketOpen(dataConnection);
    }
}
