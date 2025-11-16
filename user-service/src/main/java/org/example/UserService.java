package org.example;

import java.util.List;

public class UserService {

    private final IUserDao userDao;

    // Конструктор с внедрением DAO
    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    // Создание нового пользователя
    public Long createUser(String name, String email, int age) {
        User user = new User(name, email, age);
        userDao.save(user);
        return user.getId();
    }

    // Получить всех пользователей
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    // Удалить пользователя по ID
    public void deleteUser(Long id) {
        User user = userDao.getById(id);
        if (user != null) {
            userDao.delete(user);
        }
    }

    // Обновить пользователя
    public void updateUser(Long id, String name, String email, int age) {
        User user = userDao.getById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.update(user);
        }
    }
}
