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

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:server.properties"})
public interface ServerConfig extends Config {
    @Key("server.name")
    String name();

    @Key("server.port")
    int port();

    @Key("server.maximum-worker-thread-pool-size")
    int maximumWorkerThreadPoolSize();

    @Key("server.request-buffer-size")
    int requestBufferSize();

    @Key("server.welcome-message")
    String welcomeMessage();
}
