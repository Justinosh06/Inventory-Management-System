package GUIComponent;

import Account.User;
import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Insert;
import Database.Select;
import Pages.Home;
import Pages.ReportPage;
import Validation.Alert;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import static GUIComponent.Icon.AppIcon;

// The CategoryPopUp class provides a modal dialog for adding new product categories.
// It takes the current User object and the Home page instance as parameters to interact with the application state.
public class CategoryPopUp extends JFrame implements ActionListener {
    private User user; // The currently logged-in user.
    private Home home; // The instance of the Home page to refresh the category table.

    JButton submit; // Button to submit the new category name.
    JTextField categoryName; // Text field to enter the new category name.

    // Constructor for the CategoryPopUp.
    // @param user The currently logged-in User object.
    // @param home The instance of the Home page.
    public CategoryPopUp(User user, Home home){
        Font titleFont = FontConstant.PlexMonoBold.deriveFont(20f); // Font for the title.
        Font text = FontConstant.PlexMonoLight.deriveFont(14f); // Font for regular text.

        this.user = user; // Initialize the user.
        this.home = home; // Initialize the home page instance.

        setTitle("Add Category"); // Set the title of the pop-up window.
        setResizable(false); // Prevent resizing of the pop-up.
        setVisible(true); // Make the pop-up visible.
        setSize(280, 180); // Set the size of the pop-up.
        setIconImage(AppIcon.getImage()); // Set the application icon.
        setAlwaysOnTop(true); // Ensure the pop-up stays on top of other windows.
        setLocationRelativeTo(null); // Center the pop-up on the screen.

        JPanel container = new JPanel(); // Create a container panel.

        container.setBackground(GUIConstant.black); // Set background color.
        container.setPreferredSize(new Dimension(280, 180)); // Set preferred size.
        container.setLayout(new GridBagLayout()); // Use GridBagLayout for flexible component arrangement.
        add(container); // Add the container to the JFrame.

        // Constraints for the "ADD CATEGORY" title.
        setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, 1.0, 1.0, new Insets(10, 20, 5, 20) , "horizontal");
        GridBagConstraints gbc = myGbc.getGbc();

        JLabel title = new JLabel("ADD CATEGORY"); // Create the title label.

        title.setFont(titleFont); // Set the font.
        title.setForeground(GUIConstant.white1); // Set the text color.
        container.add(title, gbc); // Add the title to the container.

        // Constraints for the "NAME" label.
        myGbc = new setGridBagConstraints(0, 1, 1.0, 1.0, new Insets(5, 20, 0, 20), "horizontal");
        gbc = myGbc.getGbc();

        JLabel name = new JLabel("NAME"); // Create the "NAME" label.

        name.setFont(text); // Set the font.
        name.setForeground(GUIConstant.white1); // Set the text color.
        container.add(name, gbc); // Add the label to the container.

        // Constraints for the category name text field.
        myGbc = new setGridBagConstraints(0, 2, 1.0, 1.0, new Insets(5, 20, 0, 20), "horizontal");
        gbc = myGbc.getGbc();

        categoryName = new JTextField(); // Create the text field.

        categoryName.setFont(text); // Set the font.
        categoryName.setBackground(GUIConstant.black); // Set background color.
        categoryName.setForeground(GUIConstant.white1); // Set text color.
        categoryName.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        container.add(categoryName, gbc); // Add the text field to the container.

        // Constraints for the "ADD CATEGORY" submit button.
        myGbc = new setGridBagConstraints(0, 3, 1.0, 1.0, new Insets(5, 20, 20, 20), "horizontal");
        gbc = myGbc.getGbc();

        submit = new JButton("ADD CATEGORY"); // Create the submit button.

        submit.setFont(text); // Set the font.
        submit.setBackground(GUIConstant.black); // Set background color.
        submit.setForeground(GUIConstant.white1); // Set text color.
        submit.addActionListener(this); // Add ActionListener to handle button clicks.
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set cursor for interaction.
        submit.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        container.add(submit, gbc); // Add the button to the container.
    }

    // Handles action events, specifically the click on the submit button.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            String category = categoryName.getText(); // Get the text entered in the category name field.

            Select sc = new Select(); // Create a Select object for database queries.
            Insert insert = new Insert(); // Create an Insert object for database insertions.
            try{
                // Check if a category with the same name already exists for the current user.
                String query = "SELECT * FROM `category` WHERE `Name` = '" + category + "' AND `userID` = '" + user.getUserID() + "';";

                sc.Select(query); // Execute the query.
                ResultSet selectRs = sc.result; // Get the result set.

                // If a category with the same name exists, show an alert.
                if(selectRs.next()){
                    new Alert("ALERT  CATEGORY NAME DUPLICATION", "The category name has already existed!\nPlease rename the category!");
                } else {
                    // If the category name is unique, insert the new category into the database.
                    query = "INSERT INTO `category`(`Name`, `userID`)VALUES('" + category + "', '" + user.getUserID() + "');";

                    insert.Insert(query); // Execute the insert query.
                    int insertRs = insert.result; // Get the number of rows affected.

                    // If the insertion was successful, dispose of the pop-up.
                    if(insertRs == 1){
                        dispose();
                    }

                    // Refresh the category table on the Home page to reflect the newly added category.
                    query = "SELECT `categoryID`, `Name` FROM `category` WHERE userID = '" + user.getUserID() + "';";
                    home.refreshCategoryTable(query);
                }
            } catch(Exception error){
                error.printStackTrace();
            } finally{
                // Close the database connections.
                CloseDB.closeConnection(sc.getConnection());
                CloseDB.closeConnection(insert.getConnection());
            }
        }
    }
}