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

package me.adiras.ftprx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
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

    public static String get(Property prop) {
        String key = prop.getKey();
        String value = properties.getProperty(key);
        if (Objects.isNull(value)) {
            throw new RuntimeException("Property " + key + " not found");
        }
        return value;
    }
}
