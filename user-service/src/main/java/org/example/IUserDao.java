package org.example;

import java.util.List;

public interface IUserDao {
    void save(User user);
    User getById(Long id);
    void update(User user);
    void delete(User user);
    List<User> getAll();
}
