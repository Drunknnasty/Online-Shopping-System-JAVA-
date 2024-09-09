import java.io.*;
import java.util.*;

public class Customer {
    private String name;
    private String email;
    private List<Order> orderHistory;

    private static final String CUSTOMER_FILE = "C://Users//sh//Desktop//CUSTOMERS.txt";
    private static final String ORDERS_DIRECTORY = "C://Users//sh//Desktop//ORDERS.txt";

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.orderHistory = new ArrayList<>();
        loadOrderHistory();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
        saveOrderHistory();
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.write(name + "," + email);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving customer data: " + e.getMessage());
        }
    }

    public static Customer loadFromFile(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(email)) {
                    return new Customer(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
        return null;
    }

    private void loadOrderHistory() {
        File orderFile = new File(ORDERS_DIRECTORY + email + "_orders.txt");
        if (!orderFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split(";");
                int orderId = Integer.parseInt(orderData[0]);
                List<Product> products = new ArrayList<>();
                for (int i = 1; i < orderData.length - 1; i++) {
                    String[] productData = orderData[i].split(",");
                    int productId = Integer.parseInt(productData[0]);
                    String productName = productData[1];
                    double productPrice = Double.parseDouble(productData[2]);
                    if (productData.length == 4) {
                        // It's an Electronics product
                        String brand = productData[3];
                        products.add(new Electronics(productId, productName, productPrice, brand));
                    } else {
                        // It's a Clothing product
                        String size = productData[3];
                        products.add(new Clothing(productId, productName, productPrice, size));
                    }
                }
                double totalAmount = Double.parseDouble(orderData[orderData.length - 1]);
                Order order = new Order(orderId, this, products, totalAmount);
                orderHistory.add(order);
            }
        } catch (IOException e) {
            System.out.println("Error loading order history: " + e.getMessage());
        }
    }

    private void saveOrderHistory() {
        File orderFile = new File(ORDERS_DIRECTORY + email + "_orders.txt");
        orderFile.getParentFile().mkdirs(); // Create directory if not exists

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFile))) {
            for (Order order : orderHistory) {
                writer.write(order.toSaveFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }
}