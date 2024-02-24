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
import com.ftprx.server.channel.Command;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandDispatcher {
    private final CommandLookupTable commandLookupTable;
    private final Client client;

    /**
     * Create a new {@link CommandDispatcher} instance.
     * @param client the client that is assigned for this dispatcher
     */
    public CommandDispatcher(@NotNull Client client) {
        this.client = Objects.requireNonNull(client, "Client must not be null");
        this.commandLookupTable = CommandLookupTable.bootstrap();
    }

    public void execute(Command command) {
        if (commandLookupTable.isCommandNotRegistered(command)) {
            onExecuteUnknownCommand(command, client);
            return;
        }
        commandLookupTable.getCommand(command).execute(command, client);
//        if (handler.isRequireDependency()) {
//            final Command clientLastCommand = client.getLastCommand();
//            if (clientLastCommand == null) {
//                onExecuteWithoutDependCommand(command, client);
//            } else {
//                boolean atLeastOneDependency = false;
//                for (CommandCode dependency : handler.dependency()) {
//                    if (dependency.name().equals(clientLastCommand.getCode())) {
//                        atLeastOneDependency = true;
//                    }
//                }
//                if (!atLeastOneDependency) {
//                    onExecuteWithoutDependCommand(command, client);
//                }
//            }
//        }
    }

    public void onExecuteUnknownCommand(Command command, Client client) {
        client.sendReply(502, "Command not implemented.");
    }
}
