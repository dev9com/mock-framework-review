package com.dev9.controller;

import com.dev9.model.User;
import com.dev9.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Inject private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable final String userId, final HttpServletResponse response) {
        final User user = userService.getUser(userId);
        if (user == null) response.setStatus(HttpStatus.NOT_FOUND.value());
        return user;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody final User user, final HttpServletResponse response) {
        final User createdUser = userService.addUser(user);
        response.setStatus(HttpStatus.CREATED.value());
        return createdUser;
    }
}
