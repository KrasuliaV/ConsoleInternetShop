package dao.impl;

import dao.ProductDao;
import db.HomeDB;
import model.Product;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static long productId = 5;

    @Override
    public Product create(Product product) {
        product.setId(++productId);
        HomeDB.getProductDB().add(product);
        return product;
    }

    @Override
    public Product getById(Long id) {
        return HomeDB.getProductDB().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Product delete(Product product) {
        HomeDB.getProductDB().remove(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        return HomeDB.getProductDB().set(HomeDB.getProductDB().indexOf(getById(product.getId())), product);
    }

    @Override
    public List<Product> getAll() {
        return HomeDB.getProductDB();
    }

    @Override
    public Product getByProductName(String productName) {
        return HomeDB.getProductDB().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }
}
