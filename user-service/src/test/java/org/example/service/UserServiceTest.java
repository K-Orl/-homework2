package org.example.service;

import org.example.User;
import org.example.UserDao;
import org.example.UserService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeAll
    void setup() {
        userDao = new UserDao();
        userService = new UserService(userDao);
    }

    @BeforeEach
    void cleanup() {
        userDao.getAll().forEach(userDao::delete);
    }

    @Test
    void testCreateAndGetAllUsers() {
        userService.createUser("Anna", "anna@mail.com", 22);
        userService.createUser("Bob", "bob@mail.com", 30);

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testUpdateUser() {
        Long id = userService.createUser("Charlie", "c@mail.com", 25);
        userService.updateUser(id, "CharlieUpdated", "c2@mail.com", 26);

        User u = userDao.getById(id);
        assertEquals("CharlieUpdated", u.getName());
        assertEquals(26, u.getAge());
    }

    @Test
    void testDeleteUser() {
        Long id = userService.createUser("David", "d@mail.com", 28);
        userService.deleteUser(id);

        assertNull(userDao.getById(id));
    }
}
