package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Order extends AbstractEntity {

    private String ownerName;

    private List<Product> products;

    private boolean isConfirmed;

    public Order() {
        this.products = new ArrayList<>();
    }

    public Order(Long id, String ownerName, List<Product> products, boolean isConfirmed) {
        super(id);
        this.ownerName = ownerName;
        this.products = products;
        this.isConfirmed = isConfirmed;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    private Map<String, Long> getRightProductList() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getName, Collectors.counting()));
    }

    @Override
    public String toString() {
        return "Order{ id:" + id +
                ", ownerName='" + ownerName + '\'' +
                ", products=" + getRightProductList() +
                ", isConfirmed=" + isConfirmed +
                '}';
    }
}
