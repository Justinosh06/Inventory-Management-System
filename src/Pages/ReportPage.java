package Pages;

import Account.*;
import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Select;
import GUIComponent.*;

import static GUIComponent.Component.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

// The ReportPage class displays a report of transaction history and overall profit for the logged-in user.
public class ReportPage extends GUI implements ActionListener {
    private User user; // The currently logged-in user.
    private String[] column = {"ID", "Name", "Profit", "Changes"}; // Column headers for the transaction table.

    // Constructor for the ReportPage.
    // @param user The currently logged-in User object.
    public ReportPage(User user) {
        this.user = user; // Initialize the user.
        String title = "INVENTORY · REPORT"; // Set the title of the page.

        super(title); // Call the constructor of the superclass (GUI).
        getReport(); // Call the method to generate and display the report UI.
    }

    // Creates and arranges the UI components for the report page.
    public void getReport(){
        Font title = FontConstant.PlexMonoBold.deriveFont(20f); // Font for the main title.
        Font text = FontConstant.PlexMonoLight.deriveFont(16f); // Font for regular text.

        Font innerTitle = FontConstant.PlexMonoBold.deriveFont(12f); // Font for inner section titles.
        Font innerText = FontConstant.PlexMonoLight.deriveFont(22f); // Font for inner section values.

        Button = new JButton[1]; // Array to hold buttons (in this case, only the export button).
        Button[0] = new JButton(); // The export button.

        label = new JLabel[5]; // Array to hold labels for displaying text.
        label[0] = new JLabel(); // Label for the "Transition" title.
        label[1] = new JLabel(); // Label for "No. of Product".
        label[2] = new JLabel(); // Label to display the number of products.
        label[3] = new JLabel(); // Label for "Total Profit".
        label[4] = new JLabel(); // Label to display the total profit.

        panel = new JPanel[8]; // Array to hold multiple panels for layout.
        panel[0] = new JPanel(); // Main panel for the entire report page.
        panel[1] = new JPanel(); // Panel for the navigation bar.
        panel[2] = new JPanel(); // Panel to hold the main content area.
        panel[3] = new JPanel(); // Panel to hold summary information (product count, profit, export button).
        panel[4] = new JPanel(); // Panel to hold the "Transition" title and the transaction table.
        panel[5] = new JPanel(); // Panel to hold the transaction table.
        panel[6] = new JPanel(); // Panel to display the number of products.
        panel[7] = new JPanel(); // Panel to display the total profit.

        add(panel[0]); // Add the main panel to the frame.
        panel[0].setLayout(new BorderLayout()); // Set BorderLayout for the main panel.
        panel[0].setBorder(new EmptyBorder(0, (int) 0.1*1080, 10, (int) 0.1*1080)); // Add left and right padding.
        panel[0].setBackground(GUIConstant.black); // Set background color of the main panel.
        panel[0].setPreferredSize(new Dimension(1080, 675)); // Set preferred size of the main panel.

        panel[0].add(panel[1], BorderLayout.NORTH); // Add the navigation bar panel to the top.
        panel[1].setBackground(GUIConstant.black); // Set background color of the navigation bar panel.

        Navbar nv = new Navbar(panel[1], "report", this, user); // Create and add the navigation bar.

        panel[2].setLayout(new BorderLayout()); // Set BorderLayout for the main content panel.
        panel[2].setBackground(null); // Make background transparent.
        panel[0].add(panel[2], BorderLayout.CENTER); // Add the main content panel to the center.

        panel[3].setLayout(new FlowLayout()); // Set FlowLayout for the summary information panel.
        panel[3].setBackground(null); // Make background transparent.
        panel[2].add(panel[3], BorderLayout.NORTH); // Add the summary panel to the top of the main content.

        panel[4].setLayout(new BorderLayout()); // Set BorderLayout for the transaction section.
        panel[4].setBackground(null); // Make background transparent.
        panel[4].setBorder(new EmptyBorder(10, 108, 10, 108)); // Add padding around the transaction section.
        panel[2].add(panel[4], BorderLayout.CENTER); // Add the transaction section to the center of the main content.

        // Title for the transaction table.
        label[0].setText("Transition");
        label[0].setFont(title);
        label[0].setForeground(GUIConstant.white1);
        panel[4].add(label[0], BorderLayout.NORTH);

        panel[5].setLayout(new GridBagLayout()); // Set GridBagLayout for the panel holding the transaction table.
        panel[5].setBackground(null); // Make background transparent.
        panel[4].add(panel[5], BorderLayout.CENTER); // Add the table panel to the center of the transaction section.

        // Fetch transaction data from the database.
        Select sc = new Select();
        try{
            String query = "SELECT t.Action AS Action, t.Quantity AS Quantity, p.Name AS Name, p.productID AS productID, p.Profit AS Profit FROM `transaction` t JOIN `product` p ON t.productID = p.productID WHERE t.userID = '" + user.getUserID() + "';";

            sc.Select(query); // Execute the SQL query.
            ResultSet rs = sc.result; // Get the result set.

            // If there are no transactions.
            if(!rs.next()){
                setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, 1.0, 1.0, new Insets(0, 0, 2, 0), "both");
                GridBagConstraints gbc = myGbc.getGbc();
                gbc.anchor = GridBagConstraints.CENTER;

                JLabel noProduct = new JLabel("No Product");
                noProduct.setFont(text);
                noProduct.setForeground(GUIConstant.grey);
                noProduct.setHorizontalAlignment(JLabel.CENTER);
                panel[5].add(noProduct, gbc); // Display "No Product" message.
            } else {
                // Create a custom table to display the transaction data.
                Table table = new Table(column, panel[5]);

                table.setTableFont(text);
                table.setTableHeaderFont(title);
                table.setTableBackground(GUIConstant.black);
                table.setTableForeground(GUIConstant.white1);
                table.setTableHeaderBackground(GUIConstant.black);
                table.setTableHeaderForeground(GUIConstant.white1);

                // Populate the first row of the table.
                String[] dataRow = new String[column.length];
                dataRow[0] = rs.getString("productID");
                dataRow[1] = rs.getString("Name");
                dataRow[2] = rs.getString("Profit");

                int quantity = Integer.parseInt(rs.getString("Quantity"));
                String action = rs.getString("Action");

                // Format the "Changes" column based on the transaction type.
                if(action.equals("add")){
                    dataRow[3] = "+" + quantity;
                } else if(action.equals("minus")){
                    dataRow[3] = "-" + quantity;
                }

                table.addRow(dataRow); // Add the first row to the table.

                // Populate the remaining rows of the table.
                while (rs.next()) {
                    dataRow = new String[column.length];

                    dataRow[0] = rs.getString("productID");
                    dataRow[1] = rs.getString("Name");
                    dataRow[2] = rs.getString("Profit");

                    quantity = Integer.parseInt(rs.getString("Quantity"));
                    action = rs.getString("Action");

                    if(action.equals("add")){
                        dataRow[3] = "+" + quantity;
                    } else if(action.equals("minus")){
                        dataRow[3] = "-" + quantity;
                    }

                    table.addRow(dataRow); // Add subsequent rows to the table.
                }
            }
        } catch (Exception error){
            error.printStackTrace();
        } finally{
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }

