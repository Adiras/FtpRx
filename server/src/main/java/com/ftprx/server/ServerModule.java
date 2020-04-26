package com.ftprx.server;

import com.google.inject.AbstractModule;

/**
 * A module contributes configuration information, typically interface bindings.
 */
public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
//        bind(Server.class).asEagerSingleton();

        /*
            TODO:
                port
                data channel port range / passive port range Example: 5000-6000
                allow data connection modes: Active, Passive, All
                default transfer mode: ASCII, Binary
                Command channel timeout
                Data channel timeout
         */
    }
}
