package org.example.dao;

import org.example.User;
import org.example.UserDao;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoIT {

    private UserDao userDao;

    @BeforeAll
    void setup() {
        userDao = new UserDao();
    }

    @BeforeEach
    void cleanTable() {
        // чистим таблицу перед каждым тестом
        userDao.getAll().forEach(userDao::delete);
    }

    @Test
    @Order(1)
    void testSaveUser() {
        User user = new User("Alice", "alice@test.com", 23);
        userDao.save(user);
        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void testGetUserById() {
        User user = new User("Bob", "bob@test.com", 30);
        userDao.save(user);

        User loaded = userDao.getById(user.getId());
        assertNotNull(loaded);
        assertEquals("Bob", loaded.getName());
    }

    @Test
    @Order(3)
    void testGetAllUsers() {
        User u1 = new User("Anna", "anna@test.com", 22);
        User u2 = new User("Ben", "ben@test.com", 28);
        userDao.save(u1);
        userDao.save(u2);

        List<User> users = userDao.getAll();
        assertNotNull(users);
        assertTrue(users.size() == 2, "Должно быть 2 пользователя");
    }

    @Test
    @Order(4)
    void testUpdateUser() {
        User user = new User("Charlie", "charlie@test.com", 25);
        userDao.save(user);

        user.setAge(26);
        userDao.update(user);

        User updated = userDao.getById(user.getId());
        assertEquals(26, updated.getAge());
    }

    @Test
    @Order(5)
    void testDeleteUser() {
        User user = new User("David", "david@test.com", 28);
        userDao.save(user);

        userDao.delete(user);
        assertNull(userDao.getById(user.getId()));
    }
}
