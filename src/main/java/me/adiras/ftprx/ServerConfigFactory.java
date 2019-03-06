package me.adiras.ftprx;

public class ServerConfigFactory {
    private static final String PROPERTIES_FILE_NAME = "server.properties";

    public static ServerConfig getServerConfig() {
        return new FileServerConfig(PROPERTIES_FILE_NAME);
    }
}
