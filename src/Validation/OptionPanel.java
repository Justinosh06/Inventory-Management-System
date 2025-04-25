package Validation;

import Account.User;
import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Delete;
import GUIComponent.setGridBagConstraints;
import Pages.ProductPage;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import static GUIComponent.Component.*;
import static GUIComponent.Icon.AppIcon;

// The OptionPanel class creates a custom confirmation dialog with "YES" and "Cancel" options.
// It is used to prompt the user for confirmation before performing an action, such as deleting a product.
public class OptionPanel extends JFrame implements ActionListener {
    private int row; // The row index in the JTable for the item being acted upon.
    private ProductPage pd; // The instance of the ProductPage to refresh the product table after an action.
    private JTable table; // The JTable from which the item is being selected.
    private User user; // The currently logged-in user.

    boolean result; // A boolean to store the user's choice (though currently not directly used externally).
    JButton cancelButton = new JButton("Cancel"); // Button to cancel the action.
    JButton positiveButton = new JButton("YES"); // Button to confirm the action.

    // Constructor for the OptionPanel.
    // @param title The title of the confirmation dialog.
    // @param msg The message to display to the user.
    // @param row The row index of the selected item in the table.
    // @param pd The ProductPage instance.
    // @param table The JTable instance.
    // @param user The currently logged-in User object.
    public OptionPanel(String title, String msg, int row, ProductPage pd, JTable table, User user){
        this.row = row;
        this.pd = pd;
        this.table = table;
        this.user = user;

        Font Text = FontConstant.PlexMonoMedium.deriveFont(16f); // Font for the message and buttons.

        setTitle(title); // Set the title of the dialog.
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent default close operation.

        // Add a WindowListener to handle the window closing event (e.g., clicking the close button).
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                result = false; // Set result to false if the window is closed without using the buttons.
                dispose(); // Close the dialog.
            }
        });
        setSize(400, 150); // Set the size of the dialog.
        setResizable(false); // Prevent resizing of the dialog.
        setVisible(true); // Make the dialog visible.
        setIconImage(AppIcon.getImage()); // Set the application icon.
        setAlwaysOnTop(true); // Ensure the dialog stays on top of other windows.
        setLocationRelativeTo(null); // Center the dialog on the screen.

        JPanel panel0 = new JPanel(); // Main panel.
        JPanel panel1 = new JPanel(); // Panel for the message.
        JPanel panel2 = new JPanel(); // Panel for the buttons.

        add(panel0); // Add the main panel to the JFrame.
        panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical arrangement.
        panel0.setBackground(GUIConstant.black); // Set background color.
        panel0.setPreferredSize(new Dimension(400, 150)); // Set preferred size.

        panel1.setBackground(null); // Make background transparent.
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Use FlowLayout for centering the message.
        panel0.add(panel1); // Add the message panel to the main panel.

        JTextArea message = new JTextArea(msg); // Create a JTextArea to display the message.

        message.setForeground(GUIConstant.white1); // Set text color.
        message.setBackground(GUIConstant.black); // Set background color.
        message.setFont(Text); // Set font.
        message.setBounds(0, 0, 360, 80); // Set bounds.
        message.setEditable(false); // Make the text area non-editable.
        message.setLineWrap(true); // Enable line wrapping.
        message.setWrapStyleWord(true); // Wrap at word boundaries.
        panel1.add(message); // Add the message to its panel.

        panel2.setBackground(null); // Make background transparent.
        panel2.setLayout(new GridBagLayout()); // Use GridBagLayout for button placement.
        panel0.add(panel2); // Add the button panel to the main panel.

        // Constraints for the "YES" button.
        setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, 0.5, 1.0, new Insets(2, (int)0.3*400, 0, 2) , "both");
        GridBagConstraints gbc = myGbc.getGbc();

        positiveButton.setBackground(GUIConstant.black); // Set background color.
        positiveButton.setForeground(GUIConstant.white1); // Set text color.
        positiveButton.setFont(Text); // Set font.
        positiveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on hover.
        positiveButton.addActionListener(this); // Add ActionListener to handle button clicks.
        positiveButton.setBorder(new CompoundBorder(new EmptyBorder(2, 5, 2, 5), new LineBorder(GUIConstant.white1, 1))); // Set border.
        panel2.add(positiveButton, gbc); // Add the button to the panel.

        // Constraints for the "Cancel" button.
        myGbc = new setGridBagConstraints(1, 0, 0.5, 1.0, new Insets(2, 2, 0, (int)0.3*400), "both");
        gbc = myGbc.getGbc();

        cancelButton.setBackground(GUIConstant.black); // Set background color.
        cancelButton.setForeground(GUIConstant.white1); // Set text color.
        cancelButton.setFont(Text); // Set font.
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on hover.
        cancelButton.addActionListener(this); // Add ActionListener to handle button clicks.
        cancelButton.setBorder(new CompoundBorder(new EmptyBorder(2, 5, 2, 5), new LineBorder(GUIConstant.white1, 1))); // Set border.
        panel2.add(cancelButton, gbc); // Add the button to the panel.
    }

    // Handles action events, specifically button clicks.
    @Override
    public void actionPerformed(ActionEvent e) {
        // If the "YES" button is clicked, proceed with the action (in this case, deletion).
        if(e.getSource() == positiveButton){
            System.out.println("Delete");

            Delete dlt = new Delete(); // Create a Delete object for database deletion.
            try{
                // Construct the SQL query to delete the product based on its ID and the current user's ID.
                String query = "DELETE FROM `product` WHERE `productID` = '" + table.getValueAt(row, 0) + "' AND `userID` = '" + user.getUserID() + "';";

                dlt.Delete(query); // Execute the delete query.
                int rs = dlt.result; // Get the number of rows affected by the deletion.

                // If the deletion was successful (one row affected).
                if(rs == 1){
                    System.out.println("Delete Successfully");
                } else {
                    // If deletion failed, show an alert message.
                    new Alert("ALERT  DELETION FAILED", "Deletion went wrong!\nPlease try again");
                }
            } catch (Exception error){
                error.printStackTrace();
            } finally {
                CloseDB.closeConnection(dlt.getConnection()); // Close the database connection.
                pd.refreshTable(); // Refresh the product table on the ProductPage.
                dispose(); // Close the confirmation dialog.
            }
        } else if (e.getSource() == cancelButton){
            dispose(); // Close the confirmation dialog if "Cancel" is clicked.
            result = false; // Set result to false (though not directly used externally in this implementation).
        }
    }
}