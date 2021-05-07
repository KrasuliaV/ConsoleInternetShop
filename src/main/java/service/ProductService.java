package service;

import model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProductList();

    void createProduct(String name, String price);

    Product getProductById(String next);

    Product getProductByName(String next);

    void setNewPrice(Product product, String newPrice);

    void setNewName(Product product, String newName);
}
