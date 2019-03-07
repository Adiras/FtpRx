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

import me.adiras.ftprx.command.UserRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryUserRepository implements UserRepository {
    private final List<User> userList = new ArrayList<>();

    public MemoryUserRepository() {
        userList.add(User.builder()
                .withUsername("Kacper")
                .withNoPassword()
                .build());
    }

    @Override
    public User findByUsername(String username) {
        for (Iterator<User> iter = userList.iterator(); iter.hasNext(); ) {
            User element = iter.next();
            if (username.equals(element.getUsername())) {
                return element;
            }
        }
        return null;
    }
}
