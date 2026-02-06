package Users;

import Shopping.ShoppingCart;

public class Customer extends User {
   private ShoppingCart cart;


    public Customer(String username, String password, String email) {
        super(username, password, email, "Customer");
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
