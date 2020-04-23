package com.ftprx.server.process;

import java.io.OutputStream;

public class ReceiveFileProcess implements Runnable {
    private final OutputStream out;

    public ReceiveFileProcess(OutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {

    }
}
