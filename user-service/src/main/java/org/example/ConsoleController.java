package org.example;

import java.util.Scanner;

public class ConsoleController {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(UserService userService) {
        this.userService = userService;
    }

    public void start() {
        while (true) {
            System.out.println("\n1. Добавить пользователя");
            System.out.println("2. Показать всех");
            System.out.println("3. Удалить пользователя");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addUser();
                case 2 -> showUsers();
                case 3 -> deleteUser();
                case 4 -> updateUser();
                case 5 -> { return; }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void addUser() {
        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        System.out.println("Введите email:");
        String email = scanner.nextLine();

        System.out.println("Введите возраст:");
        int age = scanner.nextInt();
        scanner.nextLine();

        Long userId = userService.createUser(name, email, age);
        System.out.println("Пользователь создан с ID: " + userId);
    }

    private void showUsers() {
        userService.getAllUsers().forEach(System.out::println);
    }

    private void deleteUser() {
        System.out.println("Введите ID пользователя для удаления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        userService.deleteUser(id);
        System.out.println("Пользователь удалён (если существовал).");
    }

    private void updateUser() {
        System.out.println("Введите ID пользователя для обновления:");
        long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Введите новое имя:");
        String name = scanner.nextLine();

        System.out.println("Введите новый email:");
        String email = scanner.nextLine();

        System.out.println("Введите новый возраст:");
        int age = scanner.nextInt();
        scanner.nextLine();

        userService.updateUser(id, name, email, age);
        System.out.println("Пользователь обновлён (если существовал).");
    }
}
