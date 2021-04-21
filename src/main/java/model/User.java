package model;

import java.util.ArrayList;

public class User extends AbstractEntity {
    private String userName;
    private String password;
    private boolean isBlocked;
    private UserRole role;
    private ArrayList<String> order;

    public void setOrder(ArrayList<String> order) {
        this.order = order;
    }

    public ArrayList<String> getOrder() {
        return order;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    enum UserRole {
        ADMIN,
        CUSTOMER
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public long getId() {
        return super.getId();
    }
}
