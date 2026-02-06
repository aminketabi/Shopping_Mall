package Shopping;

import javax.swing.*;

public class Product {
    private String name;
    private double price;
    private int number;
    private ImageIcon image;

    public Product(String name, double price, int number, ImageIcon image) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.image = image;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public boolean decreaseProduct(int decrease) {
        if (decrease <= number) {
            number -= decrease;
            return true;
        }
        return false;
    }

    public void increaseProduct(int increaseM) {
        number += increaseM;
    }
}
