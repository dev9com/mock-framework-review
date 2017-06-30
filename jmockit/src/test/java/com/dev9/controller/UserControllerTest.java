package com.dev9.controller;

import com.dev9.model.User;
import com.dev9.service.UserService;
import com.google.common.collect.ImmutableList;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.Verifications;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Test
public class UserControllerTest {

    private final String id = "id";

    @Injectable private HttpServletResponse response;
    @Injectable private User user;
    @Injectable private UserService userService;
    @Tested private UserController tested;

    @Test
    public void testGetUsers() throws Exception {
        new Expectations() {{ userService.getUsers(); result = ImmutableList.of(user); }};
        assertThat(tested.getUsers()).containsExactly(user);
    }

    @Test
    public void testGetUser() throws Exception {
        new Expectations() {{ userService.getUser(id); result = user; }};
        assertThat(tested.getUser(id, response)).isEqualTo(user);
        new Verifications() {{ response.setStatus(anyInt); times = 0; }};
    }

    @Test
    public void testGetUser_NotFound() throws Exception {
        new Expectations() {{ userService.getUser(id); result = null; }};
        assertThat(tested.getUser(id, response)).isNull();
        new Verifications() {{ response.setStatus(404); times = 1; }};
    }

    @Test
    public void testAddUser() throws Exception {
        new Expectations() {{ userService.addUser(user); result = user; }};
        assertThat(tested.addUser(user, response)).isEqualTo(user);
        new Verifications() {{ response.setStatus(201); }};
    }
}
