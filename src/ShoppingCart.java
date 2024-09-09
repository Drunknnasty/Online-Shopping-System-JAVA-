import java.util.*;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\nProducts in your cart:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void clearCart() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }
}
