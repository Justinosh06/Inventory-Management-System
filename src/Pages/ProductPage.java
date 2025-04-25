package Pages;

import Account.Product;
import Account.User;
import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Select;
import GUIComponent.*;
import Validation.Alert;
import Validation.OptionPanel;

import static GUIComponent.Component.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

// The ProductPage class displays and manages the list of products for the logged-in user.
// It allows searching, updating, deleting, and inserting new products.
public class ProductPage extends GUI implements ActionListener {
    // Column headers for the product table.
    String[] column = {"ID", "Name", "Category", "Brand", "Description", "Quantity", "Modal Price", "Retail Price"};

    private User user; // The currently logged-in user.

    private Table table; // A custom table to display product information.

    // Constructor for the ProductPage.
    // @param user The currently logged-in User object.
    public ProductPage(User user) {
        String title = "INVENTORY · PRODUCT"; // Set the title of the page.

        super(title); // Call the constructor of the superclass (GUI).

        this.user = user; // Initialize the user.
        product(); // Call the method to create and display the product management UI.
    }

    // Creates and arranges the UI components for the product management page.
    public void product(){
        final float textSize = 16f; // Font size for regular text.

        final Font Text = FontConstant.PlexMonoMedium.deriveFont(textSize); // Font for labels and buttons.
        final Font tableFont = FontConstant.PlexMonoLight.deriveFont(textSize); // Font for table content.
        final Font tableHeaderFont = FontConstant.PlexMonoBold.deriveFont(textSize); // Font for table headers.

        dropdown = new JComboBox[1]; // Array to hold dropdown menus (for category filtering).
        dropdown[0] = new JComboBox(); // Dropdown to select product category for filtering.

        textField = new JTextField[1]; // Array to hold text fields (for product name search).
        textField[0] = new JTextField(); // Text field to enter product name for search.

        Button = new JButton[4]; // Array to hold buttons (Search, Update, Delete, Insert).
        Button[0] = new JButton(); // Button to trigger product search.
        Button[1] = new JButton(); // Button to update a selected product.
        Button[2] = new JButton(); // Button to delete a selected product.
        Button[3] = new JButton(); // Button to insert a new product.

        panel = new JPanel[6]; // Array to hold multiple panels for layout.
        panel[0] = new JPanel(); // Main panel for the entire product page.
        panel[1] = new JPanel(); // Panel for the navigation bar.
        panel[2] = new JPanel(); // Panel to hold the main content area.
        panel[3] = new JPanel(); // Panel for search and filter controls.
        panel[4] = new JPanel(); // Panel to hold the product table.
        panel[5] = new JPanel(); // Panel for action buttons (Update, Delete, Insert).

        label = new JLabel[3]; // Array to hold labels for form elements.
        label[0] = new JLabel("NAME"); // Label for the product name search field.
        label[1] = new JLabel("CATEGORY"); // Label for the category dropdown.

        add(panel[0]); // Add the main panel to the frame.
        panel[0].setLayout(new BorderLayout()); // Set BorderLayout for the main panel.
        panel[0].setBackground(GUIConstant.black);
        panel[0].setPreferredSize(new Dimension(1080, 675)); // Set preferred size of the main panel.

        panel[0].add(panel[1], BorderLayout.NORTH); // Add the navigation bar panel to the top.
        panel[1].setBackground(GUIConstant.black);

        Navbar nv = new Navbar(panel[1], "product", this, user); // Create and add the navigation bar.

        panel[2].setBackground(null); // Make background transparent.
        panel[2].setLayout(new BorderLayout()); // Set BorderLayout for the main content panel.
        panel[0].add(panel[2], BorderLayout.CENTER); // Add the main content panel to the center.

        panel[3].setBackground(GUIConstant.black);
        panel[3].setLayout(new GridBagLayout()); // Set GridBagLayout for the search and filter panel.
        panel[3].setBorder(new EmptyBorder(0, 0, 10, 0)); // Add bottom padding.
        panel[2].add(panel[3], BorderLayout.NORTH); // Add the search and filter panel to the top of the content.

        // Constraints for the "NAME" label.
        setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, 0.45, 0, new Insets(5, (int) (0.1*1080), 0, 5),"horizontal");
        GridBagConstraints gbc = myGbc.getGbc();

