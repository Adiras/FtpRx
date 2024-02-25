package com.ftprx.server;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Mutable;
import org.aeonbits.owner.Reloadable;

@Config.Sources("file:server.properties")
public interface ServerConfig extends Mutable, Reloadable, Accessible {

    @Key("port")
    @DefaultValue("21")
    int port();

    @Key("hostname")
    @DefaultValue("127.0.0.1")
    String hostname();

    @Key("concurrent-connection-limit")
    @DefaultValue("0")
    int concurrentConnectionLimit();
}
