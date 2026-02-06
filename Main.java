import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class Administrator extends User {
    private String adminId;

    public Administrator(String username, String password, String email, String adminId) {
        super(username, password, email);
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", adminId='" + adminId + '\'' +
                '}';
    }
}

class AuthenticationService {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Administrator> administrators = new HashMap<>();

    public AuthenticationService() {
        users.put("user1", new User("user1", "pass1", "user1@example.com"));
        users.put("user2", new User("user2", "pass2", "user2@example.com"));

        administrators.put("admin1", new Administrator("admin1", "adminpass", "admin1@example.com", "ADM001"));
        administrators.put("admin2", new Administrator("admin2", "adminpass2", "admin2@example.com", "ADM002"));
    }

    public Object authenticate(String username, String password) {
        if (administrators.containsKey(username)) {
            Administrator admin = administrators.get(username);
            if (admin.getPassword().equals(password)) {
                return admin;
            }
        }

        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }
}

class Product {
    private String title;
    private int price;
    private ImageIcon image;

    public Product(String title, int price, ImageIcon image) {
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public ImageIcon getImage() {
        return image;
    }

    @Override
    public String toString() {
        return title + "," + price + "," + (image != null ? image.getDescription() : "");
    }
}

class ShoppingCart {
    private List<Product> cart = new ArrayList<>();

    public void addProduct(Product product) {
        cart.add(product);
        saveCartToFile();
    }

    public void removeProduct(Product product) {
        cart.remove(product);
        saveCartToFile();
    }

    public List<Product> getProducts() {
        return cart;
    }

