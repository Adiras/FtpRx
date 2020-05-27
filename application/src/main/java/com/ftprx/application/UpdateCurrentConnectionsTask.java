package com.ftprx.application;

import com.ftprx.server.Server;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class UpdateCurrentConnectionsTask implements Runnable {
    private final Server server;
    private final Label label;

    public UpdateCurrentConnectionsTask(Server server, Label label) {
        this.server = server;
        this.label = label;
    }

    @Override
    public void run() {
        System.out.println("UpdateServerStatusLabelTask.run");
        Color color = Color.DARKGRAY;
        String text = "null";

        switch (server.getStatus()) {
            case RUNNING:
                text = "Running";
                color = Color.GREEN;
                break;
            case STOPPED:
                text = "Stopped";
                color = Color.RED;
                break;
            case PAUSED:
                text = "Paused";
                color = Color.DARKORANGE;
                break;
        }
        label.setTextFill(color);
        label.setText(text);
    }
}
