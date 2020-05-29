package com.ftprx.server.thread;

import com.ftprx.server.channel.Client;
import com.ftprx.server.process.DataTransferProcess;
import com.ftprx.server.process.WorkerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private static final int THREADS_IN_POOL = 10;
    private static final ExecutorService threadPool;

    static {
        threadPool = Executors.newFixedThreadPool(THREADS_IN_POOL);
    }

    public static void launchDataTransferProcess(DataTransferProcess process) {
        Executors.newCachedThreadPool().execute(process);
    }

    public static boolean launchWorkerThread(Client client) {
        threadPool.execute(new WorkerThread(client));
        return true;
    }
}