    private void saveCartToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cart.txt"))) {
            for (Product product : cart) {
                writer.write(product.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCartFromFile() {
        cart.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("cart.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String title = parts[0];
                    int price = Integer.parseInt(parts[1]);
                    ImageIcon imageIcon = new ImageIcon(parts[2]);
                    cart.add(new Product(title, price, imageIcon));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Catalog {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        saveToFile();
    }

    public List<Product> getProducts() {
        return products;
    }

    public String showCatalog() {
        StringBuilder catalog = new StringBuilder();
        for (Product product : products) {
            catalog.append(product.getTitle())
                    .append(" - Price: ")
                    .append(product.getPrice())
                    .append("\n");
        }
        return catalog.toString();
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("catalog.txt"))) {
            for (Product product : products) {
                writer.write(product.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        products.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("catalog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String title = parts[0];
                    int price = Integer.parseInt(parts[1]);
                    ImageIcon imageIcon = new ImageIcon(parts[2]);
                    if (isValidImage(imageIcon)) {
                        products.add(new Product(title, price, imageIcon));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidImage(ImageIcon icon) {
        return icon.getIconWidth() > 0 && icon.getIconHeight() > 0;
    }
}

public class Main {
    private ShoppingCart shoppingCart;
    private AuthenticationService authService;
    private Catalog catalog;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().Login());
    }

    private void Login() {
        authService = new AuthenticationService();
        catalog = new Catalog();
        catalog.loadFromFile();
        shoppingCart = new ShoppingCart();
        shoppingCart.loadCartFromFile();

        JFrame frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("username");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(userLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        frame.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("password");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        JButton adminButton = new JButton("admin");
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(adminButton, gbc);

        JButton customerButton = new JButton("customer");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(customerButton, gbc);

        adminButton.setBackground(Color.BLUE);
        adminButton.setForeground(Color.WHITE);
        customerButton.setBackground(Color.GREEN);
        customerButton.setForeground(Color.BLACK);

        JTextArea messageArea = new JTextArea(2, 20);
        messageArea.setEditable(false);
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(messageArea, gbc);

        adminButton.addActionListener(e -> performLogin(usernameField, passwordField, messageArea, "admin"));
        customerButton.addActionListener(e -> performLogin(usernameField, passwordField, messageArea, "customer"));

        frame.setVisible(true);
    }

    private void performLogin(JTextField usernameField, JPasswordField passwordField, JTextArea messageArea, String userType) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Object user = authService.authenticate(username, password);
        if (user != null) {
            messageArea.setText("success: " + user.toString());
            clearLoginFields(usernameField, passwordField);

            if (userType.equals("admin")) {
                Adminfront((Administrator) user);
            } else {
                Productfront(user);
            }
        } else {
            messageArea.setText("incorrect");
        }
    }

    private void clearLoginFields(JTextField usernameField, JPasswordField passwordField) {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void Adminfront(Administrator admin) {
        JFrame adminFrame = new JFrame("Admin Panel");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(400, 400);
        adminFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Welcome Admin: " + admin.toString());
        adminFrame.add(label);

        JButton addProductButton = new JButton("Add Product");
        JButton showCatalogButton = new JButton("Show Catalog");
        JButton backButton = new JButton("Back");

        addProductButton.addActionListener(e -> addProduct());
        showCatalogButton.addActionListener(e -> showCatalog());
        backButton.addActionListener(e -> {
            adminFrame.dispose();
            catalog.saveToFile();
            Login();
        });

        adminFrame.add(addProductButton);
        adminFrame.add(showCatalogButton);
        adminFrame.add(backButton);

        adminFrame.setVisible(true);
    }

    private void addProduct() {
        JTextField titleField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        JTextField quantityField = new JTextField(15);
        JTextField imageUrlField = new JTextField(15);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Title:"));
        myPanel.add(titleField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Price:"));
        myPanel.add(priceField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Quantity:"));
        myPanel.add(quantityField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("Image URL:"));
        myPanel.add(imageUrlField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter Product Details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            int price = Integer.parseInt(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            String imageUrl = imageUrlField.getText();

            ImageIcon productImage = new ImageIcon(imageUrl);

            if (isValidImage(productImage)) {
                for (int i = 0; i < quantity; i++) {
                    catalog.addProduct(new Product(title, price, productImage));
                }

                JOptionPane.showMessageDialog(null, quantity + " of " + title + " added to catalog.");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid image URL.");
            }
        }
    }

    private boolean isValidImage(ImageIcon icon) {
        return icon.getIconWidth() > 0 && icon.getIconHeight() > 0;
    }

    private void showCatalog() {
        String catalogContents = catalog.showCatalog();
        JOptionPane.showMessageDialog(null, catalogContents.isEmpty() ? "No products available." : catalogContents);
    }

    private void Productfront(Object user) {
        JFrame frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new FlowLayout());

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        catalog.loadFromFile();
        for (Product product : catalog.getProducts()) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BorderLayout());
            productPanel.setBackground(Color.WHITE);

            JLabel titleLabel = new JLabel(product.getTitle());
            JLabel priceLabel = new JLabel("Price: " + product.getPrice());
            JLabel imageLabel = new JLabel(product.getImage());
            imageLabel.setPreferredSize(new Dimension(100, 100)); // Set the size for image label
            JButton addButton = new JButton("Add to Basket");
            JButton removeButton = new JButton("Remove from Basket");

            addButton.setBackground(Color.GREEN);
            addButton.setForeground(Color.BLACK);
            removeButton.setBackground(Color.RED);
            removeButton.setForeground(Color.WHITE);

            addButton.addActionListener(e -> {
                shoppingCart.addProduct(product);
                JOptionPane.showMessageDialog(frame, product.getTitle() + " added to basket.");
            });

            removeButton.addActionListener(e -> {
                shoppingCart.removeProduct(product);
                JOptionPane.showMessageDialog(frame, product.getTitle() + " removed from basket.");
            });

            productPanel.add(imageLabel, BorderLayout.CENTER);
            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.add(titleLabel);
            textPanel.add(priceLabel);
            productPanel.add(textPanel, BorderLayout.NORTH);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(addButton);
            buttonPanel.add(removeButton);
            productPanel.add(buttonPanel, BorderLayout.SOUTH);
            frame.add(productPanel);
        }

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.setBackground(Color.YELLOW);
        viewCartButton.setForeground(Color.BLACK);

        viewCartButton.addActionListener(e -> {
            StringBuilder cartContents = new StringBuilder("Items in Cart:\n");
            for (Product p : shoppingCart.getProducts()) {
                cartContents.append(p.getTitle()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, cartContents.toString());
        });

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.BLACK);

        backButton.addActionListener(e -> {
            frame.dispose();
            catalog.saveToFile();
            Login();
        });

        frame.add(viewCartButton);
        frame.add(backButton);
        frame.setVisible(true);
    }
}