import java.io.*;
import java.util.*;

public class OnlineShoppingSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer customer = null;
        boolean exit = false;
        new Electronics(1, "Smartphone", 699.99, "APPLE");
        new Electronics(2, "Laptop", 1299.99, "ASUS");
        new Clothing(3, "T-Shirt", 19.99, "M");
        new Clothing(4, "Jeans", 49.99, "L");

        while (!exit) {
            System.out.println("\nWelcome to the Online Shopping System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    customer = registerCustomer(scanner);
                    break;
                case 2:
                    customer = loginCustomer(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            if (customer != null) {
                handleCustomerOptions(scanner, customer);
            }
        }

        System.out.println("Thank you for using the Online Shopping System. Goodbye!");
        scanner.close();
    }

    private static void handleCustomerOptions(Scanner scanner, Customer customer) {
        ShoppingCart cart = new ShoppingCart();
        boolean logout = false;

        while (!logout) {
            System.out.println("\nHello, " + customer.getName() + "!");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. View Order History");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addProductToCart(scanner, cart);
                    break;
                case 3:
                    cart.viewCart();
                    break;
                case 4:
                    placeOrder(cart, customer);
                    break;
                case 5:
                    viewOrderHistory(customer);
                    break;
                case 6:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void displayProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : Product.getAvailableProducts()) {
            System.out.println(product);
        }
    }

    private static void addProductToCart(Scanner scanner, ShoppingCart cart) {
        System.out.print("\nEnter the product ID to add to cart: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Product product = Product.getProductById(productId);
        if (product != null) {
            cart.addProduct(product);
            System.out.println("Added " + product.getName() + " to cart.");
        } else {
            System.out.println("Product not found!");
        }
    }

    private static void placeOrder(ShoppingCart cart, Customer customer) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add products before placing an order.");
            return;
        }

        Order order = new Order(customer, cart);
        customer.addOrder(order);
        cart.clearCart();
        System.out.println("Order placed successfully!");
        order.saveOrderDetails();
    }

    private static void viewOrderHistory(Customer customer) {
    
        List<Order> orders = customer.getOrderHistory();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("\nOrder History:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private static Customer registerCustomer(Scanner scanner) {
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        Customer customer = new Customer(name, email);
        customer.saveToFile();
        System.out.println("Registration successful!");
        return customer;
    }

    private static Customer loginCustomer(Scanner scanner) {
        System.out.print("\nEnter your email: ");
        String email = scanner.nextLine();
        Customer customer = Customer.loadFromFile(email);
        if (customer != null) {
            System.out.println("Login successful! Welcome, " + customer.getName() + "!");
            return customer;
        } else {
            System.out.println("No account found with this email. Please register.");
            return null;
        }
    }
}
