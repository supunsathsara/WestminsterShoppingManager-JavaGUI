import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductGUI extends JFrame {
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JButton addToCartButton;
    private JButton viewShoppingCartButton;
    private JTextArea productDetailsArea;

    private List<Product> productList;
    private ShoppingCart shoppingCart;

    public ProductGUI(List<Product> productList, ShoppingCart shoppingCart) {
        this.productList = productList;
        this.shoppingCart = shoppingCart;

        // Initialize components
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTable = new JTable();
        addToCartButton = new JButton("Add to Cart");
        viewShoppingCartButton = new JButton("Shopping Cart");
        productDetailsArea = new JTextArea();

        //set the size of the text area
        productDetailsArea.setPreferredSize(new Dimension(200, 300));

        // Set layout manager
        setLayout(new BorderLayout());

        // Create a panel for product selection and buttons
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Product Type:"));
        topPanel.add(productTypeComboBox);

        topPanel.add(viewShoppingCartButton);

        //create a bottm panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(new JScrollPane(productDetailsArea));
        bottomPanel.add(addToCartButton);


        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Set up action listeners
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable((String) productTypeComboBox.getSelectedItem());
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to add selected product to the shopping cart
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the product from the selected row
                    String productId = (String) productTable.getValueAt(selectedRow, 0);
                    Product selectedProduct = getProductById(productId);

                    // Add the selected product to the shopping cart
                    shoppingCart.addProduct(selectedProduct);
                } else {
                    JOptionPane.showMessageDialog(ProductGUI.this, "Please select a product to add to the cart.",
                            "No Product Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        viewShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of the shopping cart form
                ShoppingCartForm shoppingCartForm = new ShoppingCartForm();

                // Pass the shopping cart contents and final price to the shopping cart form
                shoppingCartForm.displayShoppingCartContents(shoppingCart, shoppingCart.calculateFinalCost());

                // Optionally, display the shopping cart form
                shoppingCartForm.setVisible(true);
            }
        });

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the product from the selected row
                String productId = (String) productTable.getValueAt(selectedRow, 0);
                Product selectedProduct = getProductById(productId);

                // Update the product details area with the selected product
                updateProductDetailsArea(selectedProduct);
            }
        });

        // Initialize the product table with all products
        updateProductTable("All");

        // Set frame properties
        setTitle("Product GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to update the product table based on the selected product type
    private void updateProductTable(String productType) {
        List<Product> filteredProducts;

        if (productType.equals("All")) {
            // Display all products
            filteredProducts = productList;
        } else {
            // Filter products based on the selected product type
            filteredProducts = productList.stream()
                    .filter(product -> {
                        if (productType.equals("Electronics") && product instanceof Electronic) {
                            return true;
                        } else if (productType.equals("Clothes") && product instanceof Clothing) {
                            return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }

        // Update the table with the filtered products
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Available Items");
        model.addColumn("Price");

        for (Product product : filteredProducts) {
            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getNoOfAvailable(),
                    product.getPrice()
            });
        }

        productTable.setModel(model);
    }

    // Method to update the product details area based on the selected product
    private void updateProductDetailsArea(Product selectedProduct) {
        if (selectedProduct != null) {
            String details = "Product ID: " + selectedProduct.getId() + "\n" +
                    "Product Name: " + selectedProduct.getName() + "\n" +
                    "Available Items: " + selectedProduct.getNoOfAvailable() + "\n" +
                    "Price: " + selectedProduct.getPrice();

            if (selectedProduct instanceof Electronic) {
                Electronic electronicsProduct = (Electronic) selectedProduct;
                details += "\nBrand: " + electronicsProduct.getBrand() + "\n" +
                        "Warranty Period: " + electronicsProduct.getWarranty() + " months";
            } else if (selectedProduct instanceof Clothing) {
                Clothing clothingProduct = (Clothing) selectedProduct;
                details += "\nSize: " + clothingProduct.getSize() + "\n" +
                        "Color: " + clothingProduct.getColor();
            }

            productDetailsArea.setText(details);
        } else {
            productDetailsArea.setText(""); // Clear details area if no product is selected
        }
    }



    // Helper method to get a product by its ID
    private Product getProductById(String productId) {
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Assuming you have a productList and shoppingCart instances
                List<Product> productList = new ArrayList<>();
                ShoppingCart shoppingCart = new ShoppingCart();
                new ProductGUI(productList, shoppingCart);
            }
        });
    }
}
