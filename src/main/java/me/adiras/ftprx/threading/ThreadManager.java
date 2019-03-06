package me.adiras.ftprx.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {
    private static final ExecutorService service = Executors.newCachedThreadPool();

    public static Future launchThread(final Runnable runnable) {
        return service.submit(runnable);
    }
}
