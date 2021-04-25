package service.impl;

import dao.impl.ProductDaoImpl;
import model.Product;
import service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductDaoImpl productDao;

    public ProductServiceImpl() {
        this.productDao = new ProductDaoImpl();
    }

    @Override
    public List<Product> getAllProductList() {
        return productDao.getAll();
    }

    @Override
    public Product getProductByName(String productName) {
        if (Optional.ofNullable(productName).isPresent()) {
            return productDao.getByProductName(productName);
        } else {
            return null;
        }
    }

    @Override
    public void createProduct(String name, String price) {
        if (price.matches("[\\d.\\d]+") && name != null) {
            productDao.create(new Product(name, Double.parseDouble(price)));
        }
    }

    @Override
    public Product getProductById(String next) {
        if (next.matches("[\\d]+")) {
            return productDao.getById(Long.parseLong(next));
        } else {
            return null;
        }
    }

    @Override
    public void editProduct(Product product) {
        productDao.update(product);
    }

    @Override
    public void setNewPrice(Product product, String newPrice) {
        product.setPrice(Double.parseDouble(newPrice));
    }

    @Override
    public void setNewName(Product product, String newName) {
        product.setName(newName);
    }
}
