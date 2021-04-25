package view.impl;

import model.User;
import view.Menu;
import view.SubMenu;

import java.util.Scanner;

public class MainMenu implements Menu {

    private String[] items = {"1.Products menu", "2.Orders menu", "3.User's menu", "0.Exit"};
    private Scanner scanner;

    public void showSubMenu(SubMenu submenu, User user) {
        scanner = new Scanner(System.in);
        showItems(items);
        while (true) {
            switch (scanner.next()) {
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
                    System.out.println("Enter right operation number");
            }
        }
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}
