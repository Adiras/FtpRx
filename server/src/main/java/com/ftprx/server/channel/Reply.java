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

package com.ftprx.server.channel;

import com.ftprx.server.util.ControlCharacters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * A reply is an acknowledgment (positive or negative) sent from
 * server to client via the control connection in response to commands.
 * Example: '220 <SP> Service ready <CRLF>' -> Reply{code="220", argument="Service ready"}
 */
public class Reply {
    /**
     * The codes are for use by programs.
     */
    private final String code;

    /**
     * The text is usually intended for human users.
     */
    private final String text;

    public Reply(@Nonnull String code, @Nullable String text) {
        this.code = Objects.requireNonNull(code, "Code must not be null");
        this.text = Optional.ofNullable(text).orElse(ControlCharacters.EMPTY);
    }

    @Nonnull
    public String getCode() {
        return code;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return code + ControlCharacters.SP + text + ControlCharacters.CRLF;
    }
}
