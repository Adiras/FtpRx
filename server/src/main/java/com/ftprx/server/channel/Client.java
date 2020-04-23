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

import com.ftprx.server.ActiveConnectionMode;
import com.ftprx.server.ConnectionMode;
import com.ftprx.server.PassiveConnectionMode;
import com.ftprx.server.account.Account;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Objects;
import java.util.concurrent.*;

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
    private Command lastCommand;

    public Client(Socket controlConnection) {
        this.controlConnection = Objects.requireNonNull(controlConnection, "Client cannot be null");
        commandBuffer = new ConcurrentLinkedQueue<>();
        replyBuffer = new ConcurrentLinkedQueue<>();
    }

    public void login(Account account) {
        this.account = account;
        changeWorkingDirectory(account.getHomeDirectory());
    }

    public boolean isLoggedIn() {
        return account != null;
    }

    public void logout() {
        this.account = null;
    }

    @CheckForNull
    public Command getLastCommand() {
        return lastCommand;
    }

    public void changeWorkingDirectory(String workingDirectory) {
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
        return controlConnection.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return controlConnection.getOutputStream();
    }

    public Socket getDataConnection() {
        return dataConnection;
    }

//    public boolean openDataConnection(ConnectionMode mode) {
//        return ((this.dataConnection = mode.getDataConnection()) != null);
//    }

    public void openActiveDataConnection(String host, int port) {
        try {
            this.dataConnection = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void openPassiveDataConnection(int port) {
//        ServerSocket listenerSocket = null;
//        try {
//            listenerSocket = new ServerSocket();
//            listenerSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));
//            final ServerSocket finalListenerSocket = listenerSocket;
//            Executors.newCachedThreadPool().execute(() -> {
//                try {
//                    this.dataConnection = finalListenerSocket.accept();
//                    System.out.println("KURWA DZIALAAAAA");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        finalListenerSocket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void closeDataConnection() throws IOException {
        dataConnection.close();
    }

    public Socket getControlConnection() {
        return controlConnection;
    }

    public void closeControlConnection() throws IOException {
        controlConnection.close();
    }

    public void setDataConnection(Socket dataConnection) {
        this.dataConnection = dataConnection;
    }

    public ConcurrentLinkedQueue<Command> getBufferedCommands() {
        return commandBuffer;
    }

    public ConcurrentLinkedQueue<Reply> getBufferedReplies() {
        return replyBuffer;
    }

    public boolean isControlConnectionOpen() {
        return controlConnection != null && !controlConnection.isClosed() && controlConnection.isConnected();
    }

    public boolean isDataConnectionOpen() {
        return dataConnection != null && !dataConnection.isClosed() && dataConnection.isConnected();
    }
}