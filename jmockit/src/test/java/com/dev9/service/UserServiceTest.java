package com.dev9.service;

import com.dev9.model.User;
import com.google.common.collect.ImmutableList;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Test
@SuppressWarnings("ResultOfMethodCallIgnored")
public class UserServiceTest {

    @Injectable private Map<UUID, User> users;
    @Mocked private User user;
    @Mocked private UUID uuid;
    @Tested(availableDuringSetup = true) private UserService tested;

    @BeforeMethod
    public void setUp() throws Exception {
        Deencapsulation.setField(tested, "users", users);
    }

    @Test
    public void testGetUser() throws Exception {
        final String id = "id";
        new Expectations() {{
            UUID.fromString(id); result = uuid;
            users.get(uuid); result = user;
        }};
        assertThat(tested.getUser(id)).isEqualTo(user);
    }

    /** Isolate method using Partial Mock */
    @Test
    public void testAddUser() throws Exception {
        new Expectations(tested) {{
            Deencapsulation.invoke(tested, "generateUser", user); result = user;
            user.getUuid(); result = uuid;
        }};
        assertThat(tested.addUser(user)).isEqualTo(user);
        new Verifications() {{ users.put(uuid, user); }};
    }

    @Test
    public void testGetUsers() throws Exception {
        new Expectations() {{ users.values(); result = ImmutableList.of(user); }};
        assertThat(tested.getUsers()).containsExactly(user);
    }

    /** Test private method */
    @Test
    public void testGenerateUser() throws Exception {
        final String firstName = "firstName";
        final String lastName = "lastName";
        final int age = 20;
        new Expectations() {{
            UUID.randomUUID(); result = uuid;
            user.getFirstName(); result = firstName;
            user.getLastName(); result = lastName;
            user.getAge(); result = age;
        }};
        final User result = Deencapsulation.invoke(tested, "generateUser", user);
        assertThat(result).isNotNull();
        new Verifications() {{ new User(uuid, firstName, lastName, age); }};
    }
}
