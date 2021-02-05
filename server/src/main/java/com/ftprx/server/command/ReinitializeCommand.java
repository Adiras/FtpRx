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

/**
 *  This command terminates a USER, flushing all I/O and account
 *  information, except to allow any transfer in progress to be
 *  completed. All parameters are reset to the default settings
 *  and the control connection is left open. This is identical
 *  to the state in which a user finds himself immediately after
 *  the control connection is opened. A USER command may be
 *  expected to follow.
 */
public class ReinitializeCommand extends SimpleCommand {

    @Override
    public void execute(Command command, Client client) {
        client.logout();
        client.sendReply(220, "Service ready for new user.");
    }
}
