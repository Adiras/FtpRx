/*
 * Copyright 2019, FtpRx Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftprx.server.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tinylog.Logger;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.ftprx.server.account.AccountInsertException.ACCOUNT_ALREADY_EXISTS;
import static java.nio.file.Files.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class FileAccountRepository implements ObservableAccountRepository {
    private final Gson gson;
    private final Path accountsFile;
    private final Set<AccountRepositoryChangeListener> listeners;

    public FileAccountRepository(String filename) {
        this.accountsFile = Paths.get(filename);
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

        // Only development purpose (delete in production)
        try {
            insert(new Account("admin", "test", "dir"));
            insert(new Account("admin2", "test", "dir"));
            insert(new Account("admin3", "test", "dir"));
        } catch (AccountInsertException | AccountCreateException ignore) {}
    }

    @Override
    public void update(@NotNull Account account) {
        Objects.requireNonNull(account, "Account must not be null");
    }

    @Override
    public Account findByUsername(@NotNull String username) {
        return findAll()
                .stream()
                .filter(account -> username.equals(account.getUsername()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return readFile();
    }

    @Override
    public void insert(@NotNull Account account) throws AccountInsertException {
        Objects.requireNonNull(account, "Account must not be null");
        if (isAccountExists(account)) {
            throw new AccountInsertException(ACCOUNT_ALREADY_EXISTS);
        }
        List<Account> toSave = new ArrayList<>(findAll());
        toSave.add(account);
        saveFile(toSave);
        notifyInsertAccount(account);
    }

    private void notifyInsertAccount(Account account) {
        for (AccountRepositoryChangeListener listener : listeners) {
            listener.onInsertAccount(account);
        }
    }

    public boolean isAccountExists(Account account) {
        return findByUsername(account.getUsername()) != null;
    }

    @Override
    public void delete(@NotNull Account account) {
        Objects.requireNonNull(account, "Account must not be null");
        delete(account.getUsername());
        notifyDeleteAccount(account);
    }

    private void notifyDeleteAccount(Account account) {
        for (AccountRepositoryChangeListener listener : listeners) {
            listener.onDeleteEvent(account);
        }
    }

    private void delete(String username) {
        List<Account> toSave = new ArrayList<>(findAll());
        List<Account> toRemove = new ArrayList<>();
        toSave.stream()
                .filter(account -> username.equals(account.getUsername()))
                .forEach(toRemove::add);
        toSave.removeAll(toRemove);
        saveFile(toSave);
    }

    private void createAccountsFileIfNotExists() {
        if (!exists(accountsFile)) {
            Logger.debug("Account file not found!");
            try {
                createFile(accountsFile);
            } catch (Exception e) {
                Logger.error(e.getMessage());
            }
        }
    }

    private void saveFile(List<Account> accounts) {
        createAccountsFileIfNotExists();
        try (Writer writer = newBufferedWriter(accountsFile)) {
            gson.toJson(accounts, writer);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    public List<Account> readFile() {
        createAccountsFileIfNotExists();
        try (Reader reader = newBufferedReader(accountsFile)) {
            return asList(gson.fromJson(reader, Account[].class));
        } catch (Exception e) {
            return emptyList();
        }
    }

    @Override
    public void addListener(@Nullable AccountRepositoryChangeListener listener) {
        Optional.ofNullable(listener).ifPresent(listeners::add);
    }

    @Override
    public void removeListener(@Nullable AccountRepositoryChangeListener listener) {
        Optional.ofNullable(listener).ifPresent(listeners::remove);
    }
}
