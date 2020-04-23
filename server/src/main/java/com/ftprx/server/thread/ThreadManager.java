package com.ftprx.server.thread;

import com.ftprx.server.channel.Client;
import com.ftprx.server.process.WorkerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private final ExecutorService threadPool;

    public ThreadManager() {
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    public boolean launchWorkerThread(Client client) {
        threadPool.execute(new WorkerThread(client));
        return true;
    }
}
