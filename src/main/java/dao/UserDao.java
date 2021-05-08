package dao;

import model.User;

import java.util.List;

public interface UserDao extends Dao<User> {

    User getByUsernameAndPassword(String username, String password);

    List<User> getUsersByRole(User.UserRole userRole);

}
