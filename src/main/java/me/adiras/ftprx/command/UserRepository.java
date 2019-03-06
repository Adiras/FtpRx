package me.adiras.ftprx.command;

import me.adiras.ftprx.User;

public interface UserRepository {
    User findByUsername(String username);
}
