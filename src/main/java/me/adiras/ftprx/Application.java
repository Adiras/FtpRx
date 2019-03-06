package me.adiras.ftprx;

import me.adiras.ftprx.core.Server;

public class Application {
    public static void main(String[] args) {
        launchServer();
    }

    private static void launchServer() {
        new Server().start();
    }
}
