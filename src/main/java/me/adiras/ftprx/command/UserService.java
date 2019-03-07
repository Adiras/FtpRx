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

package me.adiras.ftprx.command;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.MemoryUserRepository;
import me.adiras.ftprx.User;

import java.util.*;

public class UserService {
    private UserRepository userRepository = new MemoryUserRepository();
    private HashMap<UUID, User> connectionAssignments = new HashMap<>();

    public boolean isUserNeedPassword(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            return false;
        }
        return Objects.nonNull(user.getPassword());
    }

    public boolean isUserExists(String username) {
        return Objects.nonNull(userRepository.findByUsername(username));
    }

    public boolean assignUserToConnection(String username, Connection connection) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) return false;
        connectionAssignments.put(connection.getUUID(), user);
        return true;
    }

    public boolean isUserAssignToAnyConnection(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) return false;
        return connectionAssignments.containsValue(username);
    }
}
