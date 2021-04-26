package view.impl;


import model.User;
import model.UserRole;
import service.UserService;
import service.impl.UserServiceImpl;
import view.Menu;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;

public class LoginMenu implements Menu {

    private UserService userService;
    private String[] items = {"1.Login", "2.Register", "0. Exit"};
    private Scanner scanner;

    public LoginMenu() {
        this.userService = new UserServiceImpl();
        scanner = new Scanner(System.in);
    }

    public void show() {
        showItems(items);

        while (true) {
            switch (scanner.next()) {
                case "1":
                    loginSubMenu();
                    break;
                case "2":
                    registerSubMenu();
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.out.println("Enter right operation number");
            }
        }
    }

    @Override
    public void exit() {
        System.exit(1);
    }

    private void loginSubMenu() {
        User user = getUser(userService::login);
        if (Optional.ofNullable(user).isPresent()) {
            if (user.getUserRole().equals(UserRole.CUSTOMER)) {
                new MainMenu().showSubMenu(new UserMenu(), user);
            } else new MainMenu().showSubMenu(new AdminMenu(), user);
        } else {
            System.out.println("Wrong username/password");
            show();
        }
    }

    private void registerSubMenu() {
        User user = getUser(userService::register);
        new MainMenu().showSubMenu(new UserMenu(), user);
    }

    private User getUser(BiFunction<String, String, User> method) {
        System.out.println("input login:");
        String login = scanner.next();

        System.out.println("input password:");
        String password = scanner.next();
        return method.apply(login, password);
    }
}