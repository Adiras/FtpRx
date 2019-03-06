package me.adiras.ftprx;

public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String password;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withNoPassword() {
            this.password = null;
            return this;
        }

        public User build() {
            if (username.isEmpty()) throw new IllegalStateException("Username cannot be empty");

            User user = new User();
            user.username = username;
            user.password = password;
            return user;
        }
    }
}
