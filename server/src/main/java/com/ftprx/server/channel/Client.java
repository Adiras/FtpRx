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

import com.ftprx.server.ConnectionMode;
import com.ftprx.server.account.Account;
import com.ftprx.server.util.SocketHelper;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.requireNonNull;

/**
 * The communication path between the user protocol interpreter and
 * server protocol interpreter for the exchange of commands and replies.
 */
public class Client {

    /**
     * The communication path between the user and server
     * for the exchange of commands and replies.
     * This connection follows the Telnet Protocol.
     */
    private final Socket controlConnection;

    /**
     * A full duplex connection over which data is transferred,
     * in a specified mode and type. The data transferred may be a part
     * of a file, an entire file or a number of files.
     */
    private Socket dataConnection;

    /**
     * Queues that store commands and replies that should be handled by worker thread.
     */
    private final ConcurrentLinkedQueue<Command> commandBuffer;
    private final ConcurrentLinkedQueue<Reply> replyBuffer;

    private Account account;
    private Path workingDirectory;
    private String selectedUsername;

    public Client(@Nonnull Socket controlConnection) {
        this.controlConnection = requireNonNull(controlConnection);
        commandBuffer = new ConcurrentLinkedQueue<>();
        replyBuffer = new ConcurrentLinkedQueue<>();
    }

    public void login(@Nonnull Account account) {
        Objects.requireNonNull(account, "Account must not be null");
        this.account = account;
        workingDirectory = Paths.get(account.getHomeDirectory());
    }

    public void logout() {
        account = null;
        workingDirectory = null;
    }

    public boolean isLoggedIn() {
        return account != null;
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



    public void changeWorkingDirectory(@Nullable Path workingDirectory) throws WorkingDirectoryChangeException {
        if (!Files.exists(workingDirectory))
            throw new WorkingDirectoryChangeException("Directory not exists.");
        // the normalize method removes any redundant elements, which includes any ".."
        this.workingDirectory = workingDirectory.normalize();
    }

    @CheckForNull
    public Path getWorkingDirectory() {
        return workingDirectory;
    }

    @CheckForNull
    public Account getAccount() {
        return account;
    }

    public void receiveCommand(@Nullable Command command) {
//        Optional.ofNullable(command)
//                .ifPresent(commandBuffer::add);
        if (command != null) {
            commandBuffer.add(command);
//            if (!command.equalsCode(CommandCode.PASS)) {
//                selectedUsername = null;
//            }
        }
    }

    public Path getParentPath() {
        return workingDirectory.getParent();
    }

    public Path getRemotePath(String pathname) {
        return Paths.get(workingDirectory + File.separator + pathname);
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

    public void establishDataConnection(@Nonnull Socket dataConnection) {
        this.dataConnection = Objects.requireNonNull(dataConnection);
    }

    public void openDataConnection(@Nonnull ConnectionMode mode) {
        mode.openConnection(this);
    }

    @Nullable
    public Socket getDataConnection() {
        return dataConnection;
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

    @Nonnull
    public ConcurrentLinkedQueue<Command> getBufferedCommands() {
        return commandBuffer;
    }

    @Nonnull
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
