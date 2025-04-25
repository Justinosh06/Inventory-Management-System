package Pages;

import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Select;
import GUIComponent.*;
import Account.User;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import static GUIComponent.Component.panel;
import static GUIComponent.Component.Button;
import static GUIComponent.Component.label;

// The Home class represents the main overview page of the inventory application.
// It displays low stock products, recent transactions, category list, and sales summary.
public class Home extends GUI implements ActionListener{ //Pages.Home class inherits GUI class
    private final User user; // The currently logged-in user.
    private Table categoryTable; // A custom table to display product categories.

    // Constructor for the Home page.
    // @param user The currently logged-in User object.
    public Home(User user) { // Constructor of Pages.Home with parameter user
        String title = "INVENTORY · OVERVIEW"; // Declare string title

        this.user = user; // Initialize the user.
        super(title); // Define the frame's title with var title
        home(); // Call home method with var user
    }

    // Creates and arranges the UI components for the home page.
    public void home() { // home method with parameter user
        final float titleSize = 18f; // Font size for section titles.
        final float textSize = 14f; // Font size for regular text.
        final float tableTextSize = 16f; // Font size for table content.

        final Font tableFont = FontConstant.PlexMonoLight.deriveFont(tableTextSize); // Font for table text.
        final Font tableHeaderFont = FontConstant.PlexMonoBold.deriveFont(tableTextSize); // Font for table headers.
        final Font titleFont = FontConstant.PlexMonoMedium.deriveFont(titleSize); // Font for section titles.
        final Font textFont = FontConstant.PlexMonoLight.deriveFont(textSize); // Font for general text.
        final Font innerText = FontConstant.PlexMonoLight.deriveFont(56f); // Large font for sales figure.

        Button = new JButton[2]; // Array to hold buttons (Create Category, View Report).
        Button[0] = new JButton(); // Button to create a new category.
        Button[1] = new JButton(); // Button to view the full report page.

        panel = new JPanel[12]; // Declare JPanel panel with the size of 12 to hold different sections.
        panel[0] = new JPanel(); // Main panel for the entire home page.
        panel[1] = new JPanel(); // Panel to hold the main content with GridBagLayout.
        panel[2] = new JPanel(); // Panel for the "Low Stock" section.
        panel[3] = new JPanel(); // Panel in the right column for "Categories" and "Create Category" button.
        panel[4] = new JPanel(); // Panel for the "Recent Transaction" section.
        panel[5] = new JPanel(); // Panel for the "Sales" section and "View Report" button.
        panel[6] = new JPanel(); // Outer panel for the "Categories" section.
        panel[7] = new JPanel(); // Panel to hold the low stock table.
        panel[8] = new JPanel(); // Panel to hold the recent transaction table.
        panel[9] = new JPanel(); // Panel to hold the category table.

        add(panel[0]); // Add panel[0] into JFrame.
        panel[0].setLayout(new BorderLayout()); // Set layout of panel[0] to be BorderLayout.
        panel[0].setBackground(GUIConstant.black);
        panel[0].setPreferredSize(new Dimension(1080, 675)); // Set preferred size of panel[0].

        Navbar navbar = new Navbar(panel[0], "overview", this, user); // Create and add the navigation bar.

        panel[1].setLayout(new GridBagLayout()); // Set GridBagLayout for the main content area.
        panel[1].setBorder(new EmptyBorder(0, 20, 20, 20)); // Add padding around the main content.
        panel[1].setBackground(GUIConstant.black);
        panel[1].setForeground(GUIConstant.white1);
        panel[0].add(panel[1], BorderLayout.CENTER); // Add the main content panel to the center.

        // Constraints for the "Low Stock" section.
        setGridBagConstraints myGbc = new setGridBagConstraints(0, 1, 0.5, 1.0, new Insets(2, 2, 2, 2), "both");
        GridBagConstraints gbc = myGbc.getGbc();

        panel[2].setBackground(GUIConstant.black);
        panel[2].setLayout(new BorderLayout());
        panel[2].setForeground(GUIConstant.white1);
        panel[2].setBorder(new CompoundBorder(new roundifyBorder(20), new EmptyBorder(20, 20, 20, 20)));
        panel[1].add(panel[2], gbc); // Add "Low Stock" panel to the grid.

        // Constraints for the right column panel (Categories and Create Category).
        myGbc = new setGridBagConstraints(0, 2, 0.5, 1.0, new Insets(2, 2, 2, 2), "both");
        gbc = myGbc.getGbc();

        panel[3].setBackground(GUIConstant.black);
        panel[3].setLayout(new GridBagLayout());
        panel[1].add(panel[3], gbc); // Add right column panel to the grid.

        // Constraints for the "Recent Transaction" section.
        myGbc = new setGridBagConstraints(1, 1, 0.5, 1.0, new Insets(2, 2, 2, 2), "both");
        gbc = myGbc.getGbc();

        panel[4].setBackground(GUIConstant.black);
        panel[4].setForeground(GUIConstant.white1);
        panel[4].setBorder(new CompoundBorder(new roundifyBorder(20), new EmptyBorder(20, 20, 20, 20)));
        panel[4].setLayout(new BorderLayout());
        panel[1].add(panel[4], gbc); // Add "Recent Transaction" panel to the grid.

        // Constraints for the "Sales" section and "View Report" button.
        myGbc = new setGridBagConstraints(1, 2, 0.5, 1.0, new Insets(2, 2, 2, 2), "both");
        gbc = myGbc.getGbc();

        panel[5].setBackground(GUIConstant.black);
        panel[5].setForeground(GUIConstant.white1);
        panel[5].setBorder(new CompoundBorder(new roundifyBorder(20), new EmptyBorder(20, 20, 20, 20)));
        panel[5].setLayout(new BoxLayout(panel[5], BoxLayout.Y_AXIS));
        panel[1].add(panel[5], gbc); // Add "Sales" panel to the grid.

        // Constraints for the outer "Categories" section panel.
        myGbc = new setGridBagConstraints(0, 1, 1.0, 0.85, new Insets(0, 0, 2, 0), "both");
        gbc = myGbc.getGbc();

        panel[6].setBackground(GUIConstant.black);
        panel[6].setForeground(GUIConstant.white1);
        panel[6].setLayout(new BorderLayout());
        panel[6].setBorder(new CompoundBorder(new roundifyBorder(20), new EmptyBorder(20, 20, 20, 20)));
        panel[3].add(panel[6], gbc); // Add "Categories" panel to the right column.

        label = new JLabel[5]; // Array to hold labels for section titles.
        label[0] = new JLabel("Low Stock");
        label[1] = new JLabel("Recent Transaction");
        label[2] = new JLabel("Categories");
        label[3] = new JLabel("Sales");
        label[4] = new JLabel(); // Label for the sales figure.

        label[0].setFont(titleFont);
        label[0].setForeground(GUIConstant.white1);
        label[0].setHorizontalAlignment(JLabel.LEFT);
        panel[2].add(label[0], BorderLayout.NORTH); // Add "Low Stock" title.

        panel[7].setBackground(GUIConstant.black);
        panel[7].setBorder(new EmptyBorder(10, 0, 0, 0));
        panel[7].setLayout(new GridBagLayout());
        panel[2].add(panel[7], BorderLayout.CENTER); // Panel to hold the low stock table.

        // Fetch and display low stock products.
        Select select = null;
        try {
            select = new Select();

            String query = "SELECT `productID`, `Name`, `Quantity` FROM product WHERE `quantity` <= " + user.getLowStockThreshold() + " AND `userID` = '" + user.getUserID() + "';";
            select.Select(query);

            ResultSet rs = select.result;

            ArrayList<String[]> dataList = new ArrayList<>();
            String[] column = {"ID", "Name", "Quantity"}; // Column headers for the low stock table.

            // If no low stock products found.
            if (!rs.next()) {
                myGbc = new setGridBagConstraints(0, 0, 1.0, 1.0, new Insets(0, 0, 2, 0), "both");
                gbc = myGbc.getGbc();
                gbc.anchor = GridBagConstraints.CENTER;

                JLabel noProduct = new JLabel("No Product");
                noProduct.setFont(textFont);
                noProduct.setForeground(GUIConstant.grey);
                noProduct.setHorizontalAlignment(JLabel.CENTER);
                panel[7].add(noProduct, gbc); // Display "No Product" message.
            } else {
                // Create and populate the low stock table.
                Table lowStockTable = new Table(column, panel[7]);

                String[] rowData = new String[column.length]; // Create a row array.
                for (int i = 0; i < column.length; i++) {
                    rowData[i] = rs.getString(i + 1); // Get data from the ResultSet.
                }
                lowStockTable.addRow(rowData); // Add the first row.

                while (rs.next()) {
                    rowData = new String[column.length]; // Create a new row array for subsequent rows.
                    for (int i = 0; i < column.length; i++) {
                        rowData[i] = rs.getString(i + 1); // Get data for each column.
                    }
                    lowStockTable.addRow(rowData); // Add the row to the table.
                }

                // Apply styling to the low stock table.
                lowStockTable.setTableFont(tableFont);
                lowStockTable.setTableHeaderFont(tableHeaderFont);
                lowStockTable.setTableBackground(GUIConstant.black);
                lowStockTable.setTableForeground(GUIConstant.white1);
                lowStockTable.setTableHeaderBackground(GUIConstant.black);
                lowStockTable.setTableHeaderForeground(GUIConstant.white1);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            CloseDB.closeConnection(select.getConnection()); // Close the database connection.
        }

        label[1].setFont(titleFont);
        label[1].setForeground(GUIConstant.white1);
        label[1].setHorizontalAlignment(JLabel.LEFT);
        panel[4].add(label[1], BorderLayout.NORTH); // Add "Recent Transaction" title.

        panel[8].setBackground(GUIConstant.black);
        panel[8].setBorder(new EmptyBorder(10, 0, 0, 0));
        panel[8].setLayout(new GridBagLayout());
        panel[4].add(panel[8], BorderLayout.CENTER); // Panel to hold the recent transaction table.

        // Fetch and display recent transactions.
        try {
            select = new Select();

            String query = "SELECT p.productID, p.Name AS productName, t.Action AS Action, t.Quantity AS Quantity FROM `transaction` t JOIN `product` p ON t.productID = p.productID WHERE t.userID = '" + user.getUserID() + "';";
            select.Select(query);

            ResultSet rs = select.result;

            ArrayList<String[]> dataList = new ArrayList<>();
            String[] column = {"ID", "Name", "Changes"}; // Column headers for the transaction table.

            // If no transactions found.
            if (!rs.next()) {
                myGbc = new setGridBagConstraints(0, 0, 1.0, 1.0, new Insets(0, 0, 2, 0), "both");
                gbc = myGbc.getGbc();
                gbc.anchor = GridBagConstraints.CENTER;

                JLabel noProduct = new JLabel("No Product");
                noProduct.setFont(textFont);
                noProduct.setForeground(GUIConstant.grey);
                noProduct.setHorizontalAlignment(JLabel.CENTER);
                panel[8].add(noProduct, gbc); // Display "No Product" message.
            } else {
                // Create and populate the recent transaction table.
                Table transactionTable = new Table(column, panel[8]);

                String[] rowData = new String[column.length]; // Create a row array.
                for (int i = 0; i < column.length; i++) {
                    if(i == column.length - 1){
                        String action = rs.getString("Action");
                        int quantity = rs.getInt("Quantity");
                        String str = "";

                        if (action.equals("add")) {
                            str = "+ " + quantity;
                        } else if (action.equals("minus")) {
                            str = "- " + quantity;
                        }

                        rowData[i] = str; // Format the "Changes" column.
                    } else {
                        rowData[i] = rs.getString(i + 1); // Get data from the ResultSet.
                    }
                }
                transactionTable.addRow(rowData); // Add the first row.

                while (rs.next()) {
                    for (int i = 0; i < column.length; i++) {
                        if(i == column.length - 1){
                            String action = rs.getString("Action");
                            int quantity = rs.getInt("Quantity");
                            String str = "";

                            if (action.equals("add")) {
                                str = "+ " + quantity;
                            } else if (action.equals("minus")) {
                                str = "- " + quantity;
                            }

                            rowData[i] = str; // Format the "Changes" column for subsequent rows.
                        } else {
                            rowData[i] = rs.getString(i + 1); // Get data for each column.
                        }
                    }
                    transactionTable.addRow(rowData); // Add the row to the table.
                }

                // Apply styling to the recent transaction table.
                transactionTable.setTableFont(tableFont);
                transactionTable.setTableHeaderFont(tableHeaderFont);
                transactionTable.setTableBackground(GUIConstant.black);
                transactionTable.setTableForeground(GUIConstant.white1);
                transactionTable.setTableHeaderBackground(GUIConstant.black);
                transactionTable.setTableHeaderForeground(GUIConstant.white1);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            CloseDB.closeConnection(select.getConnection()); // Close the database connection.
        }

        label[2].setFont(titleFont);
        label[2].setForeground(GUIConstant.white1);
        label[2].setHorizontalAlignment(JLabel.LEFT);
        panel[6].add(label[2], BorderLayout.NORTH); // Add "Categories" title.

        panel[9].setBackground(GUIConstant.black);
        panel[9].setBorder(new EmptyBorder(10, 0, 0, 0));
        panel[9].setLayout(new GridBagLayout());
        panel[6].add(panel[9], BorderLayout.CENTER); // Panel to hold the category table.

        // Fetch and display product categories.
        try {
            select = new Select();

            String query = "SELECT `categoryID`, `Name` FROM `category` WHERE userID = '" + user.getUserID() + "';";
            System.out.println(query);
            select.Select(query);

            ResultSet rs = select.result;

            ArrayList<String[]> dataList = new ArrayList<>();
            String[] column = {"ID", "Name"}; // Column headers for the category table.
            categoryTable = new Table(column, panel[9]); //Create category table

            // If no categories found.
            if (rs.next()){
                // Populate the category table.
                String[] rowData = new String[column.length]; // Create a row array.
                for (int i = 0; i < column.length; i++) {
                    rowData[i] = rs.getString(i+1); // Get data from the ResultSet.
                }
                categoryTable.addRow(rowData); // Add the first row.

                while (rs.next()) {
                    rowData = new String[column.length]; // Create a new row array for subsequent rows.
                    for (int i = 0; i < column.length; i++) {
                        rowData[i] = rs.getString(i + 1); // Get data for each column.
                    }
                    categoryTable.addRow(rowData); // Add the row to the table.
                }

                // Apply styling to the category table.
                categoryTable.setTableFont(tableFont);
                categoryTable.setTableHeaderFont(tableHeaderFont);
                categoryTable.setTableBackground(GUIConstant.black);
                categoryTable.setTableForeground(GUIConstant.white1);
                categoryTable.setTableHeaderBackground(GUIConstant.black);
                categoryTable.setTableHeaderForeground(GUIConstant.white1);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            CloseDB.closeConnection(select.getConnection()); // Close the database connection.
        }

        label[3].setFont(titleFont);
        label[3].setForeground(GUIConstant.white1);
        label[3].setHorizontalAlignment(JLabel.LEFT);
        panel[5].add(label[3], BorderLayout.NORTH); // Add "Sales" title.

        Select sc = new Select();
        try{
            String query = "SELECT t.Quantity AS Quantity, p.Modal AS Modal, p.Profit AS Profit, t.Action AS Action FROM `transaction` t JOIN `product` p ON t.productID = p.productID WHERE t.userID = '" + user.getUserID() + "';";

            sc.Select(query);
            ResultSet rs = sc.result;

            double amount = 0.00; // Initialize the total sales amount.

            while(rs.next()){
                int quantity = rs.getInt("Quantity");
                double profit = rs.getDouble("Profit");
                double modal = rs.getDouble("Modal");
                String action = rs.getString("Action");

                // Calculate the sales figure (simplified here, might need more complex logic).
                if(action.equals("minus")) {
                    amount += ((double) quantity * profit);
                } else if(action.equals("add")){
                    amount -= ((double) quantity * modal);
                }
            }

            label[4].setText("$ " + String.format("%.2f", amount)); // Display the total sales amount.

            // Set the color based on whether sales are positive or negative.
            if(amount < 0){
                label[4].setForeground(GUIConstant.red);
            } else {
                label[4].setForeground(GUIConstant.white1);
            }
        } catch (Exception error){
            error.printStackTrace();
        } finally {
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }

        label[4].setFont(innerText);
        panel[5].add(label[4]); // Add the sales figure label.

        // "View Report" button.
        Button[1].setText("VIEW REPORT");
        Button[1].setFont(FontConstant.PlexMonoSemiBold.deriveFont(16f));
        Button[1].setBackground(GUIConstant.black);
        Button[1].setForeground(GUIConstant.white1);
        Button[1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[1].setBorder(new CompoundBorder(new roundifyBorder(20), new EmptyBorder(12, 220, 12, 220)));
        Button[1].addActionListener(this);
        panel[5].add(Button[1]); // Add the "View Report" button.

        // "Create Category" button.
        myGbc = new setGridBagConstraints(0, 2, 1.0, 0.15, new Insets(2, 0, 0, 0), "both");
        gbc = myGbc.getGbc();

        Button[0].setText("CREATE CATEGORY");
        Button[0].setFont(FontConstant.PlexMonoSemiBold.deriveFont(16f));
        Button[0].setBackground(GUIConstant.black);
        Button[0].setForeground(GUIConstant.white1);
        Button[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[0].setBorder(new roundifyBorder(20));
        Button[0].addActionListener(this);
        panel[3].add(Button[0], gbc); // Add the "Create Category" button.
    }

    // Handles action events triggered by the buttons on the home page.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle clicks on the "CREATE CATEGORY" button.
        if(e.getSource() == Button[0]){
            new CategoryPopUp(user, this); // Open the category creation popup.
        }
        // Handle clicks on the "VIEW REPORT" button.
        else if(e.getSource() == Button[1]){
            this.dispose(); // Close the current home page.
            ReportPage rp = new ReportPage(user); // Create a new ReportPage instance.
            rp.setLocation(this.getLocation()); // Set the location of the report page to the current home page's location.
            rp.setVisible(true); // Make the report page visible.
        }
    }

    // Refreshes the category table with new data from the database.
    // @param query The SQL query to fetch the updated category data.
    public void refreshCategoryTable(String query){
        String[] column = {"ID", "Name"}; // Column headers for the category table.
        Select sc = new Select();
        try{
            categoryTable.setColumnCount(0); // Clear existing columns.
            categoryTable.setRowCount(0); // Clear existing rows.

            // Add the column headers to the table.
            for(int i = 0; i < column.length; i++){
                categoryTable.addColumn(column[i]);
            }

            sc.Select(query); // Execute the provided SQL query.
            ResultSet rs = sc.result; // Get the result set.

            // Populate the table with the fetched data.
            while (rs.next()) {
                String[] dataRow = new String[column.length];
                for (int i = 0; i < column.length; i++) {
                    dataRow[i] = rs.getString(i + 1); // Get data for each column.
                }
                categoryTable.addRow(dataRow); // Add the row to the table.
            }

            categoryTable.fireTableDataChanged(); // Notify the table that its data has changed, triggering a re-render.
        } catch(Exception error){
            error.printStackTrace();
        } finally{
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }
    }
}