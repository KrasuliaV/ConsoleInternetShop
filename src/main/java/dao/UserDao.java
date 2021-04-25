package dao;

import model.User;
import model.UserRole;

import java.util.List;

public interface UserDao extends Dao<User> {

    User getByUsernameAndPassword(String username, String password);

    List<User> getUsersByRole(UserRole user);

    User getByOrderId(String orderId);

}
