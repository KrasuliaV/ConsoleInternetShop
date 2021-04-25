package db;

import model.Order;
import model.Product;
import model.User;
import model.UserRole;

import java.util.ArrayList;
import java.util.List;

public class HomeDB {
    private static ArrayList<Product> productDB;
    private static ArrayList<User> usersDB;

    static {
        Product banana = new Product(1L, "Banana", 32.59);
        Product apple = new Product(2L, "Apple", 22.74);
        Product honey = new Product(3L, "Honey", 150.21);
        Order firstOrder = new Order(1L, "goga", new ArrayList<>(List.of(banana, honey)), true);
        Order secondOrder = new Order(2L, "tosha", new ArrayList<>(List.of(apple, honey)), true);
        Order thirdOrder = new Order(3L, "glavniy", new ArrayList<>(List.of(banana, apple)), true);
        productDB = new ArrayList<>(List.of(banana, apple, honey));
        usersDB = new ArrayList<>(
                List.of(new User(1L, "goga", "granata", false,
                                UserRole.CUSTOMER, new ArrayList<>(List.of(firstOrder)), new ArrayList<>()),
                        new User(2L, "tosha", "pernatiy", false,
                                UserRole.CUSTOMER, new ArrayList<>(List.of(secondOrder)), new ArrayList<>()),
                        new User(3L, "glavniy", "pahan", false,
                                UserRole.ADMIN, new ArrayList<>(List.of(thirdOrder)), new ArrayList<>())));
    }

    public static ArrayList<Product> getProductDB() {
        return productDB;
    }

    public static ArrayList<User> getUsersDB() {
        return usersDB;
    }
}
