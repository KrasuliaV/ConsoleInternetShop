package model;


import java.util.ArrayList;

public class User extends AbstractEntity {
    private String userName;
    private String password;
    private boolean isBlocked;
    private UserRole role;
    private ArrayList<Order> order;

    public void setOrder(ArrayList<Order> order) {
        this.order = order;
    }

    public ArrayList<Order> getOrder() {
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

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public long getId() {
        return super.getId();
    }
}
