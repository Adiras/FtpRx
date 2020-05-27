package com.ftprx.server.account;

import java.util.List;

public interface AccountRepository {
    void update(Account account);
    Account findByUsername(String username);
    List<Account> findAll();
    void insert(Account account);
    void delete(String username);
}
