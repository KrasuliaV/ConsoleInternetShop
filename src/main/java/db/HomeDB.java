package db;

import model.ChatPM;
import model.Order;
import model.Product;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeDB {
    private static final List<Product> productDB;
    private static final List<User> usersDB;
    private static final List<ChatPM> chatDB;

    static {
        chatDB = new ArrayList<>();
        Product banana = new Product(1L, "Banana", 32.59);
        Product apple = new Product(2L, "Apple", 22.74);
        Product honey = new Product(3L, "Honey", 150.21);
        Product whisky = new Product(4L, "Whisky", 1050.71);
        Product coffee = new Product(5L, "Coffee", 101.53);
        Order firstOrder = new Order(1L, "goga", List.of(banana, honey), true);
        Order secondOrder = new Order(2L, "tosha", List.of(apple, honey), true);
        Order thirdOrder = new Order(3L, "glavniy", List.of(banana, apple), true);

        productDB = new ArrayList<>(List.of(banana, apple, honey, whisky, coffee));
        usersDB = new ArrayList<>(List.of(
                new User(1L, "goga", "granata", false, User.UserRole.CUSTOMER, new ArrayList<>(List.of(firstOrder))),
                new User(2L, "tosha", "pernatiy", true, User.UserRole.CUSTOMER, new ArrayList<>(List.of(secondOrder))),
                new User(3L, "admin", "admin", false, User.UserRole.ADMIN, new ArrayList<>(List.of(thirdOrder)))
        ));
    }

    public static List<Product> getProductDB() {
        return productDB;
    }

    public static List<User> getUsersDB() {
        return usersDB;
    }

    public static List<ChatPM> getChatDB() {
        return chatDB;
    }
}
