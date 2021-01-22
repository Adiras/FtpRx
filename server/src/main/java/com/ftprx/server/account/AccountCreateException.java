package com.ftprx.server.account;

/**
 * Caused if something went wrong creating a new instance of the
 * {@link Account} class, usually thrown by the constructor.
 */
public class AccountCreateException extends Throwable {
    // Message when the username does not meet the length standards
    public static final String WRONG_USERNAME_LENGTH = "Wrong username length";

    public AccountCreateException(String message) {
        super(message);
    }
}
