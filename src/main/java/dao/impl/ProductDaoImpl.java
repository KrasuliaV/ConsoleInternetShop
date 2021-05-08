package dao.impl;

import dao.ProductDao;
import db.HomeDB;
import model.Product;

import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static Long productId = 5L;

    @Override
    public Product create(Product product) {
        product.setId(++productId);
        HomeDB.getProductDB().add(product);
        return product;
    }

    @Override
    public Product getById(Long id) {
        return HomeDB.getProductDB()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Product product) {
        HomeDB.getProductDB().remove(product);
    }

    @Override
    public Product update(Product product) {
        int insertionIndex = HomeDB.getProductDB().indexOf(getById(product.getId()));

        return HomeDB.getProductDB().set(insertionIndex, product);
    }

    @Override
    public List<Product> getAll() {
        return HomeDB.getProductDB();
    }

    @Override
    public Product getByProductName(String productName) {
        return HomeDB.getProductDB()
                .stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }
}
