package Shopping;

public class ProductInShoppingCart {
    private  Product product;
    private int numberInShoppingCart;

    public ProductInShoppingCart(Product product) {
        this.product = product;
        this.numberInShoppingCart = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberInShoppingCart() {
        return numberInShoppingCart;
    }
    public void setNumberInShoppingCart(int quantity) {
        this.numberInShoppingCart = quantity;
    }

    public void add() {
        numberInShoppingCart++;
    }

    public void remove() {
        numberInShoppingCart--;
    }

}
