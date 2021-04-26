package view;

import model.Product;
import model.User;

import java.util.List;

public interface SubMenu extends Menu {

    void productsSubMenuShow(User user);

    void ordersMenuShow(User user);

    void usersMenuShow(User user);

    default void showProductList(List<Product> productList) {
        productList.stream()
                .map(product -> product.getName() + ", id = " + product.getId() + ", price = " + product.getPrice())
                .forEach(System.out::println);
    }
}
