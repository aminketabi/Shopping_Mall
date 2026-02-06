package Shopping;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ProductInShoppingCart> products;
    private double price;

    public ShoppingCart() {
        this.products = new ArrayList<>();
        this.price = 0.0;
    }

    public void add(Product product) {
        if (product.getNumber() == 0) {
            throw new IllegalArgumentException("Mojodi nadrim.");
        }

        for (ProductInShoppingCart product2 : products)
            if (product2.getProduct().getName().equals(product.getName())) {
                product2.add();
                calculatePrice();
                return;
            }

        ProductInShoppingCart newProduct = new ProductInShoppingCart(product);
        products.add(newProduct);
        calculatePrice();
    }

    public void remove(String name) {
        for (ProductInShoppingCart product : products)
            if (product.getProduct().getName().equals(name)) {
                product.remove();
                calculatePrice();
                if (product.getNumberInShoppingCart() == 0)
                        products.remove(product);
                return;
            }
    }

    public void clear() {
        products.clear();
        price = 0.0;
    }

    private void calculatePrice() {
        price = 0.0;
        for (ProductInShoppingCart product : products)
                price += ((double) product.getNumberInShoppingCart()) * product.getProduct().getPrice();
    }

    public List<ProductInShoppingCart> getProducts() {
        return products;
    }
    public double getPrice() {
        return price;
    }
}
