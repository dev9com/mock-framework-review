package com.dev9.service;

import com.dev9.model.User;
import com.google.common.collect.ImmutableList;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * @author Justin Graham
 * @since 6/29/17
 */
@Test
@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
@PrepareForTest({ UserService.class, Collections.class, UUID.class })
public class UserServiceTest extends PowerMockTestCase {

    @Mock private User user;
    @Mock private UUID uuid;
    @Mock private Map<UUID, User> users;

    @BeforeMethod
    public void setUp() throws Exception {
        mockStatic(Collections.class);
        when(Collections.synchronizedMap((Map<UUID, User>) any(Map.class))).thenReturn(users);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        verifyStatic();
        Collections.synchronizedMap(any(Map.class));
    }

    @Test
    public void testGetUser() throws Exception {
        final String id = "id";
        final UserService tested = new UserService();

        mockStatic(UUID.class);
        when(UUID.fromString(id)).thenReturn(uuid);
        when(users.get(uuid)).thenReturn(user);

        assertThat(tested.getUser(id)).isEqualTo(user);

        verifyStatic();
        UUID.fromString(id);
    }

    @Test
    public void testAddUser() throws Exception {
        final UserService tested = spy(new UserService());
        doReturn(user).when(tested, "generateUser", user);
        when(user.getUuid()).thenReturn(uuid);

        assertThat(tested.addUser(user)).isEqualTo(user);
    }

    @Test
    public void testGetUsers() throws Exception {
        final UserService tested = new UserService();
        when(users.values()).thenReturn(ImmutableList.of(user));

        assertThat(tested.getUsers()).containsExactly(user);
    }

    @Test
    public void testGenerateUser() throws Exception {
        final String firstName = "firstName";
        final String lastName = "lastName";
        final int age = 20;
        final UserService tested = new UserService();

        mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(uuid);
        when(user.getFirstName()).thenReturn(firstName);
        when(user.getLastName()).thenReturn(lastName);
        when(user.getAge()).thenReturn(age);
        whenNew(User.class).withArguments(uuid, firstName, lastName, age).thenReturn(user);

        final User result = Whitebox.invokeMethod(tested, "generateUser", user);
        assertThat(result).isEqualTo(user);

        verifyNew(User.class).withArguments(uuid, firstName, lastName, age);
    }
}
