import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();


        //display menu
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 7) {

            System.out.println("Welcome to Westminster Shopping Manager!");
            System.out.println("Please select an option:");
            System.out.println("1. Add a product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print all products");
            System.out.println("4. Save products to file");
            System.out.println("5. Load products from file");
            System.out.println("6. Open SHopping GUI");
            System.out.println("7. Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Please select a product type:");
                    System.out.println("1. Electronics");
                    System.out.println("2. Clothing");
                    int productType = scanner.nextInt();
                    if (productType == 1) {
                        System.out.println("Please enter the product details:");
                        System.out.println("Product ID:");
                        String productId = scanner.next();
                        System.out.println("Product Name:");
                        String productName = scanner.next();
                        System.out.println("Available Items:");
                        int availableItems = scanner.nextInt();
                        System.out.println("Price:");
                        double price = scanner.nextDouble();
                        System.out.println("Brand:");
                        String brand = scanner.next();
                        System.out.println("Warranty Period:");
                        int warrantyPeriod = scanner.nextInt();

                        Electronic electronic = new Electronic(productId, productName, availableItems, price, brand, warrantyPeriod);
                        shoppingManager.addProduct(electronic);

                    } else if (productType == 2) {
                        System.out.println("Please enter the product details:");
                        System.out.println("Product ID:");
                        String productId = scanner.next();
                        System.out.println("Product Name:");
                        String productName = scanner.next();
                        System.out.println("Available Items:");
                        int availableItems = scanner.nextInt();
                        System.out.println("Price:");
                        double price = scanner.nextDouble();
                        System.out.println("Size:");
                        String size = scanner.next();
                        System.out.println("Color:");
                        String color = scanner.next();

                        Clothing clothing = new Clothing(productId, productName, availableItems, price, size, color);
                        shoppingManager.addProduct(clothing);
                    } else {
                        System.out.println("Invalid product type. Please try again.");
                    }

                    break;
                case 2:
                    System.out.println("Please enter the product ID:");
                    String productId = scanner.next();

                    shoppingManager.deleteProduct(productId);
                    break;
                case 3:
                    shoppingManager.printProducts();
                    break;
                case 4:
                    shoppingManager.saveProducts();
                    break;
                case 5:
                    shoppingManager.loadProducts();
                    break;
                case 6:
                    shoppingManager.openShoppingGUI();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        }
    }
}
