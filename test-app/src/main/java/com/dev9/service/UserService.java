package com.dev9.service;

import com.dev9.model.User;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Component
public class UserService {

    private final Map<UUID, User> users = Collections.synchronizedMap(new HashMap<UUID, User>());

    public User getUser(final String userId) {
        final UUID id = UUID.fromString(userId);
        return users.get(id);
    }

    /** Method with dependency on private method to show off test isolation using Spying or Partial Mock */
    public User addUser(final User user) {
        final User newUser = generateUser(user);
        users.put(newUser.getUuid(), newUser);
        return newUser;
    }

    public List<User> getUsers() {
        return ImmutableList.<User>builder().addAll(users.values()).build();
    }

    /** Private method with hard dependency to show off mocking capabilities */
    private User generateUser(final User user) {
        return new User(UUID.randomUUID(), user.getFirstName(), user.getLastName(), user.getAge());
    }
}
