package com.ftprx.server.account;

import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    @Override
    public Optional<Account> findAccountByUsername(String username) {
        if ("admin".equals(username)) {
            return Optional.of(new Account("admin", "C:/Users/admin", "admin"));
        }
        return Optional.empty();
    }
}
