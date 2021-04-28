package service;

import model.User;

import java.util.List;

public interface UserService {

    /**
     * Used to login a user
     *
     * @param username user name
     * @param password user password
     * @return outcome of login - success or not
     */
    User login(String username, String password);

    User register(String username, String password);

    void sendMessageToManager(User user, String message);

    void sendMessageToClient(String userId, String message);

    List<User> getAllUserNotAdmin();

    boolean changeStatus(String choiceUserId);
}
