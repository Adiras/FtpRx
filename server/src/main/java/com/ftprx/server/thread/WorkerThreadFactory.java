package com.ftprx.server.thread;

import com.ftprx.server.channel.ControlConnection;
import com.ftprx.server.command.CommandDispatcher;

public class WorkerThreadFactory {
    private CommandDispatcher dispatcher;

    public WorkerThreadFactory(CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Runnable createWorkerThread(ControlConnection connection) {
        return new WorkerThread(connection, dispatcher);
    }
}
