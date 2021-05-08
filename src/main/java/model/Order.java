package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Order extends AbstractEntity {

    private String ownerName;

    private final List<Product> products;

    private boolean isConfirmed;

    private BigDecimal totalPrice;

    public Order() {
        this.products = new ArrayList<>();
    }

    public Order(Long id, String ownerName, List<Product> products, boolean isConfirmed) {
        super(id);
        this.ownerName = ownerName;
        this.products = products;
        this.isConfirmed = isConfirmed;
        this.totalPrice = countTotalPrice();
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

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        totalPrice = countTotalPrice();
    }

    private BigDecimal countTotalPrice() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
                ", total price=" + totalPrice +
                ", isConfirmed=" + isConfirmed +
                '}';
    }
}
