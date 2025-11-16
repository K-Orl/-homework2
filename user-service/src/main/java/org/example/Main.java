package org.example;

public class Main {
    public static void main(String[] args) {
        IUserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        ConsoleController controller = new ConsoleController(userService);
        controller.start();
    }
}
