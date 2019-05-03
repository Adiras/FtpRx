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

package me.adiras.ftprx.command.rfc959;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.Response;
import me.adiras.ftprx.account.Account;
import me.adiras.ftprx.command.CommandHandler;
import me.adiras.ftprx.core.ServerContext;

import java.util.Objects;

public class UserCommandHandler implements CommandHandler {

    @Override
    public void process(ServerContext context, Connection connection, String request) {
        String username = request.substring(5, request.length() - 2);

        if (username.isEmpty()) {
            connection.sendResponse(Response.builder()
                    .code("332")
                    .argument("Need username for login.")
                    .build());
            return;
        }

        Account userAccount = context.getAccountRepository().findByUsername(username);
        if (Objects.isNull(userAccount)) {
            connection.sendResponse(Response.builder()
                    .code("230")
                    .argument("Not logged in..")
                    .build());
            return;
        }

        if (userAccount.needPassword()) {
            connection.sendResponse(Response.builder()
                    .code("331")
                    .argument("User name okay, need password.")
                    .build());
            connection.assignUsername(username);
        } else {
            connection.sendResponse(Response.builder()
                    .code("230")
                    .argument("User logged in.")
                    .build());
        }
    }
}
