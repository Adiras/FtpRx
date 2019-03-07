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

public class Response {
    private String code;
    private String argument;

    public String getCode() {
        return code;
    }

    public String getArgument() {
        return argument;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String code;
        private String argument;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder argument(String argument) {
            this.argument = argument;
            return this;
        }

        public Response build() {
            if (code.isEmpty()) throw new IllegalStateException("Code cannot be empty");
            if (argument.isEmpty()) throw new IllegalStateException("Argument cannot be empty");

            Response resp = new Response();
            resp.code = code;
            resp.argument = argument;
            return resp;
        }
    }
}
