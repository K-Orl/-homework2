package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {

    public void save(User user) {
        executeInsideTransaction(session -> session.save(user));
    }

    public void update(User user) {
        executeInsideTransaction(session -> session.update(user));
    }

    public void delete(User user) {
        executeInsideTransaction(session -> session.delete(user));
    }

    public User getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Вспомогательный метод для операций с транзакцией
    private void executeInsideTransaction(TransactionalAction action) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            action.execute(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Функциональный интерфейс для передачи лямбды
    @FunctionalInterface
    private interface TransactionalAction {
        void execute(Session session);
    }
}
