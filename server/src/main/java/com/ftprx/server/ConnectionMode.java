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

package com.ftprx.server;

import com.ftprx.server.channel.Client;
import org.jetbrains.annotations.NotNull;

import java.net.Socket;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface ConnectionMode {
    /**
     * Opens the data connection between client and server.
     * The method blocks thread until a connection is made.
     */
    void openConnection(@NotNull Client client);
}
