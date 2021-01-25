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

import com.ftprx.server.security.PasswordEncoder;
import org.tinylog.Logger;

import javax.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.ftprx.server.account.AccountCreateException.WRONG_USERNAME_LENGTH;

public class Account {
    private String username;
    private String homeDirectory;
    private String hashedPassword;

    public Account() {
        // Empty constructor for serialization purpose
    }

    public Account(String username, String homeDirectory, String plainPassword) throws AccountCreateException {
        this.username = validateUsername(username);
        this.homeDirectory = homeDirectory;
        if (plainPassword.isEmpty() || plainPassword.isBlank()) {
            plainPassword = null;
        }
        if (plainPassword != null) {
            hashedPassword = PasswordEncoder.encode(plainPassword);
        }
    }

    private String validateUsername(String username) throws AccountCreateException {
        if (username.length() == 0) {
            throw new AccountCreateException(WRONG_USERNAME_LENGTH);
        }
        return username;
    }

    public boolean verifyPassword(@Nullable String password) {
        if (password == null) {
            return !isPasswordRequired();
        }
        return PasswordEncoder.matches(password, hashedPassword);
    }

    public boolean isPasswordRequired() {
        return hashedPassword != null;
    }

    public String getUsername() {
        return username;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
