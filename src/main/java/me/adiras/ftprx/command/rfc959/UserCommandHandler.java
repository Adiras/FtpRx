package me.adiras.ftprx.command.rfc959;

import me.adiras.ftprx.Connection;
import me.adiras.ftprx.Response;
import me.adiras.ftprx.command.CommandHandler;
import me.adiras.ftprx.core.ServerContext;

public class UserCommandHandler implements CommandHandler {
    @Override
    public void process(ServerContext context, Connection connection, String argument) {
        boolean authenticated = context.getUserService().isUserAuthenticated(argument);
        if (authenticated) {
            connection.sendResponse(Response.builder()
                    .code("331")
                    .argument("User logged in.")
                    .build());
            return;
        }

        boolean needsPassword = context.getUserService().isUserNeedPassword(argument);
        if (needsPassword) {
            connection.sendResponse(Response.builder()
                    .code("331")
                    .argument("User name okay, need password.")
                    .build());
        } else {
            boolean success = context.getUserService().authenticate(argument);
            if (success) {
                connection.sendResponse(Response.builder()
                        .code("230")
                        .argument("User logged in.")
                        .build());
            } else {
                connection.sendResponse(Response.builder()
                        .code("331")
                        .argument("Not logged in..")
                        .build());
            }
        }
    }
}
