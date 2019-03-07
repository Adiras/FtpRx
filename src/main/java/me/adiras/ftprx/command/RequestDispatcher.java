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

import me.adiras.ftprx.*;
import me.adiras.ftprx.core.ServerContext;

import java.nio.charset.Charset;
import java.util.Objects;

import static me.adiras.ftprx.util.ControlCharacters.*;

public class RequestDispatcher {
    private ServerContext context;

    public RequestDispatcher(ServerContext context) {
        this.context = context;
    }

    private CommandHandler createHandlerInstance(Class<? extends CommandHandler> handlerClass) {
        try {
            return handlerClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new NullCommandHandler();
    }

    public void handleRequest(Connection connection, byte[] data) {
        String request = new String(data, Charset.forName("UTF-8"));
        String trimmedRequest = request.substring(0, request.indexOf("\u0000"));

        Command command = CommandResolver.resolve(trimmedRequest);
        if (Objects.isNull(command)) {
            connection.sendResponse(Response.builder()
                    .code("502")
                    .argument("Command not implemented.")
                    .build());
            return;
        }

        String readyRequest = trimmedRequest
                .replace(command.getLabel(), "")
                .replace(SP, "")
                .replace(CRLF, "");

        createHandlerInstance(command.getHandler())
                .process(context, connection, readyRequest);
    }
}
