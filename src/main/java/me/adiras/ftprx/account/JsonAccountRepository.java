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

package me.adiras.ftprx.account;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JsonAccountRepository implements AccountRepository {
    private static final String DEFAULT_REPOSITORY_FILE_NAME = "/accounts.json";

    @Inject
    private Gson gson;

    private String fileName;

    public JsonAccountRepository() {
        this(DEFAULT_REPOSITORY_FILE_NAME);
    }

    public JsonAccountRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Account> findAll() {
        try (Reader reader = new InputStreamReader(JsonAccountRepository.class.getResourceAsStream(fileName), StandardCharsets.UTF_8)) {
            Collection<Account> accounts;
            Type collectionType = new TypeToken<Collection<Account>>(){}.getType();
            accounts = gson.fromJson(reader, collectionType);
            return new ArrayList<>(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Account findByUsername(String username) {
        return findAll().stream()
                .filter(account -> username.equals(account.getUsername()))
                .findFirst()
                .orElse(null);
    }
}
