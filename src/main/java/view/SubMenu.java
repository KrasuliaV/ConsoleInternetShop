package view;

import model.Product;
import model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface SubMenu extends Menu {

    void productsSubMenuShow(User user);

    void ordersMenuShow(User user);

    void usersMenuShow(User user);

    default void showProductPage(List<Product> productList) {
        productList.stream()
                .map(product -> product.getName() + ", id = " + product.getId() + ", price = " + product.getPrice())
                .forEach(System.out::println);
    }

    default <T> List<T> getPage(List<T> someList, int pageNumber, int numberElementsOnOnePage) {
        return Optional.ofNullable(createPageMap(someList, numberElementsOnOnePage).get(pageNumber))
                .orElse(createPageMap(someList, numberElementsOnOnePage).get(1));
    }

    default int getPageNumbers(int numberElementOnOnePage, int productListSize) {
        return numberElementOnOnePage >= productListSize ? 1 : (productListSize / numberElementOnOnePage + 1);
    }

    private <T> Map<Integer, List<T>> createPageMap(List<T> someList, int elementInOnePage) {
        int pageNumbers = getPageNumbers(elementInOnePage, someList.size());
        return IntStream.range(1, pageNumbers + 1)
                .boxed()
                .collect(Collectors.toMap(num -> num,
                        num2 -> someList.subList(((num2 - 1) * elementInOnePage), numberChecker(someList, num2 * elementInOnePage))));
    }

    private <T> int numberChecker(List<T> someList, int number) {
        return Math.min(number, someList.size());
    }
}
