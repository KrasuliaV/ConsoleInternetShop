package model;

import java.util.ArrayList;


public class Order extends AbstractEntity {

    private String ownerName;
    private boolean status;
    private ArrayList<String> product;

    public boolean isStatus() {
        return status;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setProduct(ArrayList<String> product) {
        this.product = product;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