        label[0].setFont(Text);
        label[0].setForeground(GUIConstant.white1);
        panel[3].add(label[0], gbc); // Add the "NAME" label.

        // Constraints for the product name search text field.
        myGbc = new setGridBagConstraints(0, 1, 0.45, 0, new Insets(5, (int) (0.1*1080), 0, 5),"horizontal");
        gbc = myGbc.getGbc();

        textField[0].setBackground(GUIConstant.black);
        textField[0].setForeground(GUIConstant.white1);
        textField[0].setFont(Text);
        textField[0].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(4, 5, 4, 5)));
        panel[3].add(textField[0], gbc); // Add the product name search text field.

        // Constraints for the "CATEGORY" label.
        myGbc = new setGridBagConstraints(1, 0, 0.35, 0, new Insets(5, 5, 0, 5),"horizontal");
        gbc = myGbc.getGbc();

        label[1].setFont(Text);
        label[1].setForeground(GUIConstant.white1);
        panel[3].add(label[1], gbc); // Add the "CATEGORY" label.

        // Constraints for the category dropdown.
        myGbc = new setGridBagConstraints(1, 1, 0.35, 0, new Insets(5, 5, 0, 5),"horizontal");
        gbc = myGbc.getGbc();

        dropdown[0].setFont(Text);
        dropdown[0].setBackground(GUIConstant.black);
        dropdown[0].setForeground(GUIConstant.white1);
        dropdown[0].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(0, 5, 0, 5)));

        dropdown[0].addItem("All"); // Add "All" option to show all categories.

        // Fetch categories from the database and populate the dropdown.
        Select sc = new Select();
        try{
            String query = "SELECT `Name` FROM `category` WHERE `userID` = '" + user.getUserID() + "';";
            sc.Select(query);

            ResultSet rs = sc.result;
            while(rs.next()){
                dropdown[0].addItem(rs.getString(1)); // Add each category name to the dropdown.
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }

        panel[3].add(dropdown[0], gbc); // Add the category dropdown.

        // Constraints for the "SEARCH" button.
        myGbc = new setGridBagConstraints(2, 1, 0.2, 0, new Insets(5, 5, 0, (int) (0.1*1080)),"horizontal");
        gbc = myGbc.getGbc();

        Button[0].setText("SEARCH");
        Button[0].setFont(Text);
        Button[0].setBorder(new CompoundBorder(new roundifyBorder(0), new EmptyBorder(5, 5, 5, 5)));
        Button[0].setBackground(GUIConstant.black);
        Button[0].setForeground(GUIConstant.white1);
        Button[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[0].addActionListener(this);
        panel[3].add(Button[0], gbc); // Add the "SEARCH" button.

        panel[4].setBackground(GUIConstant.black);
        panel[4].setLayout(new GridBagLayout()); // Set GridBagLayout for the product table panel.
        panel[4].setBorder(new EmptyBorder(0, 108, 0, 108)); // Add left and right padding.
        panel[2].add(panel[4], BorderLayout.CENTER); // Add the product table panel to the center.

        // Fetch product data from the database and populate the table.
        sc = new Select();
        try{
            String query = "SELECT `productID`, p.`Name`, c.`Name`, `Brand`, `Description`, `Quantity`, `Modal`, `retailPrice` FROM `product` p JOIN `category` c ON p.categoryID = c.categoryID WHERE p.`userID` = '" + user.getUserID() + "';";

            sc.Select(query);
            ResultSet rs = sc.result;

            table = new Table(column, panel[4]); // Create the custom table.

            // Apply styling to the table.
            table.setTableFont(tableFont);
            table.setTableHeaderFont(tableHeaderFont);
            table.setTableBackground(GUIConstant.black);
            table.setTableForeground(GUIConstant.white1);
            table.setTableHeaderBackground(GUIConstant.black);
            table.setTableHeaderForeground(GUIConstant.white1);

            // Populate the table with data from the result set.
            if(rs.next()) {
                String[] dataRow = new String[column.length];
                for (int i = 0; i < column.length; i++) {
                    dataRow[i] = rs.getString(i + 1);
                }
                table.addRow(dataRow); // Add the first row.

                while (rs.next()) {
                    dataRow = new String[column.length];
                    for (int i = 0; i < column.length; i++) {
                        dataRow[i] = rs.getString(i + 1);
                    }
                    table.addRow(dataRow); // Add subsequent rows.
                }
            }
        } catch(Exception error) {
            error.printStackTrace();
        } finally {
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }

        panel[5].setBackground(GUIConstant.black);
        panel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15)); // Set FlowLayout for action buttons.
        panel[2].add(panel[5], BorderLayout.SOUTH); // Add the action button panel to the bottom.

        // "UPDATE" button.
        Button[1].setText("UPDATE");
        Button[1].setFont(Text);
        Button[1].setBorder(new CompoundBorder(new roundifyBorder(0), new EmptyBorder(2, 50, 2, 50)));
        Button[1].setBackground(GUIConstant.black);
        Button[1].setForeground(GUIConstant.white1);
        Button[1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[1].addActionListener(this);
        panel[5].add(Button[1]); // Add the "UPDATE" button.

        // "DELETE" button.
        Button[2].setText("DELETE");
        Button[2].setFont(Text);
        Button[2].setBorder(new CompoundBorder(new roundifyBorder(0), new EmptyBorder(2, 50, 2, 50)));
        Button[2].setBackground(GUIConstant.black);
        Button[2].setForeground(GUIConstant.white1);
        Button[2].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[2].addActionListener(this);
        panel[5].add(Button[2]); // Add the "DELETE" button.

        // "INSERT" button.
        Button[3].setText("INSERT");
        Button[3].setFont(Text);
        Button[3].setBorder(new CompoundBorder(new roundifyBorder(0), new EmptyBorder(2, 50, 2, 50)));
        Button[3].setBackground(GUIConstant.black);
        Button[3].setForeground(GUIConstant.white1);
        Button[3].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[3].addActionListener(this);
        panel[5].add(Button[3]); // Add the "INSERT" button.
    }

    // Handles action events triggered by the buttons on the product page.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle clicks on the "SEARCH" button.
        if(e.getSource() == Button[0]){
            String product = textField[0].getText(); // Get the text entered in the search field.

            Select sc = new Select();
            try {
                String category = (String) dropdown[0].getSelectedItem(); // Get the selected category from the dropdown.

                // If "All" is selected, don't filter by category.
                if (category.equals("All")) {
                    category = "";
                }

                // Construct the SQL query to search for products based on name and category.
                String query = "SELECT `productID`, p.`Name`, c.`Name`, `Brand`, `Description`, `Quantity`, `Modal`, `retailPrice` FROM `product` p JOIN `category` c ON p.categoryID = c.categoryID WHERE p.`userID` = '" + user.getUserID() + "' AND c.Name LIKE '%" + category + "%' AND LOWER(p.Name) LIKE '%" + product.toLowerCase() + "%';";
                System.out.println(query);

                refreshTable(query); // Refresh the table with the search results.
            }catch(Exception error){
                error.printStackTrace();
            } finally {
                CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
            }
        }
        // Handle clicks on the "UPDATE" button.
        else if (e.getSource() == Button[1]){
            int row = table.getSelectedRow(); // Get the index of the selected row in the table.
            if(row != -1) {
                // Create a Product object with the data from the selected row.
                Product product = new Product();
                product.setProductID((String) table.getValueAt(row, 0));
                product.setName((String) table.getValueAt(row, 1));
                product.setCategory((String) table.getValueAt(row, 2));
                product.setBrand((String) table.getValueAt(row, 3));
                product.setDescription((String) table.getValueAt(row, 4));
                product.setQuantity(Integer.parseInt((String) table.getValueAt(row, 5)));
                product.setModalPrice(Double.parseDouble((String) table.getValueAt(row, 6)));
                product.setRetailPrice(Double.parseDouble((String) table.getValueAt(row, 7)));

                // Open the ProductPopUp in "UPDATE PRODUCT" mode, passing the selected product data.
                new ProductPopUp("UPDATE PRODUCT", user, this, product);
            } else {
                // Show an alert if no row is selected.
                new Alert("ALERT  NO SELECTED ROW", "No selected row!\nPlease select one by clicking it");
            }
        }
        // Handle clicks on the "DELETE" button.
        else if (e.getSource() == Button[2]){
            int row = table.getSelectedRow(); // Get the index of the selected row.
            if(row != -1){
                // Open an OptionPanel to confirm the deletion of the selected product.
                new OptionPanel("DELETE PRODUCT", "ARE YOU SURE THAT YOU WANT TO DELETE THE PRODUCT?", row, this, table, user);
            } else {
                // Show an alert if no row is selected.
                new Alert("ALERT  NO SELECTED ROW", "No selected row!\nPlease select one by clicking it");
            }
        }
        // Handle clicks on the "INSERT" button.
        else if (e.getSource() == Button[3]) {
            // Open the ProductPopUp in "INSERT PRODUCT" mode.
            new ProductPopUp("INSERT PRODUCT", user, this);
        }
    }

    // Refreshes the product table with all product data from the database.
    public void refreshTable(){
        Select sc = new Select();
        try{
            table.setColumnCount(0); // Clear existing columns.
            table.setRowCount(0); // Clear existing rows.

            // Add the column headers to the table.
            for(int i = 0; i < column.length; i++){
                table.addColumn(column[i]);
            }

            // Construct the SQL query to fetch all products for the current user.
            String query = "SELECT `productID`, p.`Name`, c.`Name`, `Brand`, `Description`, `Quantity`, `Modal`, `retailPrice` FROM `product` p JOIN `category` c ON p.categoryID = c.categoryID WHERE p.`userID` = '" + user.getUserID() + "';";

            sc.Select(query); // Execute the query.
            ResultSet rs = sc.result; // Get the result set.

            // Populate the table with the fetched data.
            while(rs.next()){
                String[] dataRow = new String[column.length];
                for(int i = 0; i < column.length; i++){
                    dataRow[i] = rs.getString(i+1);
                }
                table.addRow(dataRow); // Add each row to the table.
            }
            table.fireTableDataChanged(); // Notify the table that its data has changed, triggering a re-render.
        } catch(Exception error){
            error.printStackTrace();
        } finally{
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }
    }

    // Refreshes the product table with data based on the provided SQL query.
    // @param query The SQL query to fetch the product data.
    public void refreshTable(String query){
        Select sc = new Select();
        try{
            table.setColumnCount(0); // Clear existing columns.
            table.setRowCount(0); // Clear existing rows.

            // Add the column headers to the table.
            for(int i = 0; i < column.length; i++){
                table.addColumn(column[i]);
            }

            sc.Select(query); // Execute the provided SQL query.
            ResultSet rs = sc.result; // Get the result set.

            // Populate the table with the fetched data.
            while (rs.next()) {
                String[] dataRow = new String[column.length];
                for (int i = 0; i < column.length; i++) {
                    dataRow[i] = rs.getString(i + 1);
                }
                table.addRow(dataRow); // Add each row to the table.
            }

            table.fireTableDataChanged(); // Notify the table that its data has changed, triggering a re-render.
        } catch(Exception error){
            error.printStackTrace();
        } finally{
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }
    }
}