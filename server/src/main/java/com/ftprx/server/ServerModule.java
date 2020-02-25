package com.ftprx.server;

import com.google.inject.AbstractModule;

/**
 * A module contributes configuration information, typically interface bindings.
 */
public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Server.class).asEagerSingleton();
    }
}
