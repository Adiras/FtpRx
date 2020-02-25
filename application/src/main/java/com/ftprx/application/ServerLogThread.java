package com.ftprx.application;

import javafx.scene.control.TextInputControl;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class ServerLogThread implements Runnable {
    public static final String LOG_FILE_NAME = "C:/Users/wkacp/IdeaProjects/ftprx/application/log.txt";
    public static final Long DELAY = 3L;

    private TextInputControl control;
    private boolean running = true;

    public ServerLogThread(TextInputControl control) {
        this.control = control;
    }

    @Override
    public void run() {
        File file = new File(LOG_FILE_NAME);

        do {
            try {
                TimeUnit.SECONDS.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!file.exists());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (running) {
                String line;
                while ((line = br.readLine()) != null) {
                    control.appendText(line + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
