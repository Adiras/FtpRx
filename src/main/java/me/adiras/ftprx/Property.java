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

public enum Property {
    NAME("name"),
    PORT("port"),
    BUFFER_SIZE("buffer.size"),
    SERVER_SOCKET_BACKLOG("server.socket.backlog");

    private final String key;

    Property(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
