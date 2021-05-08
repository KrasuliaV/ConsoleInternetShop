package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
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
        return login(username, password) == null ? userDao.create(new User(username, password))
                : login(username, password);
    }

    @Override
    public List<User> getAllUserNotAdmin() {
        return userDao.getUsersByRole(User.UserRole.CUSTOMER);
    }

    @Override
    public boolean changeStatus(String choiceUserId) {
        if (!choiceUserId.matches("[\\d]+")) {
            return false;
        }

        User user = userDao.getById(Long.parseLong(choiceUserId));
        if (user == null) {
            return false;
        }

        user.setBlocked(!user.isBlocked());
        return true;
    }

    @Override
    public User getUserById(String enteringUserId) {
        return enteringUserId.matches("[\\d]+") ?
                Optional.ofNullable(userDao.getById(Long.parseLong(enteringUserId))).orElse(null) : null;
    }
}
