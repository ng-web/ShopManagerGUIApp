import javax.swing.*;
import java.awt.*;

public class ShopWindow extends JFrame {

    // create components, Event Handling, Data Parsing, File I/O

    private JTextField nameField, priceField, qtyField;
    private JTextArea displayArea;
    private JButton addBtn, clearBtn, saveBtn;

    public ShopWindow() {
        // setup window settings
        setTitle("Shop Manager");
        setSize(450, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // input panel
        JPanel inputPanel = new JPanel(new GridLayout(5,2,5,5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        inputPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Unit Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        inputPanel.add(qtyField);

        // initialize buttons
        addBtn = new JButton("Add To Cart");
        inputPanel.add(addBtn);

        clearBtn = new JButton("Reset Form");
        inputPanel.add(clearBtn);

        saveBtn = new JButton("Generate Receipt");
        inputPanel.add(saveBtn);

        // Display area for items
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // event listeners
        attachEventListeners();

        setLocationRelativeTo(null); // ensures app loads in center of screen
        setVisible(true);
    } // end constructor

    private void attachEventListeners() {
        // Add button logic
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());

                double total = price * qty;

                // add result to the view
                displayArea.append(name + "[$" + price + " x " + qty + "] = $" + total + "\n");

            } catch (NumberFormatException ex) {
                // validation rule
                JOptionPane.showMessageDialog(this,
                        "Error: Price and Quantity must be numeric!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear button logic
        clearBtn.addActionListener(e -> {
            nameField.setText("");
            priceField.setText("");
            qtyField.setText("");
            displayArea.setText("");
            nameField.requestFocus();
        });

        // Save button logic
        saveBtn.addActionListener(e -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter("receipt.txt", true))) {
                writer.println("--- Customer Receipt ---");
                writer.println(displayArea.getText());
                writer.println("------------------------------");

                JOptionPane.showMessageDialog(this, "Receipt saved to receipt.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "File error: Could not save.");
            }
        });
    }
}

void main(String[] args) {
    // set look and feel to system settings
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
        //throw new RuntimeException(e);
        e.printStackTrace();
    }

    // initializing shop
    new ShopWindow();
}
