package com.ftprx.server.repository;

import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountCreateException;
import com.ftprx.server.account.AccountRepository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class InMemoryAccountRepository implements AccountRepository {
    private List<Account> accounts = new ArrayList<>();

    public InMemoryAccountRepository() {
        try {
            insert(new Account("admin", "C:/Users/wkacp/Desktop/server", "admin"));
            insert(new Account("test", "C:/Users/wkacp/Desktop/servedr", "admin"));
        } catch (AccountCreateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@Nonnull Account account) {

    }

    @Override
    public Account findByUsername(@Nonnull String username) {
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
    public void insert(@Nonnull Account account) {
        accounts.add(account);
    }

    @Override
    public void delete(@Nonnull Account account) {
        delete(account.getUsername());
    }

    public void delete(String username) {
        for (int index = 0; index < accounts.size(); index++) {
            Account account = accounts.get(index);
            if (username.equals(account.getUsername())) {
                accounts.remove(index);
            }
        }
    }
}
