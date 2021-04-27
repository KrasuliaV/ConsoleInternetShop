package service.impl;

import dao.OrderDao;
import dao.ProductDao;
import dao.impl.OrderDaoImpl;
import dao.impl.ProductDaoImpl;
import model.Order;
import model.Product;
import model.User;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final ProductDao productDao;
    private final OrderDao orderDao;

    public OrderServiceImpl() {
        this.productDao = new ProductDaoImpl();
        this.orderDao = new OrderDaoImpl();
    }

    @Override
    public boolean addProductToBucket(Order order, String productId) {
        if (productId.matches("\\d")) {
            Product product = productDao.getById(Long.parseLong(productId));
            if (product != null) {
                order.getProducts().add(product);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void confirmOrder(Order order, User user) {
        if (order != null && user != null) {
            order.setOwnerName(user.getUserName());
            orderDao.create(order);
        }
    }

    @Override
    public boolean changeStatus(String choiceOrderId) {
        if (choiceOrderId.matches("[\\d]+")) {
            Order order = orderDao.getById(Long.parseLong(choiceOrderId));
            if (order != null) {
                order.setConfirmed(true);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

}
