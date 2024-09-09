import java.util.*;

public abstract class Product {
    private int id;
    private String name;
    private double price;

    private static List<Product> availableProducts = new ArrayList<>();

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        availableProducts.add(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public static Product getProductById(int id) {
        for (Product product : availableProducts) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Price: $" + price;
    }
}