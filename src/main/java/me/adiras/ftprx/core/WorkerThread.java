package me.adiras.ftprx.core;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.NetworkListener;
import me.adiras.ftprx.Response;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.adiras.ftprx.util.ControlCharacters.*;

public class WorkerThread implements Runnable, Connection {
    private static final Logger logger = Logger.getLogger(WorkerThread.class.getName());

    private Socket clientSocket;
    private NetworkListener listener;

    private BufferedReader reader;
    private BufferedWriter writer;

    public WorkerThread(Socket clientSocket, NetworkListener listener) {
        this.clientSocket = clientSocket;
        this.listener = listener;
    }

    private void handleReceiveInput(String input) {
        listener.onRequestReceive(this, input);
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "New worker thread created [{0}]", Thread.currentThread().getName());
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            listener.onConnectionEstablishment(this);

            while (clientSocket != null && !clientSocket.isClosed()) {
                reader.lines().forEach(this::handleReceiveInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendResponse(Response response) {
        try {
            writer.write(response.getCode() + SP + response.getArgument() + CRLF);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
