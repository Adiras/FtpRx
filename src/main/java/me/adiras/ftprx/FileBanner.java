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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileBanner implements Banner {
    private static final String DEFAULT_FILE_NAME = "/banner.txt";

    private String fileName;

    public FileBanner() {
        this(DEFAULT_FILE_NAME);
    }

    public FileBanner(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void printBanner() {
        try (Stream<String> stream = Files.lines(getBannerFile())) {
            stream.forEach(System.out::println);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Path getBannerFile() throws URISyntaxException {
        return Paths.get(FileBanner.class.getResource(fileName).toURI());
    }
}
