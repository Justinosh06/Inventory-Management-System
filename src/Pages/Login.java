package Pages;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Constant.GUIConstant;
import java.sql.*;

import Database.CloseDB;
import Database.Select;
import Constant.FontConstant;
import GUIComponent.GUI;
import GUIComponent.setGridBagConstraints;
import Account.User;
import Validation.Alert;

import static GUIComponent.Component.panel;
import static GUIComponent.Component.label;
import static GUIComponent.Component.Button;
import static GUIComponent.Component.textField;
import static GUIComponent.Component.passwordField;

// The Login class provides the user interface for logging into the inventory management system.
// It allows users to enter their username and password to access the application.
public class Login extends GUI implements ActionListener {
    static boolean login_bool = false; // Initiate boolean login with a default value of false.
    JLabel alertMsg; // JLabel to display alert messages to the user (e.g., login errors).

    // Constructor for the Login class.
    public Login() {
        super("INVENTORY · LOGIN"); // Call the constructor of the superclass (GUI) with the login window title.
        login(); // Call the login method to set up the login UI.
    }

    // Sets up the user interface for the login page.
    public void login(){ // Create method login()
        final float titleSize = 64f; // Font size for the "LOGIN" title.
        final float labelSize = 16f; // Font size for regular labels.

        final Font titleFont = FontConstant.PlexMonoBold.deriveFont(titleSize); // Font for the "LOGIN" title.
        final Font labelFont = FontConstant.PlexMonoMedium.deriveFont(labelSize); // Font for labels like "USERNAME" and "PASSWORD".
        final Font thinLabelFont = FontConstant.PlexMonoLight.deriveFont(labelSize); // Font for the "Don't have an account?" label.
        final Font textFieldFont = FontConstant.PlexMonoMedium.deriveFont(labelSize); // Font for the text fields and password field.

        panel = new JPanel[2]; // Array to hold JPanels for layout.
        panel[0] = new JPanel(); // Main panel for the login form.
        panel[1] = new JPanel(); // Panel to hold the "Don't have an account?" label and button.

        add(panel[0]); // Add the main panel to the JFrame.

        Button = new JButton[2]; // Array to hold JButtons.
        Button[0] = new JButton(); // "LOGIN" button.
        Button[1] = new JButton(); // "Create Account" button.

        label = new JLabel[5]; // Array to hold JLabels.
        label[0] = new JLabel("LOGIN"); // "LOGIN" title label.
        label[1] = new JLabel("Don't have an account?"); // Label for the signup option.
        label[2] = new JLabel("USERNAME"); // Label for the username field.
        label[3] = new JLabel("PASSWORD"); // Label for the password field.
        label[4] = new JLabel(); // Label to display alert messages.

        panel[0].setBackground(GUIConstant.black); // Set the background color of the main panel.
        panel[0].setLayout(new GridBagLayout()); // Set the layout manager for the main panel.
        panel[0].setSize(new Dimension((int) (0.4*1080), 450)); // Set the preferred size of the main panel.

        // Constraints for the "LOGIN" title label.
        setGridBagConstraints myGbc = new setGridBagConstraints(1, 1, 1.0, 0, new Insets(5, (int) (0.35*1080), 0, (int) (0.35*1080)),"horizontal");
        GridBagConstraints gbc = myGbc.getGbc();

        label[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        label[0].setFont(titleFont);
        label[0].setForeground(GUIConstant.white1);
        label[0].setHorizontalAlignment(JLabel.CENTER);
        panel[0].add(label[0], gbc); // Add the "LOGIN" title label to the panel.

        // Constraints for the "Don't have an account?" section.
        gbc.insets = new Insets(5, (int) (0.2*1080), 15, (int) (0.2*1080));
        gbc.gridy = 2;
        panel[1].setLayout(new FlowLayout(FlowLayout.CENTER)); // Set FlowLayout for the signup options.
        panel[1].setBackground(GUIConstant.black);
        panel[0].add(panel[1], gbc); // Add the signup options panel to the main panel.

        label[1].setFont(thinLabelFont);
        label[1].setForeground(GUIConstant.white1);
        label[1].setHorizontalAlignment(JLabel.CENTER);
        panel[1].add(label[1]); // Add the "Don't have an account?" label.

        Button[1].setForeground(GUIConstant.white1);
        Button[1].setBackground(GUIConstant.black);
        Button[1].addActionListener(this); // Add ActionListener to the "Create Account" button.
        Button[1].setCursor(new Cursor(Cursor.HAND_CURSOR));
        Button[1].setFont(labelFont);
        Button[1].setBorder(null);
        Button[1].setText("Create Account");
        panel[1].add(Button[1]); // Add the "Create Account" button.

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
        textField[0].setBackground(GUIConstant.black);
        textField[0].setForeground(GUIConstant.white1);
        textField[0].setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        textField[0].setMargin(new Insets(10, 10, 10, 10));
        textField[0].setFont(textFieldFont);
        panel[0].add(textField[0], gbc); // Add the username text field.

        // Constraints for the "PASSWORD" label.
        gbc.insets = new Insets(5, (int) (0.35*1080), 2, (int) (0.35*1080));
        gbc.gridy = 5;
        label[3].setFont(labelFont);
        label[3].setHorizontalAlignment(JLabel.LEFT);
        label[3].setForeground(GUIConstant.white1);
        panel[0].add(label[3], gbc); // Add the "PASSWORD" label.

        // Constraints for the password field.
        gbc.insets = new Insets(5, (int) (0.35*1080), 15, (int) (0.35*1080));
        gbc.gridy = 6;
        passwordField = new JPasswordField();
        passwordField.setBounds(0, 0, (int) (1080 * 0.4), 16);
        passwordField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        passwordField.setMargin(new Insets(10, 10, 10, 10));
        passwordField.setBackground(GUIConstant.black);
        passwordField.setForeground(GUIConstant.white1);
        passwordField.setFont(textFieldFont);
        passwordField.setSize(new Dimension((int) (1080*0.4), 100));
        panel[0].add(passwordField, gbc); // Add the password field.

        // Constraints for the "LOGIN" button.
        gbc.insets = new Insets(5, (int) (0.35*1080), 0, (int) (0.35*1080));
        gbc.gridy = 8;
        Button[0].addActionListener(this); // Add ActionListener to the "LOGIN" button.
        Button[0].setText("LOGIN");
        Button[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[0].setMargin(new Insets(10, 10, 10, 10));
        Button[0].setFont(labelFont);
        Button[0].setAlignmentX(Component.CENTER_ALIGNMENT);
        Button[0].setBackground(GUIConstant.black);
        Button[0].setForeground(GUIConstant.white2);
        panel[0].add(Button[0], gbc); // Add the "LOGIN" button.

        // Constraints for the alert message label.
        gbc.insets = new Insets(-5, (int) (0.2*1080), 5, (int) (0.2*1080));
        gbc.gridy = 7;
        label[4].setFont(labelFont);
        label[4].setForeground(GUIConstant.red);
        label[4].setHorizontalAlignment(JLabel.CENTER);
        panel[0].add(label[4], gbc); // Add the alert message label.

        alertMsg = label[4]; // Assign the alert message label to the alertMsg variable.

        setVisible(true); // Make the login window visible.
        //pack();
    }

    // Handles action events triggered by button clicks.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle "LOGIN" button click.
        if(e.getSource() == Button[0]){
            System.out.println("Pages.Login button is pressed");

            String username = textField[0].getText(); // Get the entered username.
            char[] password = passwordField.getPassword(); // Get the entered password as a character array.
            String strPassword = new String(password); // Convert the password character array to a String.

            // Check if username and password fields are not empty.
            if(username != null && !username.isEmpty() && password.length != 0) {
                String query = "SELECT * FROM user WHERE name = '" + username + "'"; // SQL query to retrieve user data by username.
                Select select = new Select(); // Create a Select object to execute the query.

                select.Select(query); // Execute the query.
                ResultSet rs = select.result; // Get the result set.

                try {
                    // Check if a user with the entered username exists.
                    if(rs.next()){
                        String storedPassword = rs.getString("Password"); // Retrieve the stored password from the database.
                        // Verify if the entered password matches the stored password.
                        if (strPassword.equals(storedPassword)) {
                            System.out.println("Login Successfully");
                            String userID = rs.getString("userID"); // Retrieve the user ID.
                            int lowStockThreshold = rs.getInt("lowStockThreshold"); // Retrieve the low stock threshold.

                            // Create a User object to store the logged-in user's information.
                            User user = new User();
                            user.setUsername(username);
                            user.setUserID(userID);
                            user.setPassword(strPassword);
                            user.setLowStockThreshold(lowStockThreshold);

                            dispose(); // Close the login window.
                            Home homeWindow = new Home(user); // Create a new Home window for the logged-in user.
                            homeWindow.setLocation(getLocation()); // Set the location of the Home window to the login window's location.
                        } else {
                            alertMsg.setText("Wrong password!"); // Display an error message for an incorrect password.
                        }
                    } else{
                        alertMsg.setText("No such account!"); // Display an error message if the username doesn't exist.
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } finally{
                    CloseDB.closeConnection(select.getConnection()); // Close the database connection.
                }
            } else {
                alertMsg.setText("Please fill in username or password!!"); // Display an error message if username or password fields are empty.
            }
        }
        // Handle "Create Account" button click.
        else if(e.getSource() == Button[1]){
            System.out.println("Create account");

            dispose(); // Close the login window.
            Signup signupWindow = new Signup(); // Create a new Signup window.
            signupWindow.setLocation(getLocation()); // Set the location of the Signup window to the login window's location.
        }
    }
}