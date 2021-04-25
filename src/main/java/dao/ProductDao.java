package dao;

import model.Product;

public interface ProductDao extends Dao<Product> {

    Product getByProductName(String productName);

}
