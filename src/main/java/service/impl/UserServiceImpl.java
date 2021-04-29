package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import model.UserRole;
import service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public User login(String username, String password) {
        return userDao.getByUsernameAndPassword(username, password);
    }

    @Override
    public User register(String username, String password) {
        if (login(username, password) == null) {
            return userDao.create(new User(username, password));
        } else {
            return login(username, password);
        }
    }

    @Override
    public List<User> getAllUserNotAdmin() {
        return userDao.getUsersByRole(UserRole.CUSTOMER);
    }

    @Override
    public boolean changeStatus(String choiceUserId) {
        if (choiceUserId.matches("[\\d]+")) {
            User user = userDao.getById(Long.parseLong(choiceUserId));
            if (user != null) {
                user.setBlocked(!user.isBlocked());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public User getUserById(String enteringUserId) {
        if (enteringUserId.matches("[\\d]+")) {
            return Optional.ofNullable(userDao.getById(Long.parseLong(enteringUserId)))
                    .orElse(null);
        } else {
            return null;
        }
    }
}
