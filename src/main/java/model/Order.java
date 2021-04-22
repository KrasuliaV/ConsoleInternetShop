package model;

import java.util.ArrayList;

public class Order extends AbstractEntity {

    private String ownerName;
    private boolean isConfirmed;
    private ArrayList<Product> product;


    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }


    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public ArrayList<Product> getProduct() {
        return product;
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
