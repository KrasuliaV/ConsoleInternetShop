package service.impl;

import dao.impl.ProductDaoImpl;
import model.Product;
import service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
    public void createProduct(String name, String price) {
        if (!price.matches("[\\d.\\d]+") && name != null) {
            return;
        }
        Product productByName = productDao.getByProductName(name);

        if (productByName != null) {
            productByName.setPrice(Double.parseDouble(price));
        } else {
            productDao.create(new Product(name, Double.parseDouble(price)));
        }
    }

    @Override
    public Product getProductById(String next) {
        return next.matches("[\\d]+") ? productDao.getById(Long.parseLong(next)) : null;
    }

    @Override
    public Product getProductByName(String productName) {
        return Optional.ofNullable(productName).isPresent() ? productDao.getByProductName(productName) : null;
    }

    @Override
    public void setNewPrice(Product product, String newPrice) {
        setSome(product::setPrice, product, Double.parseDouble(newPrice));
    }

    @Override
    public void setNewName(Product product, String newName) {
        setSome(product::setName, product, newName);
    }

    private <T> void setSome(Consumer<T> consumer, Product product, T someProductParameter) {
        consumer.accept(someProductParameter);
        productDao.update(product);
    }

}
