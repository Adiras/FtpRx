package com.ftprx.server.account;

import org.tinylog.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.ftprx.server.account.AccountCreateException.WRONG_USERNAME_LENGTH;

public class Account {

    private String username;
    private String homeDirectory;
    private String hashedPassword;

    public Account() {
    }

    public Account(String username, String homeDirectory, String plainPassword) throws AccountCreateException {
        this.username = validateUsername(username);
        this.homeDirectory = homeDirectory;
        if (plainPassword != null) {
            this.hashedPassword = generateHash(plainPassword);
        }
    }

    private String validateUsername(String username) throws AccountCreateException {
        if (username.length() == 0) {
            throw new AccountCreateException(WRONG_USERNAME_LENGTH);
        }
        return username;
    }

    public boolean verifyPassword(String password) {
        return hashedPassword.equals(generateHash(password));
    }

    public boolean isPasswordRequired() {
        return hashedPassword != null;
    }

    private String generateHash(String input) {
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            for (byte b : hashedBytes) {
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.error(e.getMessage());
        }
        return hash.toString();
    }

    public String getUsername() {
        return username;
    }

    public String getHomeDirectory() {
        return homeDirectory;
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
