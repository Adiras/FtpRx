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
import com.ftprx.server.util.ControlCharacters;
import org.tinylog.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class ListingProcess extends DataTransferProcess {

    private final String pathname;

    public ListingProcess(@Nonnull Client client, @Nullable String pathname) {
        super(requireNonNull(client));
        this.pathname = pathname;
    }

    @Override
    public void perform() {
        // Try-Catch block to handle exceptions
        try {
            PrintWriter writer = new PrintWriter(client.getDataConnection().getOutputStream());

            // Creates a new File instance by converting the given
            // pathname string into an abstract pathname
            String[] files = new File(pathname == null ? client.getWorkingDirectory() : pathname).list();
//            for (String file : files) {
//                writer.println(new String(file.getBytes(), StandardCharsets.ISO_8859_1));
//                System.out.println(file);
//            }
            writer.flush();

            client.sendReply(226, "Directory send OK.");
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
    }
}
