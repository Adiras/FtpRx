package me.adiras.ftprx;

import me.adiras.ftprx.command.UserRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MemoryUserRepository implements UserRepository {
    private final List<User> userList = new ArrayList<>();

    public MemoryUserRepository() {
        userList.add(User.builder()
                .withUsername("Kacper")
                .withNoPassword()
                .build());
    }

    @Override
    public User findByUsername(String username) {
        for (Iterator<User> iter = userList.iterator(); iter.hasNext(); ) {
            User element = iter.next();
            if (username.equals(element.getUsername())) {
                return element;
            }
        }
        return null;
    }
}
