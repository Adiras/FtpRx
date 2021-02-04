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

package com.ftprx.server.command;

import com.ftprx.server.channel.Client;
import com.ftprx.server.channel.Command;

/*
 * This command is used to find out the type of operating
 * system at the server. The reply shall have as its first
 * word one of the system names listed in the current version
 * of the Assigned Numbers document
 */
public class SystemCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        // operating system name
        var name = System.getProperty("os.name");
        // operating system version
        var version = System.getProperty("os.version");
        // operating system architecture
        var arch = System.getProperty("os.arch");
        client.sendReply(215, String.format("%s - %s (%s)", name, version, arch));
    }
}
