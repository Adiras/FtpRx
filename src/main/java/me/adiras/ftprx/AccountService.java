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

package me.adiras.ftprx;

import me.adiras.ftprx.command.Command;

import java.util.HashMap;

public class AccountService {
    private AccountRepository repository = new AccountRepository();
    private HashMap<Connection, Account> authenticationQueue = new HashMap<>();
//    private HashMap<Connection, Account> sessions = new HashMap<>();

    public void waitForAuthenticate(Connection connection, String username) {
        authenticationQueue.put(connection, repository.findAccountByUsername(username)
                .orElseThrow(IllegalArgumentException::new));
    }

    public boolean isWaitingForAuthenticate(Connection connection, String username) {
        return authenticationQueue.get(connection).getUsername().equals(username);
    }

    public boolean isAccountExists(String username) {
        return repository.findAccountByUsername(username).isPresent();
    }

    public boolean isAccountNeedPassword(String username) {
        return repository.findAccountByUsername(username)
                .orElseThrow(IllegalAccessError::new)
                .needPassword();
    }

}
