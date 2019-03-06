package me.adiras.ftprx;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(code, response.code) &&
                Objects.equals(argument, response.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, argument);
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
