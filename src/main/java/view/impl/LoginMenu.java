package view.impl;


import model.User;
import model.UserRole;
import service.UserService;
import service.impl.UserServiceImpl;
import view.Menu;
import view.style.ConsoleColors;

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
        System.out.printf("%sGoodbye, my dear friend.%n We will be waiting for you next time!!!", ConsoleColors.YELLOW_BOLD.getColorCode());
        System.exit(1);
    }

    private void loginSubMenu() {
        User user = getUser(userService::login);
        if (Optional.ofNullable(user).isPresent()) {
            if (user.getUserRole().equals(UserRole.CUSTOMER)) {
                new MainMenu().showSubMenu(new UserMenu(), user);
            } else new MainMenu().showSubMenu(new AdminMenu(), user);
        } else {
            System.out.printf("%sWrong username/password%s%n", ConsoleColors.RED.getColorCode(), ConsoleColors.RESET.getColorCode());
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