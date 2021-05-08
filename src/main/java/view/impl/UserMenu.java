package view.impl;

import model.Order;
import model.Product;
import model.User;
import service.ChatService;
import service.OrderService;
import service.ProductService;
import service.impl.ChatServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;
import view.SubMenu;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserMenu implements SubMenu {

    public static final String EMPTY_BUCKET = "You bucket is empty";

    private final Scanner scanner;

    private final ProductService productService;
    private final OrderService orderService;
    private final ChatService chatService;

    Order order;

    public UserMenu() {
        scanner = new Scanner(System.in);
        this.productService = new ProductServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.chatService = new ChatServiceImpl();
    }

    @Override
    public void productsSubMenuShow(User user) {
        checkOrderForExistence();
        String[] productItems = {"1.Choose product", "2.Search product", "3.Order checkout", "0.Exit"};
        showItems(productItems);
        while (true) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    productChooserMenu(user);
                    break;
                case "2":
                    searchingMenu(user);
                    break;
                case "3":
                    checkoutOrder(user);
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
    public void ordersMenuShow(User user) {
        List<Order> orderList = user.getOrderList();
        if (orderList.isEmpty()) {
            System.out.println("You don't have some order in this perfect shop!");
        } else {
            user.getOrderList().forEach(System.out::println);
        }
        new MainMenu().showSubMenu(this, user);
    }

    @Override
    public void usersMenuShow(User user) {
        List<String> massageByClientId = chatService.getMassageByClientId(user.getId());
        if (!massageByClientId.isEmpty()) {
            System.out.println(massageByClientId);
        }
        System.out.println("Enter your question to manager:");
        chatService.sendMessageToManager(user, scanner.nextLine());
        System.out.println("Your message was sent to the manager");
        new MainMenu().showSubMenu(this, user);
    }

    private void productChooserMenu(User user) {
        checkOrderForExistence();
        int pageCounter = 1;
        List<Product> allProductList = productService.getAllProductList();
        while (true) {
            String[] productItems = {" - Enter product number", " - N(for go to next prod. page)", " - B(for go to Bucket)", " - P(for go to prev. menu)", " - E(for exit from shop)"};
            showItems(productItems);
            showProductPage(getPage(allProductList, pageCounter, 2));
            String choice = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (choice) {
                case "B":
                    pageCounter = 1;
                    checkoutOrder(user);
                    break;
                case "P":
                    productsSubMenuShow(user);
                    break;
                case "N":
                    pageCounter++;
                    pageCounter = getPageNumbers(2, allProductList.size()) >= pageCounter ? pageCounter : 1;
                    break;
                case "E":
                    exit();
                    break;
                default:
                    if (orderService.addProductToBucket(order, choice)) {
                        productChooserMenu(user);
                    }
                    System.out.println(ENTER_RIGHT_OPERATION);
            }
        }
    }

    private void searchingMenu(User user) {
        checkOrderForExistence();
        String[] productItems = {" - Enter product name", " - 1: Go to Bucket)", " - 2: Choose product", " - 0: Exit from shop"};
        showItems(productItems);
        while (true) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    checkoutOrder(user);
                    break;
                case "2":
                    productChooserMenu(user);
                    break;
                case "0":
                    exit();
                    break;
                default:
                    if (orderService.addProductToBucket(order, choice)) {
                        searchingMenu(user);
                    }
                    System.out.println("You enter wrong product name. Try again");
            }
        }
    }

    private void checkoutOrder(User user) {
        if (order == null) {
            emptyBucketAction(user);
        } else {
            List<Product> products = order.getProducts();
            productListChecker(user, products);
        }
    }

    private void productListChecker(User user, List<Product> products) {
        if (products.isEmpty()) {
            emptyBucketAction(user);
        } else {
            showProductPage(products);
            confirmationChooser(user);
        }
    }

    private void emptyBucketAction(User user) {
        System.out.println(EMPTY_BUCKET);
        productsSubMenuShow(user);
    }

    private void confirmationChooser(User user) {
        String[] productItems = {" - Y(confirm order)", " - C(for choose product)", " - E(for exit from shop)"};
        showItems(productItems);
        while (true) {
            switch (scanner.nextLine().toUpperCase()) {
                case "Y":
                    orderService.confirmOrder(order, user);
                    new MainMenu().showSubMenu(this, user);
                    break;
                case "C":
                    productChooserMenu(user);
                    break;
                case "E":
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

    private void checkOrderForExistence() {
        if (order == null) order = new Order();
    }

}
