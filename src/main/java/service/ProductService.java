package service;

import model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProductList();

    Product getProductByName(String next);

    void createProduct(String name, String price);

    Product getProductById(String next);

    void editProduct(Product product);

    void setNewPrice(Product product, String newPrice);

    void setNewName(Product product, String newName);
}
