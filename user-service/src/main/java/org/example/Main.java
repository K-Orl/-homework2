package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserDao userDao = new UserDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Показать всех");
            System.out.println("3. Удалить пользователя");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Выход");

            System.out.print("Выберите пункт меню: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addUser();
                case "2" -> showAllUsers();
                case "3" -> deleteUser();
                case "4" -> updateUser();
                case "5" -> running = false;
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        HibernateUtil.shutdown();
    }

    private static void addUser() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User(name, email, age);
        userDao.save(user);
        System.out.println("Пользователь добавлен!");
    }

    private static void showAllUsers() {
        List<User> users = userDao.getAll();
        if (users.isEmpty()) {
            System.out.println("Пользователей нет.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void deleteUser() {
        System.out.print("ID пользователя для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());

        User user = userDao.getById(id);
        if (user != null) {
            userDao.delete(user);
            System.out.println("Пользователь удалён!");
        } else {
            System.out.println("Пользователь с таким ID не найден.");
        }
    }

    private static void updateUser() {
        System.out.print("ID пользователя для обновления: ");
        Long id = Long.parseLong(scanner.nextLine());

        User user = userDao.getById(id);
        if (user == null) {
            System.out.println("Пользователь с таким ID не найден.");
            return;
        }

        System.out.print("Новое имя (Enter для пропуска): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) user.setName(name);

        System.out.print("Новый email (Enter для пропуска): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) user.setEmail(email);

        System.out.print("Новый возраст (Enter для пропуска): ");
        String ageInput = scanner.nextLine();
        if (!ageInput.isBlank()) user.setAge(Integer.parseInt(ageInput));

        userDao.update(user);
        System.out.println("Пользователь обновлён!");
    }
}
