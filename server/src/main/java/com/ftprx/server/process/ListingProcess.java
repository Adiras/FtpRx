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

package com.ftprx.server.process;

import com.ftprx.server.channel.Client;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public class ListingProcess extends DataTransferProcess {
    private static final Charset MESSAGE_CHARSET = StandardCharsets.ISO_8859_1;
    private DirectoryListFormat format = new DosDirectoryListFormat();
    private final File directory;

    public ListingProcess(@Nonnull Client client, @Nonnull File directory) {
        super(client);
        this.directory = directory;
    }

    @Override
    public void perform() {
        try {
            PrintWriter writer = new PrintWriter(client.getDataConnection().getOutputStream());
//            Stream.of(directory.listFiles())
//                    .dropWhile(File::isHidden)
//                    .forEach(file -> writer.println(new String(format.parse(file).getBytes(), MESSAGE_CHARSET)));
            for (File file : directory.listFiles()) {
                if (file.isHidden()) continue;
                writer.println(new String(format.parse(file).getBytes(), MESSAGE_CHARSET));
            }
            writer.flush();
            client.sendReply(226, "Directory send OK.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }
}
