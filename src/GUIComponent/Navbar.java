package GUIComponent;

import Pages.*; // Imports classes from the Pages package
import Account.User; // Imports the User class from the Account package
import Constant.FontConstant; // Imports font-related constants
import Constant.GUIConstant; // Imports GUI-related constants
import Validation.Uppercase; // Imports the Uppercase class for text manipulation

import javax.swing.*; // Imports core Swing classes
import javax.swing.border.CompoundBorder; // Imports the CompoundBorder class for combining borders
import javax.swing.border.EmptyBorder; // Imports the EmptyBorder class for creating empty space around components
import java.awt.*; // Imports core AWT classes for layout and graphics
import java.awt.event.ActionEvent; // Imports the ActionEvent class for handling actions
import java.awt.event.ActionListener; // Imports the ActionListener interface for handling action events

// The Navbar class represents a navigation bar component for the application.
// It displays a set of buttons allowing users to navigate between different pages.
public class Navbar implements ActionListener {
    private JPanel navbarPanel; // The main panel for the navigation bar
    private String pageName; // The name of the currently displayed page
    private String[] pages = {"overview", "product", "report", "profile"}; // An array of page names

    private JButton[] navButton = new JButton[pages.length]; // An array to hold the navigation buttons

    private JFrame currentFrame; // The current JFrame the navigation bar is associated with
    private final User user; // The currently logged-in user

    // Constructor for the Navbar class.
    // @param parentPanel The panel to which the navigation bar will be added.
    // @param page        The name of the current page.
    // @param frame       The current JFrame.
    // @param user        The currently logged-in user.
    public Navbar(JPanel parentPanel, String page, JFrame frame, User user) {
        navbarPanel = new JPanel(); // Initialize the navigation bar panel
        parentPanel.add(navbarPanel, BorderLayout.NORTH); // Add the navigation bar to the top of the parent panel

        currentFrame = frame; // Set the current frame
        this.user = user; // Set the current user

        pageName = page; // Set the name of the current page
        getNav(); // Call the method to create and configure the navigation bar
    }

    // Creates and configures the navigation bar with buttons and styling.
    public void getNav(){
        Icon icon = new Icon(); // Create an instance of the Icon class (assuming it provides icons)

        JPanel logoPanel = new JPanel(); // Panel to hold the application logo
        JPanel buttonContainer = new JPanel(); // Panel to hold the navigation buttons

        JLabel label = new JLabel(); // Label to display the application logo

        navbarPanel.setBackground(GUIConstant.black); // Set the background color of the navigation bar
        navbarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Set the layout manager for the navigation bar

        buttonContainer.setBackground(GUIConstant.black); // Set the background color of the button container
        buttonContainer.setForeground(GUIConstant.white1); // Set the foreground color of the button container
        buttonContainer.setBorder(new roundifyBorder(20)); // Apply a rounded border to the button container
        buttonContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Set the layout manager for the button container
        navbarPanel.add(buttonContainer); // Add the button container to the navigation bar

        // Loop through the page names to create navigation buttons
        for(int i = 0; i < pages.length; i++) {
            navButton[i] = new JButton(Uppercase.setUppercase(pages[i])); // Create a new button with an uppercase page name

            // Apply different borders to the active and inactive buttons
            if(pageName.equals(pages[i])) {
                navButton[i].setBorder(new CompoundBorder(new roundifyBorder(20), new EmptyBorder(12, 12, 12, 12)));
            } else {
                navButton[i].setBorder(new EmptyBorder(12, 12, 12, 12));
            }
            navButton[i].setBackground(null); // Make the button background transparent
            navButton[i].setOpaque(false); // Ensure transparency
            navButton[i].setForeground(GUIConstant.white1); // Set the button text color
            navButton[i].setHorizontalTextPosition(JButton.CENTER); // Center the button text
            navButton[i].setFont(FontConstant.PlexMonoMedium.deriveFont(12f)); // Set the button font
            navButton[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set the cursor to a hand cursor on hover
            buttonContainer.add(navButton[i]); // Add the button to the button container

            navButton[i].addActionListener(this); // Add an action listener to each button
        }

        logoPanel.setBackground(GUIConstant.black); // Set the background color of the logo panel
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Set the layout manager for the logo panel
        navbarPanel.add(logoPanel); // Add the logo panel to the navigation bar

        label.setIcon(icon.scaledAppIcon); // Set the application logo icon
        label.setForeground(GUIConstant.white1); // Set the logo text color
        label.setBorder(new EmptyBorder(0, 10, 0, 10)); // Add padding around the logo
        label.setFont(FontConstant.PlexMonoSemiBold.deriveFont(16f)); // Set the logo font
        logoPanel.add(label); // Add the logo label to the logo panel
    }

    // Handles action events triggered by the navigation buttons.
    // @param e The ActionEvent object.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle clicks on the "overview" button
        if (e.getSource() == navButton[0]){
            currentFrame.dispose(); // Dispose the current frame
            Home homepage = new Home(user); // Create a new Home page instance
            homepage.setLocation(currentFrame.getLocation()); // Set the location of the new frame to the current frame's location
            homepage.setVisible(true); // Make the new frame visible
        }
        // Handle clicks on the "product" button
        else if (e.getSource() == navButton[1]){
            currentFrame.dispose(); // Dispose the current frame
            ProductPage pp = new ProductPage(user); // Create a new ProductPage instance
            pp.setLocation(currentFrame.getLocation()); // Set the location of the new frame
            pp.setVisible(true); // Make the new frame visible
        }
        // Handle clicks on the "report" button
        else if (e.getSource() == navButton[2]){
            currentFrame.dispose(); // Dispose the current frame
            ReportPage rp = new ReportPage(user); // Create a new ReportPage instance
            rp.setLocation(currentFrame.getLocation()); // Set the location of the new frame
            rp.setVisible(true); // Make the new frame visible
        }
        // Handle clicks on the "profile" button
        else if (e.getSource() == navButton[3]){
            currentFrame.dispose(); // Dispose the current frame
            ProfilePage pp = new ProfilePage(user); // Create a new ProfilePage instance
            pp.setLocation(currentFrame.getLocation()); // Set the location of the new frame
            pp.setVisible(true); // Make the new frame visible
        }
    }
}