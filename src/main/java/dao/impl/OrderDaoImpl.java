package dao.impl;

import dao.OrderDao;
import db.HomeDB;
import model.Order;
import model.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDaoImpl implements OrderDao {

    private static long orderId = 3;

    @Override
    public Order create(Order order) {
        orderId++;
        order.setId(orderId);
        getUserByOrderOwner(order).getOrderList().add(order);
        return order;
    }

    @Override
    public Order getById(Long id) {
        return HomeDB.getUsersDB().stream()
                .flatMap(user -> user.getOrderList().stream())
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order delete(Order order) {
        getUserByOrder(order).getOrderList().remove(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        List<Order> ordersListOfOneUser = getUserByOrder(order).getOrderList();
        ordersListOfOneUser.set(
                ordersListOfOneUser.indexOf(
                        getById(order.getId())), order);
        return order;
    }

    @Override
    public List<Order> getAll() {
        return HomeDB.getUsersDB().stream()
                .flatMap(user -> user.getOrderList().stream())
                .collect(Collectors.toList());
    }

    private User getUserByOrder(Order order) {
        return HomeDB.getUsersDB().stream()
                .filter(user -> isThereOrder(user, order.getId()))
                .findFirst()
                .orElse(null);
    }

    private boolean isThereOrder(User user, Long orderId) {
        return user.getOrderList()
                .stream()
                .map(Order::getId)
                .anyMatch(id -> id.equals(orderId));
    }

    private User getUserByOrderOwner(Order order) {
        return HomeDB.getUsersDB().stream()
                .filter(user -> user.getUserName().equals(order.getOwnerName()))
                .findFirst()
                .orElse(null);
    }
}
