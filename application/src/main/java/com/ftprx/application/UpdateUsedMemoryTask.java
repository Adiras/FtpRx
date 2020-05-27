package com.ftprx.application;

import com.ftprx.server.Server;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class UpdateUsedMemoryTask implements Runnable {
    private final Server server;
    private final Label label;

    public UpdateUsedMemoryTask(Server server, Label label) {
        this.server = server;
        this.label = label;
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long usedMemory = totalMemory - freeMemory;
        String value = String.format("%dMB", usedMemory / (1024L * 1024L));
        label.setText(value);
    }
}
