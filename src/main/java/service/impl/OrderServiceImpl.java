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
    public boolean addProductToBucket(Order order, String productNameOrId) {
        Product product = productNameOrId.matches("[\\d]+") ?
                productDao.getById(Long.parseLong(productNameOrId))
                : productDao.getByProductName(productNameOrId);

        if (product == null) {
            return false;
        }
        order.getProducts().add(product);
        return true;
    }

    @Override
    public void confirmOrder(Order order, User user) {
        if (order == null || user == null) {
            return;
        }

        order.setOwnerName(user.getUserName());
        order.setTotalPrice();
        orderDao.create(order);
    }

    @Override
    public boolean changeStatus(String choiceOrderId) {
        if (!choiceOrderId.matches("[\\d]+")) {
            return false;
        }

        Order order = orderDao.getById(Long.parseLong(choiceOrderId));
        if (order == null) {
            return false;
        }

        order.setConfirmed(true);
        return true;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAll();
    }

}
