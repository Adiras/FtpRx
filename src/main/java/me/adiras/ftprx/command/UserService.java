package me.adiras.ftprx.command;

import me.adiras.ftprx.MemoryUserRepository;
import me.adiras.ftprx.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UserService {
    private UserRepository userRepository = new MemoryUserRepository();
    private List<User> authenticatedUsers = new ArrayList<>();

    public boolean isUserNeedPassword(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            return false;
        }
        return Objects.nonNull(user.getPassword());
    }

    public boolean isUserAuthenticated(String username) {
        for (Iterator<User> iter = authenticatedUsers.iterator(); iter.hasNext(); ) {
            User element = iter.next();
            if (username.equals(element.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean authenticate(String username) {
        return authenticate(username, null);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            return false;
        }

        if (Objects.isNull(password) || username.equals(user.getUsername())) {
            authenticatedUsers.add(user);
            return true;
        }
        return false;
    }
}
