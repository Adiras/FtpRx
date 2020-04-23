package com.ftprx.server.account;

import javax.annotation.Nullable;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    @Override
    public Account findAccountByUsername(String username) {
        if ("admin".equals(username)) {
            return new Account("admin", "C:/Users/wkacp/Desktop/server", "admin");
        }
        return null;
    }
}
