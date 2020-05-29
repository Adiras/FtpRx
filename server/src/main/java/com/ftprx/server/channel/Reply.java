package com.ftprx.server.channel;

import com.ftprx.server.util.ControlCharacters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return code + ControlCharacters.SP + text + ControlCharacters.CRLF;
    }
//    public static final class ReplyBuilder {
//        private String code;
//        private String text;
//
//        private ReplyBuilder() {
//        }
//
//        public static ReplyBuilder aReply() {
//            return new ReplyBuilder();
//        }
//
//        public ReplyBuilder withCode(String code) {
//            this.code = code;
//            return this;
//        }
//
//        public ReplyBuilder withText(String text) {
//            this.text = text;
//            return this;
//        }
//
//        public Reply build() {
//            return new Reply(code, text);
//        }
//    }
}
