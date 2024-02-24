package com.ftprx.server.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class JsonAccountFileManager implements AccountFileManager {
    private final Gson gson;

    public JsonAccountFileManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void write(Writer writer, List<Account> accounts) {
        gson.toJson(accounts, writer);
    }

    @Override
    public List<Account> read(Reader reader) {
        return List.of(gson.fromJson(reader, Account[].class));
    }
}
