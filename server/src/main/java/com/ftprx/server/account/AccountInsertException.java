package com.ftprx.server.account;

public class AccountInsertException extends Throwable {
    // Message for trying insert account that already exists
    public static final String ACCOUNT_ALREADY_EXISTS = "Account with the specified username already exists";

    public AccountInsertException(String message) {
        super(message);
    }
}
