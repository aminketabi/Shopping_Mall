package Catalog;

import Shopping.Product;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private static Catalog catalog;
    private List<Product> products = new ArrayList<>();

    public Catalog() {

    }

    public void addProduct(Product product) {
        products.add(product);
        //saveToFile();
    }

    public List<Product> getProducts() {
        return products;
    }

    public String showCatalog() {
        StringBuilder catalog = new StringBuilder();
        for (Product product : products) {
            catalog.append(product.getName())
                    .append(" - Price: ")
                    .append(product.getPrice())
                    .append(" - Available: ")
                    .append(1)
                    .append("\n");
        }
        return catalog.toString();
    }

    public static Catalog getCatalog() {
        return catalog;
    }

}
