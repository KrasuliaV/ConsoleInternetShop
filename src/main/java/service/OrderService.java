package service;

import model.Order;
import model.User;

import java.util.List;

public interface OrderService {

    boolean addProductToBucket(Order order, String productId);

    void confirmOrder(Order order, User user);

    boolean changeStatus(String choice);

    List<Order> getAllOrders();
}
