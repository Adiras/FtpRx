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

package com.ftprx.server.command;

import com.ftprx.server.Server;
import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/**
 * The interface definition for an FTP Command.
 * Your subclass should implement the <code>execute</code> method.
 */
public abstract class SimpleCommand {

    /**
     * Contains the business logic for the command being executed.
     *
     * @param command the <code>command</code> to handle
     * @param client the <code>client</code> who sent the command
     */
    public abstract void execute(Command command, Client client);

    protected Server getServerInstance() {
        return Server.getInstance();
    }
}
