package me.adiras.ftprx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerProperties {
    private static final String fileName = "server.properties";
    private static Properties properties;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName);

        try {
            properties= new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String propertyName) {
        String result = properties.getProperty(propertyName);
        if (result == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }
        return result;
    }
}
