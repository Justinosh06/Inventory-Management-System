package Account;

import Database.CloseDB;
import Database.Select;

import java.awt.*;
import java.io.*;
import java.sql.*;

// The ExportData class provides functionality to export transaction data for a specific user to a CSV file.
public class ExportData {
    // Constructor for the ExportData class.
    // @param user The User object for whom the transaction data will be exported.
    public ExportData(User user) {
        Select sc = new Select(); // Create an instance of the Select class for database queries.
        Connection connection = null; // Declare a Connection object to manage the database connection.
        FileWriter fileWriter = null; // Declare a FileWriter object to write to the file.
        BufferedWriter bufferedWriter = null; // Declare a BufferedWriter object for efficient writing.
        File exportedFile = null; // Declare a File object to represent the exported file.

        try {
            connection = sc.getConnection(); // Establish a database connection.
            String query = "SELECT t.productID, p.Name, t.Quantity, t.Action, p.Profit FROM `transaction` t JOIN `product` p ON t.productID = p.productID WHERE t.userID = '" + user.getUserID() + "';";

            sc.Select(query);
            ResultSet resultSet = sc.result; // Execute the query and get the result set.

            // Create a new CSV file with a unique timestamp in the filename.
            String filename = "report_inventory_" + System.currentTimeMillis() + ".csv";
            exportedFile = new File(filename);
            fileWriter = new FileWriter(exportedFile);
            bufferedWriter = new BufferedWriter(fileWriter);

            // Write the header row to the CSV file.
            bufferedWriter.write("Product ID,Name,Change,Profit");
            bufferedWriter.newLine(); // Add a new line after the header.

            // Write the data rows from the result set to the CSV file.
            while (resultSet.next()) {
                String productID = resultSet.getString("productID");
                String name = resultSet.getString("Name");
                int quantity = resultSet.getInt("Quantity");
                String action = resultSet.getString("Action");
                double profit = resultSet.getDouble("Profit");

                profit *= quantity; // Calculate the total profit for the transaction.

                String change = null;
                if(action.equals("add")){
                    change = "+" + quantity; // Indicate an added quantity.
                } else if(action.equals("minus")){
                    change = "-" + quantity; // Indicate a removed quantity.
                }

                // Format the data row as a comma-separated string.
                String line = String.format("%s,%s,%s,%.2f", productID, name, change, profit);
                bufferedWriter.write(line); // Write the data row to the file.
                bufferedWriter.newLine(); // Add a new line after each data row.
            }

            System.out.println("Data exported to: " + exportedFile.getAbsolutePath());

            // Attempt to open the exported file using the default system application.
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(exportedFile);
            } else {
                System.out.println("Desktop API is not supported on this platform.");
            }

            // Close database resources.
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Ensure the database connection is closed.
            CloseDB.closeConnection(connection);
            // Ensure the file writer and buffered writer are closed.
            try {
                if (bufferedWriter != null) bufferedWriter.close();
                if (fileWriter != null) fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}