        // Panel to display the number of products.
        panel[6].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(10, 10, 10, 10)));
        panel[6].setBackground(null);
        panel[6].setLayout(new BoxLayout(panel[6], BoxLayout.Y_AXIS));
        panel[6].setForeground(GUIConstant.white1);
        panel[3].add(panel[6]);

        // Label for "No. of Product".
        label[1].setText("No. of Product");
        label[1].setForeground(GUIConstant.white1);
        label[1].setFont(innerTitle);
        label[1].setHorizontalAlignment(JLabel.LEFT);
        panel[6].add(label[1]);

        // Fetch the number of products for the user.
        sc = new Select();
        try{
            String query = "SELECT `itemNumber` FROM `user` WHERE `userID` = '" + user.getUserID() + "';";

            sc.Select(query);
            ResultSet rs = sc.result;

            if(rs.next()){
                label[2].setText(rs.getString(1) + " items"); // Display the number of items.
            }
        } catch (Exception error){
            error.printStackTrace();
        } finally {
            CloseDB.closeConnection(sc.getConnection()); // Close the database connection.
        }

        label[2].setForeground(GUIConstant.white1);
        label[2].setFont(innerText);
        panel[6].add(label[2]);

        // Panel to display the total profit.
        panel[7].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(10, 10, 10, 10)));
        panel[7].setForeground(GUIConstant.white1);
        panel[7].setLayout(new BoxLayout(panel[7], BoxLayout.Y_AXIS));
        panel[7].setBackground(null);
        panel[3].add(panel[7]);

        // Label for "Total Profit".
        label[3].setText("Total Profit");
        label[3].setFont(innerTitle);
        label[3].setForeground(GUIConstant.white1);
        panel[7].add(label[3]);

        // Calculate the total profit from the transactions.
        sc = new Select();
        try{
            String query = "SELECT t.Quantity AS Quantity, p.Modal AS Modal, p.Profit AS Profit, t.Action AS Action FROM `transaction` t JOIN `product` p ON t.productID = p.productID WHERE t.userID = '" + user.getUserID() + "';";

            sc.Select(query);
            ResultSet rs = sc.result;

            double amount = 0.00; // Initialize the total profit.

            while(rs.next()){
                int quantity = rs.getInt("Quantity");
                double profit = rs.getDouble("Profit");
                double modal = rs.getDouble("Modal");
                String action = rs.getString("Action");

                // Calculate profit based on the transaction type (add reduces profit, minus increases it).
                if(action.equals("minus")) {
                    amount += ((double) quantity * profit);
                } else if(action.equals("add")){
                    amount -= ((double) quantity * modal);
                }
            }

            label[4].setText("$ " + String.format("%.2f", amount)); // Display the total profit, formatted to two decimal places.

            // Set the color of the profit based on whether it's positive or negative.
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
        panel[7].add(label[4]);

        // Export button.
        Button[0].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(18, 32, 18, 32)));
        Button[0].setForeground(GUIConstant.white1);
        Button[0].setText("EXPORT");
        Button[0].setFont(innerText);
        Button[0].addActionListener(this);
        Button[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[0].setBackground(null);
        panel[3].add(Button[0]);
    }

    // Handles action events triggered by the buttons on the report page.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle clicks on the "EXPORT" button.
        if(e.getSource() == Button[0]){
            new ExportData(user); // Create a new ExportData object to handle data export.
        }
    }
}