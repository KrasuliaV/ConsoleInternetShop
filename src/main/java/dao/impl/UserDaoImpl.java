package dao.impl;

import dao.UserDao;
import db.HomeDB;
import model.Order;
import model.User;
import model.UserRole;

import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {

    private static Long userId = 3L;

    @Override
    public User create(User user) {
        userId++;
        user.setId(userId);
        HomeDB.getUsersDB().add(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return HomeDB.getUsersDB().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User delete(User user) {
        HomeDB.getUsersDB().remove(user);
        return user;
    }

    @Override
    public User update(User user) {
        return HomeDB.getUsersDB().set(HomeDB.getUsersDB().indexOf(getById(user.getId())), user);
    }

    @Override
    public List<User> getAll() {
        return HomeDB.getUsersDB();
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return HomeDB.getUsersDB().stream()
                .filter(user -> user.getUserName().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByRole(UserRole userRole) {
        return HomeDB.getUsersDB().stream()
                .filter(user -> user.getUserRole().equals(userRole))
                .collect(Collectors.toList());
    }

    @Override
    public User getByOrderId(String orderId) {
        return HomeDB.getUsersDB().stream()
                .filter(user -> isThereOrder(user, orderId))
                .findFirst()
                .orElse(null);
    }

    private boolean isThereOrder(User user, String orderId) {
        return user.getOrderList()
                .stream()
                .map(Order::getId)
                .anyMatch(id -> id.equals(Long.parseLong(orderId)));
    }
}
