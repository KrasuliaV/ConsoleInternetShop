package model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends AbstractEntity {
    private String userName;

    private String password;

    private boolean isBlocked;

    private UserRole userRole;

    private List<Order> orderList;

    public User() {
        orderList = new ArrayList<>();
    }

    public User(String username, String password) {
        this.userName = username;
        this.password = password;
        this.isBlocked = false;
        this.userRole = UserRole.CUSTOMER;
        this.orderList = new ArrayList<>();
    }

    public User(Long id, String username, String password, boolean isBlocked, UserRole userRole, List<Order> orderList) {
        super(id);
        this.userName = username;
        this.password = password;
        this.isBlocked = isBlocked;
        this.userRole = userRole;
        this.orderList = orderList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isBlocked == user.isBlocked && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && userRole == user.userRole && Objects.equals(orderList, user.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, password, isBlocked, userRole, orderList);
    }

    @Override
    public String toString() {
        return userName +
                "{id=" + id +
                ", isBlocked=" + isBlocked +
                ", orderList=" + orderList +
                '}';
    }
}
