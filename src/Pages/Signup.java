package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.*;

import Constant.FontConstant;
import Constant.GUIConstant;

import Database.*;
import GUIComponent.GUI;
import Validation.Alert;

import static GUIComponent.Component.panel;
import static GUIComponent.Component.label;
import static GUIComponent.Component.Button;
import static GUIComponent.Component.textField;
import static GUIComponent.Component.passwordField;

// The Signup class provides the user interface for creating a new account in the inventory management system.
// It allows users to enter a username and password to register.
public class Signup extends GUI implements ActionListener {
    // Constructor for the Signup class.
    public Signup() {
        super("INVENTORY · SIGNUP"); // Call the constructor of the superclass (GUI) with the signup window title.
        signup(); // Call the signup method to set up the signup UI.
    }

    JLabel alertMsg; // JLabel to display alert messages to the user (e.g., registration errors).

    // Sets up the user interface for the signup page.
    public void signup(){
        final float titleSize = 48f; // Font size for the "CREATE ACCOUNT" title.
        final float labelSize = 16f; // Font size for regular labels.

        final Font titleFont = FontConstant.PlexMonoBold.deriveFont(titleSize); // Font for the "CREATE ACCOUNT" title.
        final Font labelFont = FontConstant.PlexMonoMedium.deriveFont(labelSize); // Font for labels like "USERNAME" and "PASSWORD".
        final Font thinLabelFont = FontConstant.PlexMonoLight.deriveFont(labelSize); // Font for the "Already have one?" label.
        final Font textFieldFont = FontConstant.PlexMonoMedium.deriveFont(labelSize); // Font for the text fields and password field.

        panel = new JPanel[2]; // Array to hold JPanels for layout.
        panel[0] = new JPanel(); // Main panel for the signup form.
        panel[1] = new JPanel(); // Panel to hold the "Already have one?" label and button.

        add(panel[0]); // Add the main panel to the JFrame.

        Button = new JButton[2]; // Array to hold JButtons.
        Button[0] = new JButton(); // "CREATE ACCOUNT" button.
        Button[1] = new JButton(); // "Login" button.

        label = new JLabel[5]; // Array to hold JLabels.
        label[0] = new JLabel("CREATE ACCOUNT"); // "CREATE ACCOUNT" title label.
        label[1] = new JLabel("Already have one?"); // Label for the login option.
        label[2] = new JLabel("USERNAME"); // Label for the username field.
        label[3] = new JLabel("PASSWORD"); // Label for the password field.
        label[4] = new JLabel(); // Label to display alert messages.

        panel[0].setBackground(GUIConstant.black); // Set the background color of the main panel.
        panel[0].setLayout(new GridBagLayout()); // Set the layout manager for the main panel.
        panel[0].setSize(new Dimension((int) (0.4*1080), 450)); // Set the preferred size of the main panel.

        GridBagConstraints gbc = new GridBagConstraints(); // Create GridBagConstraints for layout management.

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill the horizontal space.

        // Constraints for the "CREATE ACCOUNT" title label.
        gbc.insets = new Insets(5, (int) (0.1*1080), 0, (int) (0.1*1080));
        gbc.gridy = 1;
        label[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        label[0].setFont(titleFont);
        label[0].setForeground(GUIConstant.white1);
        label[0].setHorizontalAlignment(JLabel.CENTER);
        panel[0].add(label[0], gbc); // Add the "CREATE ACCOUNT" title label.

        // Constraints for the "Already have one?" section.
        gbc.insets = new Insets(5, (int) (0.2*1080), 15, (int) (0.2*1080));
        gbc.gridy = 2;
        panel[1].setLayout(new FlowLayout(FlowLayout.CENTER)); // Set FlowLayout for the login option.
        panel[1].setBackground(GUIConstant.black);
        panel[0].add(panel[1], gbc); // Add the login option panel.

        label[1].setFont(thinLabelFont);
        label[1].setForeground(GUIConstant.white1);
        label[1].setHorizontalAlignment(JLabel.CENTER);
        panel[1].add(label[1]); // Add the "Already have one?" label.

        Button[1].setForeground(GUIConstant.white1);
        Button[1].setBackground(GUIConstant.black);
        Button[1].addActionListener(this); // Add ActionListener to the "Login" button.
        Button[1].setCursor(new Cursor(Cursor.HAND_CURSOR));
        Button[1].setFont(labelFont);
        Button[1].setBorder(null);
        Button[1].setText("Login");
        panel[1].add(Button[1]); // Add the "Login" button.

        // Constraints for the "USERNAME" label.
        gbc.insets = new Insets(5, (int) (0.35*1080), 2, (int) (0.35*1080));
        gbc.gridy = 3;
        label[2].setFont(labelFont);
        label[2].setForeground(GUIConstant.white1);
        label[2].setHorizontalAlignment(JLabel.LEFT);
        panel[0].add(label[2], gbc); // Add the "USERNAME" label.

        // Constraints for the username text field.
        gbc.insets = new Insets(5, (int) (0.35*1080), 15, (int) (0.35*1080));
        gbc.gridy = 4;
        textField = new JTextField[1];
        textField[0] = new JTextField();
        textField[0].setForeground(GUIConstant.white1);
        textField[0].setBackground(GUIConstant.black);
        textField[0].setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textField[0].setMargin(new Insets(10, 10, 10, 10));
        textField[0].setFont(textFieldFont);
        panel[0].add(textField[0], gbc); // Add the username text field.

        // Constraints for the "PASSWORD" label.
        gbc.insets = new Insets(5, (int) (0.35*1080), 2, (int) (0.35*1080));
        gbc.gridy = 5;
        label[3].setFont(labelFont);
        label[3].setForeground(GUIConstant.white1);
        label[3].setHorizontalAlignment(JLabel.LEFT);
        panel[0].add(label[3], gbc); // Add the "PASSWORD" label.

        // Constraints for the password field.
        gbc.insets = new Insets(5, (int) (0.35*1080), 15, (int) (0.35*1080));
        gbc.gridy = 6;
        passwordField = new JPasswordField();
        passwordField.setForeground(GUIConstant.white1);
        passwordField.setBackground(GUIConstant.black);
        passwordField.setBounds(0, 0, (int) (1080 * 0.4), 16);
        passwordField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        passwordField.setMargin(new Insets(10, 10, 10, 10));
        passwordField.setFont(textFieldFont);
        passwordField.setSize(new Dimension((int) (1080*0.4), 100));
        panel[0].add(passwordField, gbc); // Add the password field.

        // Constraints for the "CREATE ACCOUNT" button.
        gbc.insets = new Insets(5, (int) (0.35*1080), 0, (int) (0.35*1080));
        gbc.gridy = 8;
        Button[0].addActionListener(this); // Add ActionListener to the "CREATE ACCOUNT" button.
        Button[0].setText("CREATE ACCOUNT");
        Button[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[0].setMargin(new Insets(10, 10, 10, 10));
        Button[0].setFont(labelFont);
        Button[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        Button[0].setBackground(GUIConstant.black);
        Button[0].setForeground(GUIConstant.white1);
        panel[0].add(Button[0], gbc); // Add the "CREATE ACCOUNT" button.

        // Constraints for the alert message label.
        gbc.insets = new Insets(0, (int) (0.2*1080), 5, (int) (0.2*1080));
        gbc.gridy = 7;
        label[4].setForeground(GUIConstant.red);
        label[4].setFont(labelFont);
        label[4].setHorizontalAlignment(JLabel.CENTER);
        panel[0].add(label[4], gbc); // Add the alert message label.

        alertMsg = label[4]; // Assign the alert message label to the alertMsg variable.
    }

    // Handles action events triggered by button clicks.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle "CREATE ACCOUNT" button click.
        if(e.getSource() == Button[0]){
            System.out.println("Create Button is pressed");

            String username = textField[0].getText(); // Get the entered username.
            char[] password = passwordField.getPassword(); // Get the entered password as a character array.
            String strPassword = new String(password); // Convert the password character array to a String.

            // Check if username and password fields are not empty and within length limits.
            if(username != null && !username.isEmpty() && password.length != 0 && username.length() <= 50 && strPassword.length() <= 20){
                String query = "SELECT COUNT(*) FROM user WHERE name = '" + username + "'"; // SQL query to check if the username already exists.
                Select select = new Select(); // Create a Select object to execute the query.
                Insert insert = new Insert(); // Create an Insert object to execute the insert query.

                try {
                    select.Select(query); // Execute the query.
                    select.result.next(); // Move the cursor to the first row of the result set.
                    int hasUser = select.result.getInt(1); // Get the count of users with the entered username.

                    // If the username is not already taken.
                    if (hasUser == 0) {
                        query = "SELECT MAX(userID) FROM user"; // SQL query to get the maximum existing userID.
                        select.Select(query); // Execute the query.
                        ResultSet selectRs = select.result; // Get the result set.

                        String newUserId;
                        if (selectRs.next()) {
                            String maxUserId = selectRs.getString(1); // Get the maximum userID.
                            // Generate a new userID by incrementing the numeric part of the maximum userID.
                            if (maxUserId == null) newUserId = "U1";
                            else newUserId = "U" + (Integer.parseInt(maxUserId.substring(1)) + 1);
                        } else {
                            newUserId = "U1"; // If no users exist yet, start with "U1".
                        }

                        // SQL query to insert the new user into the database.
                        query = "INSERT INTO `user` (userID, Name, Password, lowStockThreshold, itemNumber) VALUES ('" + newUserId + "', '" + username + "', '" + strPassword + "', " + 10 + ", " + 0 + ");";

                        insert.Insert(query); // Execute the insert query.
                        int insertRs = insert.result; // Get the number of affected rows.

                        // Check if the insertion was successful.
                        if (insertRs == 1) {
                            System.out.println("Inserted successfully");
                        } else System.out.println("Insertion failed");

                        dispose(); // Close the signup window.
                        Login loginWindow = new Login(); // Create a new Login window.
                        loginWindow.setLocation(getLocation()); // Set the location of the Login window to the signup window's location.
                    }
                    // If the username is taken or the input exceeds the length limit.
                    else if(strPassword.length() > 20 || username.length() > 50){
                        alertMsg.setText("Your username or password has exceed the length limit!"); // Display length limit error.
                    } else {
                        new Alert("Alert · Username is Taken", "USERNAME IS ALREADY TAKEN PLEASE USE ANOTHER ONE!"); // Display username taken alert.
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } finally{
                    CloseDB.closeConnection(select.getConnection()); // Close the database connection.
                }
            } else {
                alertMsg.setText("Please fill in username or password!!"); // Display an error if username or password fields are empty.
            }
        }
        // Handle "Login" button click.
        else if(e.getSource() == Button[1]){
            System.out.println("Pages.Login");

            dispose(); // Close the signup window.
            Login loginWindow = new Login(); // Create a new Login window.
            loginWindow.setLocation(getLocation()); // Set the location of the Login window to the signup window's location.
        }
    }
}