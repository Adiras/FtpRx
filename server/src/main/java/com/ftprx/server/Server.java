package com.ftprx.server;

import com.ftprx.server.process.ProtocolInterpreter;

import java.time.Instant;
import java.util.Optional;

public class Server {
    private final ProtocolInterpreter protocolInterpreter;
    private Instant startTimestamp;

    public Server() {
        this.protocolInterpreter = new ProtocolInterpreter();
    }

    public void start() {
        protocolInterpreter.start();
        startTimestamp = Instant.now();
    }

    public void stop() {
        protocolInterpreter.stop();
        startTimestamp = null;
    }

    public ServerStatus getStatus() {
        return protocolInterpreter.getStatus();
    }

    public void pause() {
        protocolInterpreter.pause();
    }

    public ProtocolInterpreter getProtocolInterpreter() {
        return protocolInterpreter;
    }

    public Optional<Instant> getStartTimestamp() {
        return Optional.ofNullable(startTimestamp);
    }
}
