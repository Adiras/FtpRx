package me.adiras.ftprx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileServerConfig implements ServerConfig {
    private final static String DEFAULT_SERVER_NAME = "FtpRx";
    private final static String DEFAULT_PORT = "21";

    private Properties properties;

    public FileServerConfig(String fileName) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            properties = new Properties();
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServerName() {
        return properties.getProperty("server.name", DEFAULT_SERVER_NAME);
    }

    @Override
    public int getPort() {
        return Integer.parseInt(properties.getProperty("server.port", DEFAULT_PORT));
    }
}
