import java.io.*;
import java.util.*;

public class Order {
    private static int orderCounter = 0;

    private int id;
    private Customer customer;
    private List<Product> products;
    private double totalAmount;

    public Order(Customer customer, ShoppingCart cart) {
        this.id = ++orderCounter;
        this.customer = customer;
        this.products = new ArrayList<>(cart.getProducts());
        this.totalAmount = cart.getTotalPrice();
    }

    // Constructor for loading orders
    public Order(int id, Customer customer, List<Product> products, double totalAmount) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");
        sb.append("Products:\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append("Total Amount: $").append(totalAmount).append("\n");
        return sb.toString();
    }

    // Save format for orders
    public String toSaveFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(";");
        for (Product product : products) {
            sb.append(product.getId()).append(",").append(product.getName()).append(",")
              .append(product.getPrice());
            if (product instanceof Electronics) {
                sb.append(",").append(((Electronics) product).toString().split(", ")[3]);
            } else if (product instanceof Clothing) {
                sb.append(",").append(((Clothing) product).toString().split(", ")[3]);
            }
            sb.append(";");
        }
        sb.append(totalAmount);
        return sb.toString();
    }

    public void saveOrderDetails() {
        File orderFile = new File("orders/" + customer.getEmail() + "_orders.txt");
        orderFile.getParentFile().mkdirs(); // Create directory if not exists

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFile, true))) {
            writer.write(toSaveFormat());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order details: " + e.getMessage());
        }
    }
}
