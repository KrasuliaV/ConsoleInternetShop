package view.impl;


import model.User;
import model.UserRole;
import service.UserService;
import service.impl.UserServiceImpl;
import view.Menu;

import java.util.Optional;
import java.util.Scanner;

public class LoginMenu implements Menu {

    private UserService userService;
    private String[] items = {"1.Login", "2.Register", "0. Exit"};
    private Scanner scanner;

    public LoginMenu() {
        this.userService = new UserServiceImpl();
    }

    public void show() {
        showItems(items);

        scanner = new Scanner(System.in);

        while (true) {
            String choice = scanner.next();

            switch (choice) {
                case "1":
                    loginSubMenu(scanner);
                    break;
                case "2":
                    registerSubMenu(scanner);
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

    private void loginSubMenu(Scanner scanner) {
        System.out.println("input login:");
        String login = scanner.next();

        System.out.println("input password:");
        String password = scanner.next();

        User user = userService.login(login, password);
        if (Optional.ofNullable(user).isPresent()) {
            if (user.getUserRole().equals(UserRole.CUSTOMER)) {
                new MainMenu().showSubMenu(new UserMenu(), user);
            } else new MainMenu().showSubMenu(new AdminMenu(), user);
        } else {
            System.out.println("Wrong username/password");
            show();
        }
    }

    private void registerSubMenu(Scanner scanner) {
        System.out.println("input login:");
        String login = scanner.next();

        System.out.println("input password:");
        String password = scanner.next();
        User user = userService.register(login, password);
        new MainMenu().showSubMenu(new UserMenu(), user);
    }
}