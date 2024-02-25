package com.ftprx.server;

import com.ftprx.server.channel.Client;

import java.util.List;

/**
 * Provides the APIs for managing connected client.
 */
public interface ClientManager {
    List<Client> getClients();
}
