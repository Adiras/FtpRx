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

package com.ftprx.server.account;

/**
 * Caused if something went wrong creating a new instance of the
 * {@link Account} class, usually thrown by the constructor.
 */
public class AccountCreateException extends Throwable {
    // Message when the username does not meet the length standards
    public static final String WRONG_USERNAME_LENGTH = "Wrong username length";

    public AccountCreateException(String message) {
        super(message);
    }
}
