package com.ftprx.server.account;

import java.util.UUID;

public class Account {
    private String username;
    private String homeDirectory;
    private String password;

    public Account() {
    }

    public Account(String username, String homeDirectory, String password) {
        this.username = username;
        this.homeDirectory = homeDirectory;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public void setPassword(String password) {
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

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
