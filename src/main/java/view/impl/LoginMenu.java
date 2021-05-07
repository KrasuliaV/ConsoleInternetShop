package view.impl;


import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import view.Menu;
import view.style.ConsoleColors;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;

public class LoginMenu implements Menu {

    private final UserService userService;
    private static final String[] items = {"1.Login", "2.Register", "0.Exit"};
    private final Scanner scanner;

    public LoginMenu() {
        this.userService = new UserServiceImpl();
        scanner = new Scanner(System.in);
    }

    public void show() {
        showItems(items);

        while (true) {
            switch (scanner.nextLine()) {
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
                    System.out.println(ENTER_RIGHT_OPERATION);
            }
        }
    }

    @Override
    public void exit() {
        scanner.close();
        System.out.printf("%sGoodbye, my dear friend.%n We will be waiting for you next time!!!", ConsoleColors.YELLOW_BOLD.getColorCode());
        System.exit(1);
    }

    private void loginSubMenu() {
        User user = getUser(userService::login);

        if (Optional.ofNullable(user).isPresent()) {
            checkUser(user);
        } else {
            System.out.printf("%sWrong username/password%s%n", ConsoleColors.RED.getColorCode(), ConsoleColors.RESET.getColorCode());
            show();
        }
    }

    private void checkUser(User user) {
        if (user.getUserRole().equals(User.UserRole.CUSTOMER)) {
            checkBlockingUserStatus(user);
        } else new MainMenu().showSubMenu(new AdminMenu(), user);
    }

    private void checkBlockingUserStatus(User user) {

        if (user.isBlocked()) {
            System.out.printf("%sYour account was blocked by admin!!!!%nYou can't use this shop%s%n", ConsoleColors.RED.getColorCode(), ConsoleColors.RESET.getColorCode());
            show();
        } else {
            new MainMenu().showSubMenu(new UserMenu(), user);
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