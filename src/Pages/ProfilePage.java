package Pages;

import Account.User;
import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Select;
import Database.Update;
import GUIComponent.GUI;
import GUIComponent.Navbar;
import GUIComponent.Component;
import GUIComponent.setGridBagConstraints;
import Validation.*;

import static GUIComponent.Component.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

// The ProfilePage class displays the user's profile information and allows them to edit their password and low stock threshold.
public class ProfilePage extends GUI implements ActionListener {
    private final User user; // The currently logged-in user.

    Component c = new Component(); // An instance of a custom Component class (presumably for UI elements).

    JButton changePwdBtn = new JButton(); // Button to initiate password change.
    JButton editButton = new JButton(); // Button to initiate editing of low stock threshold.
    JButton signOutBtn = new JButton(); //Button to sign out

    // Constructor for the ProfilePage.
    // @param user The currently logged-in User object.
    public ProfilePage(User user) {
        String title = "INVENTORY · PROFILE"; // Set the title of the page.

        super(title); // Call the constructor of the superclass (GUI).

        this.user = user; // Initialize the user.
        profile(); // Call the method to create and display the profile UI.
    }

    // Creates and arranges the UI components for the profile page.
    public void profile(){
        final float usernameSize = 64f; // Font size for the username.
        final float textSize = 16f; // Font size for regular text.

        final Font usernameText = FontConstant.PlexMonoBold.deriveFont(usernameSize); // Create font for username.
        final Font Text = FontConstant.PlexMonoLight.deriveFont(textSize); // Create font for regular text.

        panel = new JPanel[4]; // Array to hold multiple panels for layout.
        panel[0] = new JPanel(); // Main panel for the entire profile page.
        panel[1] = new JPanel(); // Panel to hold user information.
        panel[2] = new JPanel(); // Panel to hold password information and change button.
        panel[3] = new JPanel(); // Panel to hold low stock threshold information and edit button.

        label = new JLabel[6]; // Array to hold labels for displaying text.
        label[0] = new JLabel(); // Label for the username.
        label[1] = new JLabel(); // Label for "Password:".
        label[2] = new JLabel(); // Label to display masked password.
        label[3] = new JLabel(); // (Not used in the current implementation).
        label[4] = new JLabel(); // Label for "Low Stock Threshold:".
        label[5] = new JLabel(); // Label to display the low stock threshold value.

        Button = new JButton[2]; // Array to hold buttons (for increment/decrement of low stock).
        Button[0] = new JButton("+"); // Increment button.
        Button[1] = new JButton("-"); // Decrement button.

        add(panel[0]); // Add the main panel to the frame.
        panel[0].setLayout(new BorderLayout()); // Set BorderLayout for the main panel.
        panel[0].setPreferredSize(new Dimension(1080, 675)); // Set preferred size of the main panel.

        Navbar nvbar = new Navbar(panel[0], "profile", this, user); // Create and add the navigation bar.

        panel[1].setLayout(new GridBagLayout()); // Set GridBagLayout for the user info panel.
        panel[1].setBorder(new EmptyBorder(0, 20, 20, 20)); // Add padding around the user info panel.
        panel[1].setBackground(GUIConstant.black); // Set background color of the user info panel.
        panel[0].add(panel[1], BorderLayout.CENTER); // Add the user info panel to the center of the main panel.

        // Constraints for positioning components within panel[1].
        setGridBagConstraints myGbc = new setGridBagConstraints(1, 1, 1.0, 0, new Insets(5, (int) (0.2*1080), 0, (int) (0.2*1080)),"horizontal");
        GridBagConstraints gbc = myGbc.getGbc();

        // Display the username.
        String username = Uppercase.setUppercase(user.getUsername()); // Convert username to uppercase.
        label[0].setText(username); // Set the text of the username label.
        label[0].setFont(usernameText); // Set the font of the username label.
        label[0].setForeground(GUIConstant.white1); // Set the text color of the username label.
        label[0].setHorizontalAlignment(JLabel.CENTER); // Center the username label.
        panel[1].add(label[0], gbc); // Add the username label to panel[1].

        // Constraints for the password section.
        myGbc = new setGridBagConstraints(1, 2, 1.0, 0, new Insets(2, 2, 2, 2) , "horizontal");
        gbc = myGbc.getGbc();

        panel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Set FlowLayout for password panel.
        panel[2].setBackground(null); // Make the background transparent.
        panel[1].add(panel[2], gbc); // Add the password panel to panel[1].

        // Display "Password:".
        label[1].setText("Password: ");
        label[1].setFont(Text);
        label[1].setForeground(GUIConstant.white1);
        panel[2].add(label[1]);

        // Display masked password.
        String str = "";
        for(int i = 0; i < user.getPassword().length(); i++){
            str = str + "*"; // Create a string of asterisks.
        }

        label[2].setText(str);
        label[2].setFont(Text);
        label[2].setForeground(GUIConstant.white1);
        panel[2].add(label[2]);

        textField = new JTextField[1]; // Text field for editing password.
        textField[0] = new JTextField();

        textField[0].setVisible(false); // Initially hide the password text field.
        textField[0].setBackground(GUIConstant.black);
        textField[0].setForeground(GUIConstant.white1);
        textField[0].setText(user.getPassword()); // Set initial text to the current password.
        textField[0].setFont(Text);
        textField[0].setMargin(new Insets(2, 5, 2, 5)); // Add some margin.
        panel[2].add(textField[0]);

        // Button to change/update password.
        changePwdBtn.setText("CHANGE PASSWORD");
        changePwdBtn.setFont(Text);
        changePwdBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on hover.
        changePwdBtn.setForeground(GUIConstant.white1);
        changePwdBtn.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
        changePwdBtn.setBackground(null);
        changePwdBtn.addActionListener(this); // Add action listener to handle clicks.
        panel[2].add(changePwdBtn);

        // Constraints for the low stock threshold section (initially set but might be redundant).
        myGbc = new setGridBagConstraints(1, 3, 1.0, 0, new Insets(1, 2, 2, 2) , "horizontal");
        gbc = myGbc.getGbc();

        // Constraints for the low stock threshold display and edit controls.
        myGbc = new setGridBagConstraints(1, 4, 1.0, 0, new Insets(2, 2, 2, 2) , "horizontal");
        gbc = myGbc.getGbc();

        panel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Set FlowLayout for low stock panel.
        panel[3].setBackground(null); // Make background transparent.
        panel[1].add(panel[3], gbc); // Add low stock panel to panel[1].

        // Display "Low Stock Threshold:".
        label[4].setText("Low Stock Threshold: ");
        label[4].setFont(Text);
        label[4].setForeground(GUIConstant.white1);
        panel[3].add(label[4]);

        // Increment button for low stock threshold.
        Button[0].setBorder(new EmptyBorder(0, 2, 0, 2));
        Button[0].setBackground(null);
        Button[0].setForeground(GUIConstant.white1);
        Button[0].setVisible(false); // Initially hidden.
        Button[0].addActionListener(this);
        Button[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel[3].add(Button[0]);

        // Display the current low stock threshold value.
        label[5].setText(Integer.toString(user.getLowStockThreshold()));
        label[5].setFont(Text);
        label[5].setForeground(GUIConstant.white1);
        panel[3].add(label[5]);

        c.declareIntegerField(1); // Declare an integer field in the custom Component class.

        c.integerField[0].setVisible(false); // Initially hide the integer input field.
        c.integerField[0].setFont(Text);
        c.integerField[0].setBackground(GUIConstant.black);
        c.integerField[0].setForeground(GUIConstant.white1);
        c.integerField[0].setBorder(new EmptyBorder(0, 2, 0, 2));
        panel[3].add(c.integerField[0]);

        // Decrement button for low stock threshold.
        Button[1].setBorder(new EmptyBorder(0, 2, 0, 2));
        Button[1].setBackground(null);
        Button[1].setForeground(GUIConstant.white1);
        Button[1].addActionListener(this);
        Button[1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button[1].setVisible(false); // Initially hidden.
        panel[3].add(Button[1]);

        // Button to enable/disable editing of the low stock threshold.
        editButton.setText("EDIT");
        editButton.setFont(Text);
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editButton.setBackground(null);
        editButton.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
        editButton.setForeground(GUIConstant.white1);
        editButton.addActionListener(this);
        panel[3].add(editButton);

        myGbc = new setGridBagConstraints(1, 5, 1.0, 0, new Insets(5, 350, 2, 350) , "horizontal");
        gbc = myGbc.getGbc();

        // Button to sign out.
        signOutBtn.setText("SIGN OUT");
        signOutBtn.setFont(Text);
        signOutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutBtn.setBackground(null);
        signOutBtn.setBorder(new LineBorder(GUIConstant.white1, 1));
        signOutBtn.setForeground(GUIConstant.white1);
        signOutBtn.addActionListener(this);
        panel[1].add(signOutBtn, gbc);
    }

    // Handles action events triggered by the buttons on the profile page.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle clicks on the "CHANGE PASSWORD" / "UPDATE" button.
        if (e.getSource() == changePwdBtn){
            // If the password text field is visible (in edit mode).
            if (textField[0].isVisible()) {
                String pwd = textField[0].getText(); // Get the new password from the text field.
                String query = "UPDATE `user` SET `password` = '" + pwd + "' WHERE `userID` = '" + user.getUserID() + "';"; // SQL query to update password.

                Update up = null;
                try {
                    up = new Update(); // Create an Update object for database interaction.

                    up.Update(query); // Execute the update query.
                    int rs = up.result; // Get the number of rows affected.

                    // If the update was successful.
                    if (rs == 1) {
                        System.out.println("Update successfully");

                        user.setPassword(pwd); // Update the user object's password.

                        // Mask the password again in the display label.
                        String str = "";
                        for (int i = 0; i < user.getPassword().length(); i++) {
                            str = str + "*";
                        }

                        textField[0].setVisible(false); // Hide the password text field.
                        label[2].setText(str); // Update the masked password label.
                        label[2].setVisible(true); // Make the masked password label visible.
                        changePwdBtn.setText("CHANGE PASSWORD"); // Reset the button text.
                    } else {
                        System.out.println("Update failed");
                        new Alert("Alert · Update Failed", "UPDATE FAILED!"); // Show an alert message.
                    }
                } catch (Exception error) {
                    System.err.println(error);
                } finally {
                    CloseDB.closeConnection(up.getConnection()); // Close the database connection.
                }
            } else {
                // If the password text field is not visible (in display mode).
                textField[0].setText(user.getPassword()); // Set the text field to the current password.

                changePwdBtn.setText("UPDATE"); // Change the button text to "UPDATE".
                textField[0].setVisible(true); // Make the password text field visible.
                label[2].setVisible(false); // Hide the masked password label.
            }
        }
        // Handle clicks on the "EDIT" / "UPDATE" button for low stock threshold.
        else if(e.getSource() == editButton){
            // If the increment/decrement buttons and input field are visible (in edit mode).
            if(Button[0].isVisible() && Button[1].isVisible() && c.getIntegerFieldValue(0) != null){
                String query = "UPDATE `user` SET `lowStockThreshold` = " + c.getIntegerFieldValue(0) + " WHERE `userID` = '" + user.getUserID() + "';"; // SQL query to update low stock threshold.

                Update up = null;
                try {
                    up = new Update(); // Create an Update object.
                    up.Update(query); // Execute the update query.

                    int rs = up.result; // Get the number of rows affected.

                    // If the update was successful.
                    if (rs == 1) {
                        System.out.println("Update successfully");

                        editButton.setText("EDIT"); // Reset the button text to "EDIT".
                        user.setLowStockThreshold(c.getIntegerFieldValue(0)); // Update the user object's low stock threshold.

                        label[5].setText(Integer.toString(user.getLowStockThreshold())); // Update the displayed value.

                        c.integerField[0].setVisible(false); // Hide the input field.
                        label[5].setVisible(true); // Make the displayed value visible.
                        Button[0].setVisible(false); // Hide the increment button.
                        Button[1].setVisible(false); // Hide the decrement button.
                    } else {
                        System.out.println("Update failed");
                    }
                } catch (Exception error){
                    System.err.println(error);
                } finally {
                    CloseDB.closeConnection(up.getConnection()); // Close the database connection.
                }
            } else {
                // If not in edit mode.
                editButton.setText("UPDATE"); // Change the button text to "UPDATE".

                c.setIntegerField(0, user.getLowStockThreshold()); // Set the initial value in the input field.
                c.integerField[0].setVisible(true); // Make the input field visible.
                label[5].setVisible(false); // Hide the displayed value.
                Button[0].setVisible(true); // Make the increment button visible.
                Button[1].setVisible(true); // Make the decrement button visible.
            }
        }
        // Handle clicks on the increment button.
        else if(e.getSource() == Button[0]){
            c.setIntegerField(0, c.getIntegerFieldValue(0)+1); // Increment the value in the integer field.
        }
        // Handle clicks on the decrement button.
        else if(e.getSource() == Button[1]){
            // Decrement the value if it's not already 0.
            if(c.getIntegerFieldValue(0) != 0) {
                c.setIntegerField(0, c.getIntegerFieldValue(0)-1);
            }
        }
        // Handle clicks on signing out button.
        else if(e.getSource() == signOutBtn){
            dispose(); // Dispose profilePage
            Login login = new Login(); // Create a new Login
            login.setLocation(this.getLocation()); // Set location of login to the location of profilePage
            login.setVisible(true); // Set Login to be visible
        }
    }
}