import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShoppingCartForm extends JFrame {
    private JTable cartTable;
    private JTextArea discountArea;

    public ShoppingCartForm() {
        // Initialize components
        cartTable = new JTable();
        cartTable.setEnabled(false); // Make the table read-only

        discountArea = new JTextArea();
        discountArea.setEditable(false);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the frame
        add(new JScrollPane(cartTable), BorderLayout.CENTER);
        add(new JScrollPane(discountArea), BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose the form when closed
        setSize(400, 400);
        setLocationRelativeTo(null);
    }

    public void displayShoppingCartContents(ShoppingCart shoppingCart, double finalPrice) {
        List<Product> products = shoppingCart.getProducts();

        // Create a table model for the shopping cart
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        // Populate the table model with shopping cart contents
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getName(),
                    shoppingCart.getProductQuantity(product),
                    product.getPrice()
            });
        }

        // Set the table model for the cartTable
        cartTable.setModel(model);

        // Display discounts and final price in the discountArea
        StringBuilder discounts = new StringBuilder();

        if (finalPrice < calculateTotalCost(products)) {
            if (shoppingCart.hasFirstPurchaseDiscount()) {
                discounts.append("First Purchase Discount (10%)\n");
            }

            if (shoppingCart.hasCategoryDiscount()) {
                discounts.append("Three Items in Same Category Discount (20%)\n");
            }

            discounts.append("\nFinal Total: ").append(finalPrice);

            // Set the discounts in the discountArea
            discountArea.setText(discounts.toString());
        } else {
            discountArea.setText(""); // Clear the discountArea if no discounts are applied
        }
    }

    private double calculateTotalCost(List<Product> products) {
        double totalCost = 0;

        for (Product product : products) {
            totalCost += product.getPrice();
        }

        return totalCost;
    }
}
