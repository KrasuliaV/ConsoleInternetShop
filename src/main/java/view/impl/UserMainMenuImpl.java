package view.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class UserMenu implements SubMenu {
    private Scanner scanner;
    private Order order;
    private final ProductServiceImpl productServiceImpl;
    String answer;

    public UserMenu() {
        this.productServiceImpl = new ProductServiceImpl();
        scanner = new Scanner(System.in);
    }

    @Override
    public void productsSubMenuShow(User user) {
        String[] productItems = {"1.Choose product", "2.Search product", "3.Order checkout", "0.Exit"};
        answer = scanner.nextLine();
        switch (answer) {
            case ("1") :
                productChooserMenu();
                break;
            case ("2") :
                searchingProductByName();
                break;
            case ("3") :
                checkoutOrder();
                break;
            case ("0") :
                exit();
            default :
                System.out.println("Wrong number. Please, enter correct number");
                this.productChooserMenu();
        }
    }

    @Override
    public void ordersMenuShow(User user) {
        ordersShow(user);
    }

    @Override
    public void usersMenuShow(User user){
        usersMenuShow();
    }

    private void productsShow() {
        for (Product product : productServiceImpl.getAllProducts()) {
            System.out.println(currentProduct.toString());
        }
        printMessage("choose products");
        answer = scanAnswer(this::productsShow);
        if (answer.equals("1")) {
            productChooserMenu();
        }
    }

    private void ordersShow(User user) {
        ArrayList<Order> orders = user.getOrderList();
        for (Order currentOrder : orders) {
            System.out.println(currentOrder.toString());
        }
    }

    private void usersMenuShow(){ }

    private void productChooserMenu(){
        printMessage("choose product by id");
        String input = scanAnswer(this::productChooserMenu);
        if (input.equals("1")) {
            System.out.println("Enter id of product");
        }
        answer = scanner.nextLine();
        Product product = productServiceImpl.getProduct(answer);
        if (Optional.ofNullable(product).isPresent()) {
            order.getListProducts().add(product);
        }
        else {
            System.out.println("not product");
            this.productChooserMenu();
        }
    }

    private void searchingProductByName(){
        printMessage("enter product name");
        String nameProduct = scanner.nextLine();
        Product product = productServiceImpl.getProductByName(nameProduct);
        if (Optional.ofNullable(product).isPresent()) {
            printMessage("add to list products");
            answer = scanAnswer(this::searchingProductByName);
            if (answer.equals("1")) {
                order.getListProducts().add(product);
            }
        }
        else {
            System.out.println("not product");
            this.searchingProductMenu();
        }
    }

    private void checkoutOrder(){
        checkOrderForExistence();
        if (!order.getBox().isEmpty()) {
            for (Product product : order.getListProducts()) {
                System.out.print(product + " ");
            }
            System.out.println("1. checkout your order 2. back to product Chooser Menu 3. back to main menu");
            answer = scanner.nextLine();
            switch (answer) {
                case ("1"):
                    confirmOrder(order, user); // - находится в сервисном уровне;
                    break;
                case ("2"):
                    productChooserMenu();
                    break;
                case ("3"):
                    showSubMenu(SubMenu submenu, User user);
                    break;
                default :
                    System.out.println("Wrong number. Please, enter correct number");
                    this.checkoutOrder();
            }
        }
        else {
            System.out.println("basket is empty");
            showSubMenu(SubMenu submenu, User user);
        }
    }

    void exit();
    {
        new LoginMenu.show();
    }

    private void checkOrderForExistence() {
        if(this.order == null)
            this.order = new Order();
    }

    private void printMessage(String string) {
        System.out.println("1. " + string + " 2. back to main menu");
    }

    private String scanAnswer(Runnable method) {
        String input = scanner.nextLine();
        if (input == "1") {
            return "1";
        }
        if (input == "2") {
            showSubMenu(SubMenu submenu, User user);
        }
        else {
            System.out.println("Wrong number. Please, enter correct number");
            method.run();
        }
        return null;
    }
}
