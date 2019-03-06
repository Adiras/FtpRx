package me.adiras.ftprx.core;

import me.adiras.ftprx.NetworkListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.adiras.ftprx.threading.ThreadManager.launchThread;

public abstract class MultithreadServer {
    private static final Logger logger = Logger.getLogger(MultithreadServer.class.getName());

    private ServerSocketFactory socketFactory;
    private ServerSocket serverSocket;
    private NetworkListener listener;

    public MultithreadServer(ServerSocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    public synchronized void start() {
        logger.log(Level.INFO, "Starting server");
        try {
            serverSocket = socketFactory.createServerSocket();
            logger.log(Level.INFO, "Server running at port {0}", serverSocket.getLocalPort());
            launchThread(() -> {
                acceptorThreadImpl();
            });
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to start server: {0}", e.getMessage());
        }
    }

    private void acceptorThreadImpl() {
        while (serverSocket != null && !serverSocket.isClosed()) {
            try {
                // This method waits until a client connects to the server on the given port
                handleConnection(serverSocket.accept());
            } catch (Exception e) {
                logger.log(Level.WARNING, "Unable to handle new connection: {0}", e.getMessage());
            }
        }
    }

    private void handleConnection(Socket socket) {
        launchThread(new WorkerThread(socket, listener));
    }

    public synchronized void stop() {
        logger.log(Level.INFO, "Stopping server");
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setListener(NetworkListener listener) {
        this.listener = listener;
    }
}
