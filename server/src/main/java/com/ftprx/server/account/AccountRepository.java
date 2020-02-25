package com.ftprx.server.account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findAccountByUsername(String username);
}
