package com.ftprx.server.account;

public class Account {
    private final String username;
    private final String homeDirectory;
    private final String password;

    public Account(String username, String homeDirectory, String password) {
        this.username = username;
        this.homeDirectory = homeDirectory;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public String getPassword() {
        return password;
    }
}
