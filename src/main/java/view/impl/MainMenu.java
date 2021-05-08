package view.impl;

import model.User;
import view.Menu;
import view.SubMenu;

import java.util.Scanner;

public class MainMenu implements Menu {

    private final Scanner scanner = new Scanner(System.in);

    private static String[] items = {"1.Products menu", "2.Orders menu", "3.User's menu", "0.Exit"};

    public void showSubMenu(SubMenu submenu, User user) {
        showItems(items);
        while (true) {
            switch (scanner.nextLine()) {
                case "1":
                    submenu.productsSubMenuShow(user);
                    break;
                case "2":
                    submenu.ordersMenuShow(user);
                    break;
                case "3":
                    submenu.usersMenuShow(user);
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
        new LoginMenu().show();
    }
}
