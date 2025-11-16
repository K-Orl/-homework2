package org.example.service;

import org.example.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class UserServiceTest {

    private UserService userService;
    private FakeUserDao fakeDao;

    @BeforeEach
    void setUp() {
        fakeDao = new FakeUserDao();
        userService = new UserService(fakeDao);
    }

    @Test
    void testCreateUser() {
        Long id = userService.createUser("Anna", "anna@mail.com", 22);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(1, fakeDao.users.size());
    }

    @Test
    void testGetAllUsers() {
        userService.createUser("A", "a@mail", 10);
        userService.createUser("B", "b@mail", 20);

        List<User> users = userService.getAllUsers();

        Assertions.assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser() {
        Long id = userService.createUser("A", "a@mail", 10);
        userService.deleteUser(id);

        Assertions.assertTrue(fakeDao.users.isEmpty());
    }

    @Test
    void testUpdateUser() {
        Long id = userService.createUser("A", "a@mail", 10);

        userService.updateUser(id, "New", "new@mail", 99);

        User u = fakeDao.users.get(0);
        Assertions.assertEquals("New", u.getName());
        Assertions.assertEquals(99, u.getAge());
    }

    // --- FAKE DAO (in-memory) ---
    private static class FakeUserDao implements IUserDao {

        List<User> users = new ArrayList<>();
        long counter = 1;

        @Override
        public void save(User user) {
            user.setName(user.getName());
            try {
                var field = User.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(user, counter++);
            } catch (Exception ignored) {}

            users.add(user);
        }

        @Override
        public User getById(Long id) {
            return users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst().orElse(null);
        }

        @Override
        public void update(User user) {}

        @Override
        public void delete(User user) {
            users.remove(user);
        }

        @Override
        public List<User> getAll() {
            return users;
        }
    }
}
