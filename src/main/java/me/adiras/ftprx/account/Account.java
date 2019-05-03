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

import java.util.Objects;

public class Account {
    private final String username;
    private final String password;
    private final String homeDirectory;

    public Account(String username, String password, String homeDirectory) {
        this.username = username;
        this.password = password;
        this.homeDirectory = homeDirectory;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public boolean needPassword() {
        return Objects.nonNull(password);
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", homeDirectory='" + homeDirectory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                Objects.equals(homeDirectory, account.homeDirectory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, homeDirectory);
    }
}
