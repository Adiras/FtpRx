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

package com.ftprx.server.repository;

import com.ftprx.server.account.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.ftprx.server.account.AccountInsertException.ACCOUNT_ALREADY_EXISTS;
import static java.nio.file.Files.*;

public class FileAccountRepository implements ObservableAccountRepository {
    private final Gson gson;
    private final Path repositoryFile;
    private final Set<AccountRepositoryChangeListener> listeners;

    public FileAccountRepository(String filename) {
        this.repositoryFile = Paths.get(filename);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

        // Only development purpose (delete in production)
        try {
            insert(new Account("admin", "test", "dir"));
            insert(new Account("admin2", "test", "dir"));
            insert(new Account("admin3", "test", "dir"));
        } catch (AccountInsertException ignore) {} catch (AccountCreateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(@Nonnull Account account) {
        Objects.requireNonNull(account, "Account must not be null");
    }

    @Override
    public Account findByUsername(@Nonnull String username) {
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
    public void insert(@Nonnull Account account) throws AccountInsertException {
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
    public void delete(@Nonnull Account account) {
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

    private void createRepositoryFileIfNotExists() {
        if (!exists(repositoryFile)) {
            Logger.debug("Account file not found!");
            try {
                createFile(repositoryFile);
            } catch (Exception e) {
                Logger.error(e.getMessage());
            }
        }
    }

    private void saveFile(List<Account> accounts) {
        createRepositoryFileIfNotExists();
        try (Writer writer = newBufferedWriter(repositoryFile)) {
            gson.toJson(accounts, writer);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    public List<Account> readFile() {
        createRepositoryFileIfNotExists();
        try (Reader reader = newBufferedReader(repositoryFile)) {
            return Arrays.asList(gson.fromJson(reader, Account[].class));
        } catch (Exception e) {
            return Collections.emptyList();
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
