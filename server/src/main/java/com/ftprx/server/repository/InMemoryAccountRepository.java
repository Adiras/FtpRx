package com.ftprx.server.repository;

import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountRepository implements AccountRepository {

    private List<Account> accounts = new ArrayList<>();

    public InMemoryAccountRepository() {
        insert(new Account("admin", "C:/Users/wkacp/Desktop/server", "admin"));
        insert(new Account("test", "C:/Users/wkacp/Desktop/servedr", "admin"));
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public Account findByUsername(String username) {
        for (Account account : accounts) {
            if (username.equals(account.getUsername())) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return accounts;
    }

    @Override
    public void insert(Account account) {
        accounts.add(account);
    }

    @Override
    public void delete(String username) {
        for (int index = 0; index < accounts.size(); index++) {
            Account account = accounts.get(index);
            if (username.equals(account.getUsername())) {
                accounts.remove(index);
            }
        }
    }
}
