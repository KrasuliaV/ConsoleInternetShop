package view.impl;


import model.User;
import model.UserRole;
import service.UserService;
import view.Menu;

import java.util.Optional;
import java.util.Scanner;

public class LoginMenu implements Menu {

    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserServiceImpl();
    new MainMenu().showSubMenu(new UserMenu(), user);

    private String[] items = {"1.Login", "2.Register", "3.Back", "0.Exit"};


    @Override
    public void show() {
        while (true) {
            for (String item : items) {
                System.out.println(item);
            }
            System.out.println("Choose option:");

            switch (scanner.nextLine()) {
                case "1":
                    loginSubMenu();
                    break;

                case "2":
                    registerSubMenu();
                    break;
                case "9":
                    back();
                    break;
                case "0":
                    exitProgram();
                    break;
            }
        }
    }

    private void registerSubMenu() {
        System.out.println("login:");
        String login = scanner.nextLine();

        System.out.println("password:");
        String password = scanner.nextLine();

        User user = userService.register(login, password);
        new MainMenu().showSubMenu(new UserMenu(), user);
    }

    private void loginSubMenu() {
        System.out.println("your login:");
        String login = scanner.nextLine();

        System.out.println("your password:");
        String password = scanner.nextLine();

        User user = userService.login(login, password);
        if (Optional.ofNullable(user).isPresent()) {
            if (user.getUserRole().equals(UserRole.USER)){
                new MainMenu().showSubMenu(new UserMenu(), user);
            } else {
                new MainMenu().showSubMenu(new AdminMenu(), user);
            }
        } else {
            System.out.println("Error! Wrong Login or password.");
            show();
        }
    }

    @Override
    public void back () {
        exitProgram();
    }
}