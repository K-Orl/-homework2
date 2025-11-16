package org.example.dao;

import org.example.HibernateUtil;
import org.example.User;
import org.example.UserDao;
import org.junit.jupiter.api.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoIT {

    private UserDao userDao;

    @BeforeAll
    public void setUp() {
        userDao = new UserDao();
    }

    @AfterAll
    public void tearDown() {
        HibernateUtil.shutdown(); // закрываем сессию после всех тестов
    }

    @Test
    @Order(1)
    public void testSaveUser() {
        User user = new User("Alice", "alice@test.com", 23);
        userDao.save(user);
        Assertions.assertNotNull(user.getId(), "ID должен быть присвоен после сохранения");
    }

    @Test
    @Order(2)
    public void testGetUserById() {
        User user = new User("Bob", "bob@test.com", 30);
        userDao.save(user);

        User loaded = userDao.getById(user.getId());
        Assertions.assertNotNull(loaded, "Пользователь должен быть найден");
        Assertions.assertEquals("Bob", loaded.getName(), "Имя пользователя должно совпадать");
    }

    @Test
    @Order(3)
    public void testGetAllUsers() {
        List<User> users = userDao.getAll();
        Assertions.assertNotNull(users, "Список пользователей не должен быть null");
        Assertions.assertTrue(users.size() > 0, "Список должен содержать хотя бы одного пользователя");
    }

    @Test
    @Order(4)
    public void testUpdateUser() {
        User user = new User("Charlie", "charlie@test.com", 25);
        userDao.save(user);

        user.setAge(26);
        userDao.update(user);

        User updated = userDao.getById(user.getId());
        Assertions.assertEquals(26, updated.getAge(), "Возраст должен быть обновлён");
    }

    @Test
    @Order(5)
    public void testDeleteUser() {
        User user = new User("David", "david@test.com", 28);
        userDao.save(user);

        userDao.delete(user);
        User deleted = userDao.getById(user.getId());
        Assertions.assertNull(deleted, "Пользователь должен быть удалён");
    }
}
