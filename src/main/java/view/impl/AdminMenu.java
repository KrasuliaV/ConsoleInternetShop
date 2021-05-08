package view.impl;

import model.ChatPM;
import model.Product;
import model.User;
import service.ChatService;
import service.OrderService;
import service.ProductService;
import service.UserService;
import service.impl.ChatServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;
import service.impl.UserServiceImpl;
import view.SubMenu;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AdminMenu implements SubMenu {

    private final Scanner scanner;

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final ChatService chatService;

    public AdminMenu() {
        scanner = new Scanner(System.in);
        this.productService = new ProductServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.userService = new UserServiceImpl();
        this.chatService = new ChatServiceImpl();
    }

    @Override
    public void productsSubMenuShow(User user) {
        String[] productItems = {"1.Edit existing product", "2.Add new product", "0.Exit"};
        showItems(productItems);
        while (true) {
            switch (scanner.nextLine()) {
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
                    System.out.println(ENTER_RIGHT_OPERATION);
            }
        }
    }

    @Override
    public void ordersMenuShow(User user) {
        changeStatus(orderService::getAllOrders, orderService::changeStatus, "order", user);
    }

    @Override
    public void usersMenuShow(User user) {
        String[] productItems = {"1.Change user status", "2.Message from customer", "0.Exit"};
        showItems(productItems);
        while (true) {
            switch (scanner.nextLine()) {
                case "1":
                    changeUserStatus(user);
                    break;
                case "2":
                    checkMessage(user);
                    break;
                case "0":
                    exit();
                    break;
                default:
                    System.out.println(ENTER_RIGHT_OPERATION);
            }
        }
    }

    private void changeUserStatus(User user) {
        changeStatus(userService::getAllUserNotAdmin, userService::changeStatus, "user", user);
    }

    private <T> void changeStatus(Supplier<List<T>> supplier, Predicate<String> predicate, String model, User user) {
        supplier.get().forEach(System.out::println);
        while (true) {
            System.out.printf("Enter %s id for changeStatus or B(for back to last menu)%n", model);
            String choice = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (choice) {
                case "B":
                    new MainMenu().showSubMenu(this, user);
                    break;
                default:
                    if (!predicate.test(choice)) {
                        System.out.println(ENTER_RIGHT_OPERATION);
                    } else {
                        new MainMenu().showSubMenu(this, user);
                    }
            }
        }
    }

    private void checkMessage(User user) {
        List<ChatPM> chatsWithoutAnswer = chatService.getChatsWithoutAnswer();

        if (chatsWithoutAnswer.isEmpty()) {
            System.out.println("Shop doesn't have some message without answer");
            new MainMenu().showSubMenu(this, user);
        } else {
            showListWithMessagesInformation(chatsWithoutAnswer);
            chooserMessageAndAnswering(user, chatsWithoutAnswer);
        }
    }

    private void showListWithMessagesInformation(List<ChatPM> chatsWithoutAnswer) {
        chatsWithoutAnswer.stream()
                .map(chatPM -> chatPM.getCustomerName() + " id:" + chatPM.getCustomerId())
                .forEach(System.out::println);
    }

    private void chooserMessageAndAnswering(User user, List<ChatPM> chatsWithoutAnswer) {

        if (chatsWithoutAnswer.size() == 1) {
            chatWithCustomer(chatsWithoutAnswer.get(0), user);
        } else {
            chatChooser(user, chatsWithoutAnswer);
        }
    }

    private void chatChooser(User user, List<ChatPM> chatsWithoutAnswer) {
        System.out.println("Enter user id for choose chat");
        String enteringUserId = scanner.nextLine();

        if (enteringUserId.matches("[\\d]+")) {
            ChatPM chatPM = getChatByCustomerId(enteringUserId, chatsWithoutAnswer);
            answeringToCustomer(user, chatsWithoutAnswer, chatPM);
        } else {
            chatChooser(user, chatsWithoutAnswer);
        }
    }

    private void answeringToCustomer(User user, List<ChatPM> chatsWithoutAnswer, ChatPM chatPM) {

        if (chatPM.getMessageList().isEmpty()) {
            System.out.println("You enter incorrect user id");
            chatChooser(user, chatsWithoutAnswer);
        } else {
            chatWithCustomer(chatPM, user);
        }
    }

    private void chatWithCustomer(ChatPM chatPM, User user) {
        System.out.println(chatPM.getMessageList());
        System.out.println("Enter answer for customer");
        chatService.sendMessageToCustomer(chatPM, scanner.nextLine());
        System.out.println("Your message was sent to the customer");

        new MainMenu().showSubMenu(this, user);
    }

    private ChatPM getChatByCustomerId(String enteringUserId, List<ChatPM> chatsWithoutAnswer) {
        return chatsWithoutAnswer.stream()
                .filter(chat -> chat.getCustomerId() == Long.parseLong(enteringUserId))
                .findFirst()
                .orElse(new ChatPM());
    }

    private void editProduct(User user) {
        int pageCounter = 1;
        List<Product> allProductList = productService.getAllProductList();
        while (true) {
            String[] productItems = {" - Enter product number", " - N(for go to next prod. page)", " - A(for add new product)", " - B(for back to last menu)"};
            showItems(productItems);
            showProductPage(getPage(allProductList, pageCounter, 2));
            String choice = scanner.nextLine().toUpperCase(Locale.ROOT);
            switch (choice) {
                case "A":
                    addProductMenu(user);
                    break;
                case "N":
                    pageCounter++;
                    pageCounter = getPageNumbers(2, allProductList.size()) >= pageCounter ? pageCounter : 1;
                    break;
                case "B":
                    productsSubMenuShow(user);
                    break;
                default:
                    Optional.ofNullable(productService.getProductById(choice))
                            .ifPresentOrElse((product -> makeChangeToProduct(product, user)),
                                    () -> System.out.println(ENTER_RIGHT_OPERATION));
            }
        }
    }

    private void makeChangeToProduct(Product product, User user) {
        String[] productItems = {" - N(for name change)", " - P(for price change)", " - B(for back to last menu)"};
        showItems(productItems);
        switch (scanner.nextLine().toUpperCase(Locale.ROOT)) {
            case "N":
                changeProductName(product);
                productsSubMenuShow(user);
                break;
            case "P":
                changeProductPrice(product);
                productsSubMenuShow(user);
                break;
            case "B":
                editProduct(user);
                break;
            default:
                System.out.println(ENTER_RIGHT_OPERATION);
                makeChangeToProduct(product, user);
            }
    }

    private void changeProductName(Product product) {
        System.out.println("Enter new product name");
        productService.setNewName(product, scanner.nextLine());
    }

    private void changeProductPrice(Product product) {
        System.out.println("Enter new product price");
        String newPrice = getRightPrice(scanner.nextLine());
        productService.setNewPrice(product, newPrice);
    }

    private void addProductMenu(User user) {
        System.out.println("Enter product name:");
        String name = scanner.nextLine();

        System.out.println("Enter product price:");
        String price = getRightPrice(scanner.nextLine());
        productService.createProduct(name, price);
        productsSubMenuShow(user);
    }

    private String getRightPrice(String price) {

        if (price.matches("[\\d.\\d]+")) {
            return price;
        } else {
            System.out.println("Enter right price");
            return getRightPrice(scanner.nextLine());
        }
    }

    @Override
    public void exit() {
        new LoginMenu().show();
    }
}
