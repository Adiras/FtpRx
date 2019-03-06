package me.adiras.ftprx;

public class Application {
    public static void main(String[] args) {
        launchServer();
    }

    private static void launchServer() {
        new Server().start();
    }
}
