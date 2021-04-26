package view.impl;

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
import java.util.Optional;
import java.util.Scanner;

public class AdminMenu implements SubMenu {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final Scanner scanner;

    public AdminMenu() {
        this.productService = new ProductServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.userService = new UserServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void productsSubMenuShow(User user) {
        String[] productItems = {"1.Edit existing product", "2.Add new product", "0.Exit"};
        showItems(productItems);
        while (true) {
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    editProduct(user);
                    break;
                case "2":
                    addProductMenu(user);
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
    public void ordersMenuShow(User user) {
        System.out.println(orderService.getAllOrders());
        System.out.println("Enter order number for changeStatus or B(for back to last menu)");
        while (true) {
            String choice = scanner.next();
            switch (choice) {
                case "B":
                    new MainMenu().showSubMenu(this, user);
                    break;
                default:
                    if (!orderService.changeStatus(choice)) {
                        System.out.println("Enter right status number");
                    }
            }
        }
    }

    @Override
    public void usersMenuShow(User user) {
        List<String> messageList = user.getMassageList();
        if (messageList.isEmpty()) {
            System.out.println("You don't have some message from client");
            new MainMenu().showSubMenu(this, user);
        } else {
            System.out.println(messageList);
            chooserMessageAndAnswering(user, messageList);
        }
    }

    private void chooserMessageAndAnswering(User user, List<String> messageList) {
        if (messageList.size() == 1) {
            System.out.println("Enter answer for client");
            userService.sendMessageToClient(getUserIdFromMessageString(messageList.get(0)), scanner.next());
        } else {
            System.out.println("Enter user id for choose message");
            String enteringUserId = scanner.next();
            getClientId(messageList, enteringUserId)
                    .ifPresentOrElse(clientId -> chatWithOneClient(clientId, messageList),
                            () -> inputWrongAnswerAndReturnedToThisMenu(user)
                    );
        }
    }

    private String getUserIdFromMessageString(String message) {
        return message.split(" ", 2)[0];
    }

    private void inputWrongAnswerAndReturnedToThisMenu(User user) {
        System.out.println("You enter wrong id number");
        usersMenuShow(user);
    }

    private Optional<String> getClientId(List<String> messageList, String clientId) {
        return messageList.stream()
                .map(this::getUserIdFromMessageString)
                .filter(messageId -> messageId.equals(clientId))
                .findFirst();
    }

    private void chatWithOneClient(String clientId, List<String> messageList) {
        messageList.stream()
                .filter(message -> getUserIdFromMessageString(message).equals(clientId))
                .forEach(System.out::println);
        userService.sendMessageToClient(clientId, scanner.next());
    }

    private void editProduct(User user) {
        System.out.println("Enter product number or A(for add new product) or B(for back to last menu)");
        showProductList(productService.getAllProductList());
        while (true) {
            String choice = scanner.next();
            switch (choice) {
                case "A":
                    addProductMenu(user);
                    break;
                case "B":
                    productsSubMenuShow(user);
                    break;
                default:
                    Optional.ofNullable(productService.getProductById(scanner.next()))
                            .ifPresentOrElse((product -> makeChangeToProduct(product, user)),
                                    () -> System.out.println("Enter right operation letter"));
            }
        }
    }

    private void makeChangeToProduct(Product product, User user) {
        System.out.println("Enter what you want to change N(for name change) or P(for price change) or B(for back to last menu)");
        while (true) {
            String choice = scanner.next();
            switch (choice) {
                case "N":
                    changeProductName(product);
                    makeChangeToProduct(product, user);
                    break;
                case "P":
                    changeProductPrice(product);

                    break;
                case "B":
                    editProduct(user);
                    break;
                default:
                    System.out.println("Enter right operation letter");
                    makeChangeToProduct(product, user);
            }
        }

    }

    private void changeProductPrice(Product product) {
        System.out.println("Enter new product name");
        productService.setNewName(product, scanner.next());
    }

    private void changeProductName(Product product) {
        System.out.println("Enter new product price");
        String newPrice = scanner.next();
        if (newPrice.matches("[\\d.\\d]+")) {
            productService.setNewPrice(product, newPrice);
        } else {
            System.out.println("Enter right price");
            changeProductName(product);
        }
    }

    private void addProductMenu(User user) {
        System.out.println("Enter product name:");
        String name = scanner.next();

        System.out.println("Enter product price:");
        String price = scanner.next();

        productService.createProduct(name, price);
        productsSubMenuShow(user);
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}
