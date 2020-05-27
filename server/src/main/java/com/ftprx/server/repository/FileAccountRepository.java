package com.ftprx.server.repository;

import com.ftprx.server.account.Account;
import com.ftprx.server.account.AccountRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.tinylog.Logger.debug;
import static org.tinylog.Logger.error;

public class FileAccountRepository implements AccountRepository {

    private final Gson gson;
    private final Path repositoryFile;

    public FileAccountRepository(String filename) {
        this.repositoryFile = Paths.get(filename);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void update(Account account) {
    }

    @Override
    public Account findByUsername(String username) {
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
    public void insert(Account account) {
        List<Account> toSave = new ArrayList<>(findAll());
        toSave.add(account);
        saveFile(toSave);
    }

    @Override
    public void delete(String username) {
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
            debug("Account file not found!");
            try {
                createFile(repositoryFile);
            } catch (Exception e) {
                error(e.getMessage());
            }
        }
    }

    private void saveFile(List<Account> accounts) {
        createRepositoryFileIfNotExists();
        try (Writer writer = newBufferedWriter(repositoryFile)) {
            gson.toJson(accounts, writer);
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    public List<Account> readFile() {
        createRepositoryFileIfNotExists();
        try (Reader reader = newBufferedReader(repositoryFile)) {
            return asList(gson.fromJson(reader, Account[].class));
        } catch (Exception e) {
            return emptyList();
        }
    }
}
