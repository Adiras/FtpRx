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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import me.adiras.ftprx.account.AccountRepository;
import me.adiras.ftprx.account.JsonAccountRepository;
import org.aeonbits.owner.ConfigFactory;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountRepository.class).to(JsonAccountRepository.class);
        bind(ServerConfig.class).toInstance(ConfigFactory.create(ServerConfig.class));
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }
}
