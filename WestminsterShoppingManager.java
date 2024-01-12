import java.io.*;
import java.util.ArrayList;
import java.util.List;

class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productList;

    // Constructor
    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }

    // Methods from ShoppingManager interface
    @Override
    public void addProduct(Product product) {
        productList.add(product);
        System.out.println("Product added successfully!");
    }

    @Override
    public void deleteProduct(String productId) {
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                productList.remove(product);
                System.out.println("Product deleted successfully!");
                return;
            }
        }
        System.out.println("Product not found!");
    }

    @Override
    public void printProducts() {
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    @Override
    public void saveProducts() {
        // save to a file
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (Product product : productList) {
                if (product instanceof Electronic) {
                    // If the product is an instance of Electronics, save specific details
                    Electronic electronicsProduct = (Electronic) product;
                    writer.println("Electronics," +
                            product.getId() + "," +
                            product.getName() + "," +
                            product.getNoOfAvailable() + "," +
                            product.getPrice() + "," +
                            electronicsProduct.getBrand() + "," +
                            electronicsProduct.getWarranty());
                } else if (product instanceof Clothing) {
                    // If the product is an instance of Clothing, save specific details
                    Clothing clothingProduct = (Clothing) product;
                    writer.println("Clothing," +
                            product.getId() + "," +
                            product.getName() + "," +
                            product.getNoOfAvailable() + "," +
                            product.getPrice() + "," +
                            clothingProduct.getSize() + "," +
                            clothingProduct.getColor());
                } else {
                    //continue;
                }
            }
            System.out.println("Products saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving products to file: " + e.getMessage());
        }
    }


    @Override
    public void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    String type = parts[0].trim();
                    if (type.equals("Electronics")) {
                        Electronic electronicsProduct = new Electronic(
                                parts[1].trim(),  // productId
                                parts[2].trim(),  // productName
                                Integer.parseInt(parts[3].trim()),  // availableItems
                                Double.parseDouble(parts[4].trim()),  // price
                                parts[5].trim(),  // brand
                                Integer.parseInt(parts[6].trim())  // warrantyPeriod
                        );
                        productList.add(electronicsProduct);
                    } else if (type.equals("Clothing")) {
                        Clothing clothingProduct = new Clothing(
                                parts[1].trim(),  // productId
                                parts[2].trim(),  // productName
                                Integer.parseInt(parts[3].trim()),  // availableItems
                                Double.parseDouble(parts[4].trim()),  // price
                                parts[5].trim(),  // size
                                parts[6].trim()   // color
                        );
                        productList.add(clothingProduct);
                    } else {
                        System.out.println("Invalid product type. Please try again.");
                    }
                }
            }
            System.out.println("Products loaded from file successfully.");
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }
    }


    public void openShoppingGUI() {
        ShoppingCart shoppingCart = new ShoppingCart();
        ProductGUI productGUI = new ProductGUI(productList, shoppingCart);

        productGUI.setVisible(true);

    }
}