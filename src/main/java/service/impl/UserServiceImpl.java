package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import model.UserRole;
import service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public void sendMessageToManager(User user, String message) {
        List<User> adminList = userDao.getUsersByRole(UserRole.ADMIN);
        adminList.sort(Comparator.comparingInt(admin -> admin.getMassageList().size()));
        User adminWithMinMessageList = adminList.get(0);
        adminWithMinMessageList.getMassageList().add(user.getId() + " " + message);
        userDao.update(adminWithMinMessageList);
    }

    @Override
    public void sendMessageToClient(String clientId, String message) {
        User client = userDao.getById(Long.parseLong(clientId));
        client.getMassageList().add(message);
        userDao.update(client);
    }

    @Override
    public List<User> getAllUserNotAdmin() {
        return userDao.getAll().stream()
                .filter(user -> user.getUserRole().equals(UserRole.CUSTOMER))
                .collect(Collectors.toList());
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
}
