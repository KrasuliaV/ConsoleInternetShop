package view.impl;

import model.Order;
import model.Product;
import model.User;
import service.OrderService;
import service.ProductService;
import service.UserService;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;
import service.impl.UserServiceImpl;
import view.SubMenu;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserMenu implements SubMenu {

    public static final String ENTER_RIGHT_OPERATION = "Enter right operation";
    public static final String EMPTY_BUCKET = "You bucket is empty";

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    private final Scanner scanner;
    Order order;

    public UserMenu() {
        this.userService = new UserServiceImpl();
        this.productService = new ProductServiceImpl();
        this.orderService = new OrderServiceImpl();
        scanner = new Scanner(System.in);
    }

    @Override
    public void productsSubMenuShow(User user) {
        checkOrderForExistence();
        String[] productItems = {"1.Choose product", "2.Search product", "3.Order checkout", "0.Exit"};
        showItems(productItems);
        while (true) {
            String choice = scanner.next();
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
            System.out.println(user.getOrderList());
        }
        new MainMenu().showSubMenu(this, user);
    }

    @Override
    public void usersMenuShow(User user) {
        if (!user.getMassageList().isEmpty()) {
            System.out.println(user.getMassageList());
        }
        System.out.println("Enter tour question to manager:");
        userService.sendMessageToManager(user, scanner.next());
        System.out.println("Your message was sent to the manager");
        new MainMenu().showSubMenu(this, user);
    }

    private void productChooserMenu(User user) {
        checkOrderForExistence();
        int pageCounter = 1;
        List<Product> allProductList = productService.getAllProductList();
        while (true) {
            System.out.println(" - enter product number\n - N(for go to next prod. page)\n - B(for go to Bucket)\n - E(for exit from shop)");
            showProductPage(getProductPage(allProductList, pageCounter, 2));
            String choice = scanner.next().toUpperCase(Locale.ROOT);
            switch (choice) {
                case "B":
                    pageCounter = 1;
                    checkoutOrder(user);
                    break;
                case "N":
                    pageCounter++;
                    pageCounter = getPageNumbers(2, allProductList.size()) >= pageCounter ? pageCounter : 1;
                    showProductPage(getProductPage(allProductList, pageCounter, 2));
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
        System.out.println("Enter product name or B(for go to Bucket) or C(for choose product) or E(for exit from shop) ");
        while (true) {
            String choice = scanner.next().toUpperCase(Locale.ROOT);
            switch (choice) {
                case "B":
                    checkoutOrder(user);
                    break;
                case "C":
                    productChooserMenu(user);
                    break;
                case "E":
                    exit();
                    break;
                default:
                    if (orderService.addProductToBucket(order, choice)) { // What better pass to method: product or product name
                        searchingMenu(user);
                    }
                    System.out.println("You enter wrong product name. Try again");
            }
        }
    }

    private void checkoutOrder(User user) {
        if (order == null) {
            System.out.println(EMPTY_BUCKET);
            productsSubMenuShow(user);
        } else {
            List<Product> products = order.getProducts();
            productListChecker(user, products);
        }
    }

    private void productListChecker(User user, List<Product> products) {
        if (products.isEmpty()) {
            System.out.println(EMPTY_BUCKET);
            productsSubMenuShow(user);
        } else {
            showProductPage(products);
            confirmationChooser(user);
        }
    }

    private void confirmationChooser(User user) {
        System.out.println("Choose operation: Y(confirm order) or C(for choose product) or E(for exit from shop)");
        while (true) {
            switch (scanner.next().toUpperCase()) {
                case "Y":
                    orderService.confirmOrder(order, user);
                    new MainMenu().showSubMenu(this, user);//orderService.confirmOrder(user, order)
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
