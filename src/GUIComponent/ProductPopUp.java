package GUIComponent;

import Account.Product;
import Account.User;
import Constant.FontConstant;
import Constant.GUIConstant;
import Database.CloseDB;
import Database.Insert;
import Database.Select;
import Database.Update;
import GUIComponent.roundifyBorder;
import Pages.ProductPage;
import Validation.Alert;

import static GUIComponent.Component.*;
import static GUIComponent.Icon.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Date;

// The ProductPopUp class provides a modal dialog for adding new products or updating existing ones.
// It adapts its UI and functionality based on whether it's in "add" or "update" mode.
public class ProductPopUp extends JFrame implements ActionListener{
    JButton submit; // Button to submit the product information (either "ADD PRODUCT" or "UPDATE").
    boolean update = false; // Flag indicating whether the pop-up is for updating an existing product.

    private User user; // The currently logged-in user.
    private Product product; // The product object being updated (only in update mode).
    private ProductPage pd; // The instance of the ProductPage to refresh the product table.

    Component c = new Component(); // An instance of the Component class (presumably for UI element creation).

    // UI components: JPanels for layout.
    JPanel panel0 = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    // UI components: JLabels for displaying text.
    JLabel label0 = new JLabel();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();
    JLabel label5 = new JLabel();
    JLabel label6 = new JLabel();
    JLabel label7 = new JLabel();
    JLabel label8 = new JLabel();

    // UI components: JTextFields for user input.
    JTextField textField0 = new JTextField(); // Product ID (only for adding).
    JTextField textField1 = new JTextField(); // Product Name.
    JTextField textField2 = new JTextField(); // Product Brand.
    JTextField textField3 = new JTextField(); // Product Description.

    // UI component: JComboBox for selecting the product category.
    JComboBox dropdown = new JComboBox();

    // Constructor for adding a new product.
    public ProductPopUp(String title, User user, ProductPage pd) {
        this.pd = pd;
        this.user = user;
        this.product = product;
        this.update = false; // Set update flag to false for adding.

        final Font TitleText = FontConstant.PlexMonoBold.deriveFont(24f); // Font for the title.
        final Font Text = FontConstant.PlexMonoMedium.deriveFont(12f); // Font for regular text.

        setTitle(title); // Set the title of the pop-up.
        setResizable(false); // Prevent resizing.
        setVisible(true); // Make visible.
        setSize(320, 520); // Set size.
        setIconImage(AppIcon.getImage()); // Set app icon.
        setAlwaysOnTop(true); // Keep on top.
        setLocationRelativeTo(null); // Center on screen.

        panel0.setBackground(GUIConstant.black); // Set background color.
        panel0.setLayout(new BorderLayout()); // Use BorderLayout.
        panel0.setBorder(new EmptyBorder(20, 15, 20, 15)); // Set padding.
        add(panel0); // Add to JFrame.

        label0.setText("ADD PRODUCT"); // Set title text.
        label0.setForeground(GUIConstant.white1); // Set text color.
        label0.setFont(TitleText); // Set font.
        panel0.add(label0, BorderLayout.NORTH); // Add to top.

        panel1.setLayout(new GridBagLayout()); // Use GridBagLayout for the form.
        panel1.setBackground(GUIConstant.black); // Set background.
        panel0.add(panel1, BorderLayout.CENTER); // Add to center.

        // Constraints for "PRODUCT ID" label.
        setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1.0, new Insets(2, 2, 0, 2) , "horizontal");
        GridBagConstraints gbc = myGbc.getGbc();

        label1.setText("PRODUCT ID"); // Set text.
        label1.setForeground(GUIConstant.white1); // Set color.
        label1.setFont(Text); // Set font.
        panel1.add(label1, gbc); // Add to panel.

        // Constraints for product ID text field.
        myGbc = new setGridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        textField0.setFont(Text); // Set font.
        textField0.setBackground(GUIConstant.black); // Set background.
        textField0.setForeground(GUIConstant.white1); // Set text color.
        textField0.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        textField0.setMargin(new Insets(2, 5, 2, 5)); // Set margin.
        panel1.add(textField0, gbc); // Add to panel.

        // Constraints for "NAME" label.
        myGbc = new setGridBagConstraints(0, 2, 2.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
        gbc = myGbc.getGbc();

        label2.setText("NAME"); // Set text.
        label2.setForeground(GUIConstant.white1); // Set color.
        label2.setFont(Text); // Set font.
        panel1.add(label2, gbc); // Add to panel.

        // Constraints for name text field.
        myGbc = new setGridBagConstraints(0, 3, 2.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        textField1.setFont(Text); // Set font.
        textField1.setBackground(GUIConstant.black); // Set background.
        textField1.setForeground(GUIConstant.white1); // Set text color.
        textField1.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        textField1.setMargin(new Insets(2, 5, 2, 5)); // Set margin.
        panel1.add(textField1, gbc); // Add to panel.

        // Constraints for "BRAND" label.
        myGbc = new setGridBagConstraints(0, 4, 2.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
        gbc = myGbc.getGbc();

        label3.setText("BRAND"); // Set text.
        label3.setForeground(GUIConstant.white1); // Set color.
        label3.setFont(Text); // Set font.
        panel1.add(label3, gbc); // Add to panel.

        // Constraints for brand text field.
        myGbc = new setGridBagConstraints(0, 5, 2.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        textField2.setFont(Text); // Set font.
        textField2.setBackground(GUIConstant.black); // Set background.
        textField2.setForeground(GUIConstant.white1); // Set text color.
        textField2.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        textField2.setMargin(new Insets(2, 5, 2, 5)); // Set margin.
        panel1.add(textField2, gbc); // Add to panel.

        // Constraints for "DESCRIPTION" label.
        myGbc = new setGridBagConstraints(0, 6, 2.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
        gbc = myGbc.getGbc();

        label4.setText("DESCRIPTION"); // Set text.
        label4.setForeground(GUIConstant.white1); // Set color.
        label4.setFont(Text); // Set font.
        panel1.add(label4, gbc); // Add to panel.

        // Constraints for description text field.
        myGbc = new setGridBagConstraints(0, 7, 2.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        textField3.setFont(Text); // Set font.
        textField3.setBackground(GUIConstant.black); // Set background.
        textField3.setForeground(GUIConstant.white1); // Set text color.
        textField3.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        textField3.setMargin(new Insets(2, 5, 2, 5)); // Set margin.
        panel1.add(textField3, gbc); // Add to panel.

        // Constraints for modal and retail price labels/fields (horizontal layout).
        myGbc = new setGridBagConstraints(0, 8, 1.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
        gbc = myGbc.getGbc();

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS)); // Horizontal BoxLayout.
        panel2.setBackground(GUIConstant.black); // Set background.
        panel1.add(panel2, gbc); // Add to panel.

        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS)); // Vertical BoxLayout for modal price.
        panel3.setBackground(GUIConstant.black); // Set background.
        panel2.add(panel3); // Add to horizontal container.

        label5.setText("MODAL PRICE"); // Set text.
        label5.setHorizontalAlignment(JLabel.LEFT); // Align left.
        label5.setFont(Text); // Set font.
        label5.setForeground(GUIConstant.white1); // Set color.
        panel3.add(label5); // Add to modal price container.

        panel2.add(Box.createHorizontalStrut(10)); // Add spacing.

        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS)); // Vertical BoxLayout for retail price.
        panel4.setBackground(GUIConstant.black); // Set background.
        panel2.add(panel4); // Add to horizontal container.

        label6.setText("RETAIL PRICE"); // Set text.
        label6.setHorizontalAlignment(JLabel.LEFT); // Align left.
        label6.setFont(Text); // Set font.
        label6.setForeground(GUIConstant.white1); // Set color.
        panel4.add(label6); // Add to retail price container.

        c.declarePriceField(2); // Declare two price input fields.

        c.priceField[0].setFont(Text); // Set font for modal price field.
        c.priceField[0].setBackground(null); // No background.
        c.priceField[0].setForeground(GUIConstant.white1); // Set text color.
        c.priceField[0].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        c.priceField[0].setMargin(new Insets(2, 5, 2, 5)); // Set margin.
        panel3.add(c.priceField[0]); // Add to modal price container.

        c.priceField[1].setFont(Text); // Set font for retail price field.
        c.priceField[1].setBackground(null); // No background.
        c.priceField[1].setForeground(GUIConstant.white1); // Set text color.
        c.priceField[1].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5))); // Set border.
        panel4.add(c.priceField[1]); // Add to retail price container.

        // Constraints for "CATEGORY" label.
        myGbc = new setGridBagConstraints(0, 9, 1.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        label7.setText("CATEGORY"); // Set text.
        label7.setForeground(GUIConstant.white1); // Set color.
        label7.setFont(Text); // Set font.
        panel1.add(label7, gbc); // Add to panel.

        // Constraints for category dropdown.
        myGbc = new setGridBagConstraints(0, 10, 1.0, 1.0, new Insets(2, 0, 2, 0), "horizontal");
        gbc = myGbc.getGbc();

        dropdown = new JComboBox(); // Create dropdown.
        dropdown.setBorder(new CompoundBorder(new EmptyBorder(2, 5, 2, 5), new LineBorder(GUIConstant.white1, 1))); // Set border.
        dropdown.setBackground(GUIConstant.black); // Set background.
        dropdown.setForeground(GUIConstant.white1); // Set text color.
        panel1.add(dropdown, gbc); // Add to panel.

        // Populate the category dropdown from the database.
        Select sl = new Select();
        try{
            String query = "SELECT * FROM category WHERE `userID` = '" + user.getUserID() + "';"; // Fetch categories for the current user.

            sl.Select(query); // Execute query.
            ResultSet rs = sl.result; // Get results.

            while(rs.next()){
                dropdown.addItem(rs.getString("Name")); // Add category names to the dropdown.
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            CloseDB.closeConnection(sl.getConnection()); // Close connection.
        }

        // Constraints for "QUANTITY" label.
        myGbc = new setGridBagConstraints(0, 11, 1.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        label8.setText("QUANTITY"); // Set text.
        label8.setForeground(GUIConstant.white1); // Set color.
        label8.setFont(Text); // Set font.
        panel1.add(label8, gbc); // Add to panel.

        c.declareIntegerField(1); // Declare one integer input field for quantity.

        // Constraints for quantity input field.
        myGbc = new setGridBagConstraints(0, 12, 1.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
        gbc = myGbc.getGbc();

        c.integerField[0].setFont(Text); // Set font.
        c.integerField[0].setBackground(GUIConstant.black); // Set background.
        c.integerField[0].setForeground(GUIConstant.white1); // Set text color.
        c.integerField[0].setBorder(new CompoundBorder(new EmptyBorder(2, 5, 2, 5), new LineBorder(GUIConstant.white1, 1))); // Set border.
        panel1.add(c.integerField[0], gbc); // Add to panel.

        // Constraints for the "ADD PRODUCT" button.
        myGbc = new setGridBagConstraints(0, 13, 1.0, 1.0, new Insets(2, 2, 2, 2), "both");
        gbc = myGbc.getGbc();

        submit = new JButton("ADD PRODUCT"); // Create button.
        submit.addActionListener(this); // Add action listener.
        submit.setForeground(GUIConstant.white1); // Set text color.
        submit.setBackground(GUIConstant.black); // Set background.
        submit.setFont(Text); // Set font.
        submit.setBorder(new CompoundBorder(new EmptyBorder(20, 0, 20, 0), new LineBorder(GUIConstant.white1, 1))); // Set border.
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set cursor.
        panel1.add(submit, gbc); // Add to panel.
    }

// Constructor for updating an existing product
public ProductPopUp(String title, User user, ProductPage pd, Product product) {
    this.pd = pd;
    this.user = user;
    this.product = product;
    this.update = true; // Set update flag to true for updating.

    final Font TitleText = FontConstant.PlexMonoBold.deriveFont(24f);
    final Font Text = FontConstant.PlexMonoMedium.deriveFont(12f);

    setTitle(title);
    setResizable(false);
    setVisible(true);
    setSize(320, 520);
    setIconImage(AppIcon.getImage());
    setAlwaysOnTop(true);
    setLocationRelativeTo(null);

    panel0.setBackground(GUIConstant.black);
    panel0.setLayout(new BorderLayout());
    panel0.setBorder(new EmptyBorder(20, 15, 20, 15));
    add(panel0);

    label0.setText("UPDATE PRODUCT"); // Set title for update mode.
    label0.setForeground(GUIConstant.white1);
    label0.setFont(TitleText);
    panel0.add(label0, BorderLayout.NORTH);

    panel1.setLayout(new GridBagLayout());
    panel1.setBackground(GUIConstant.black);
    panel0.add(panel1, BorderLayout.CENTER);

    setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, 1.0, 1.0, new Insets(2, 2, 0, 2) , "horizontal");
    GridBagConstraints gbc = myGbc.getGbc();

    label2.setText("NAME");
    label2.setForeground(GUIConstant.white1);
    label2.setFont(Text);
    panel1.add(label2, gbc);

    myGbc = new setGridBagConstraints(0, 1, 2.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
    gbc = myGbc.getGbc();

    textField1.setFont(Text);
    textField1.setText(product.getName()); // Populate with existing data.
    textField1.setBackground(GUIConstant.black);
    textField1.setForeground(GUIConstant.white1);
    textField1.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
    textField1.setMargin(new Insets(2, 5, 2, 5));
    panel1.add(textField1, gbc);

    myGbc = new setGridBagConstraints(0, 2, 2.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
    gbc = myGbc.getGbc();

    label3.setText("BRAND");
    label3.setForeground(GUIConstant.white1);
    label3.setFont(Text);
    panel1.add(label3, gbc);

    myGbc = new setGridBagConstraints(0, 3, 2.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
    gbc = myGbc.getGbc();

    textField2.setFont(Text);
    textField2.setText(product.getBrand()); // Populate with existing data.
    textField2.setBackground(GUIConstant.black);
    textField2.setForeground(GUIConstant.white1);
    textField2.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
    textField2.setMargin(new Insets(2, 5, 2, 5));
    panel1.add(textField2, gbc);

    myGbc = new setGridBagConstraints(0, 4, 2.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
    gbc = myGbc.getGbc();

    label4.setText("DESCRIPTION");
    label4.setForeground(GUIConstant.white1);
    label4.setFont(Text);
    panel1.add(label4, gbc);

    myGbc = new setGridBagConstraints(0, 5, 2.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
    gbc = myGbc.getGbc();

    textField3.setFont(Text);
    textField3.setText(product.getDescription()); // Populate with existing data.
    textField3.setBackground(GUIConstant.black);
    textField3.setForeground(GUIConstant.white1);
    textField3.setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
    textField3.setMargin(new Insets(2, 5, 2, 5));
    panel1.add(textField3, gbc);

    myGbc = new setGridBagConstraints(0, 6, 1.0, 1.0, new Insets(2, 2, 0, 2), "horizontal");
    gbc = myGbc.getGbc();

    panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
    panel2.setBackground(GUIConstant.black);
    panel1.add(panel2, gbc);

    panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
    panel3.setBackground(GUIConstant.black);
    panel2.add(panel3);

    label5.setText("MODAL PRICE");
    label5.setHorizontalAlignment(JLabel.LEFT);
    label5.setFont(Text);
    label5.setForeground(GUIConstant.white1);
    panel3.add(label5);

    panel2.add(Box.createHorizontalStrut(10));

    panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
    panel4.setBackground(GUIConstant.black);
    panel2.add(panel4);

    label6.setText("RETAIL PRICE");
    label6.setHorizontalAlignment(JLabel.LEFT);
    label6.setFont(Text);
    label6.setForeground(GUIConstant.white1);
    panel4.add(label6);

    c.declarePriceField(2);

    c.priceField[0].setFont(Text);
    c.priceField[0].setValue(product.getModalPrice()); // Populate with existing data.
    c.priceField[0].setBackground(null);
    c.priceField[0].setForeground(GUIConstant.white1);
    c.priceField[0].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
    c.priceField[0].setMargin(new Insets(2, 5, 2, 5));
    panel3.add(c.priceField[0]);

    c.priceField[1].setFont(Text);
    c.priceField[1].setValue(product.getRetailPrice()); // Populate with existing data.
    c.priceField[1].setBounds(0, 0, 100, 24);
    c.priceField[1].setBackground(null);
    c.priceField[1].setForeground(GUIConstant.white1);
    c.priceField[1].setBorder(new CompoundBorder(new LineBorder(GUIConstant.white1, 1), new EmptyBorder(2, 5, 2, 5)));
    panel4.add(c.priceField[1]);

    myGbc = new setGridBagConstraints(0, 7, 1.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
    gbc = myGbc.getGbc();

    label7.setText("CATEGORY");
    label7.setForeground(GUIConstant.white1);
    label7.setFont(Text);
    panel1.add(label7, gbc);

    myGbc = new setGridBagConstraints(0, 8, 1.0, 1.0, new Insets(2, 0, 2, 0), "horizontal");
    gbc = myGbc.getGbc();

    dropdown.setBorder(new CompoundBorder(new EmptyBorder(2, 5, 2, 5), new LineBorder(GUIConstant.white1, 1)));
    dropdown.setBackground(GUIConstant.black);
    dropdown.setForeground(GUIConstant.white1);
    panel1.add(dropdown, gbc);

    Select sl = new Select();
    try{
        String query = "SELECT * FROM category WHERE `userID` = '" + user.getUserID() + "';"; // Fetch categories for the current user.

        sl.Select(query);
        ResultSet rs = sl.result;

        while(rs.next()){
            dropdown.addItem(rs.getString("Name"));
        }
    } catch (Exception e){
        e.printStackTrace();
    } finally {
        CloseDB.closeConnection(sl.getConnection());
    }

    dropdown.setSelectedItem(product.getCategory()); // Select the existing category.

    myGbc = new setGridBagConstraints(0, 9, 1.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
    gbc = myGbc.getGbc();

    label8.setText("QUANTITY");
    label8.setForeground(GUIConstant.white1);
    label8.setFont(Text);
    panel1.add(label8, gbc);

    c.declareIntegerField(1);

    myGbc = new setGridBagConstraints(0, 10, 1.0, 1.0, new Insets(2, 2, 2, 2), "horizontal");
    gbc = myGbc.getGbc();

    c.integerField[0].setFont(Text);
    c.integerField[0].setBackground(GUIConstant.black);
    c.integerField[0].setForeground(GUIConstant.white1);
    c.integerField[0].setBorder(new CompoundBorder(new EmptyBorder(2, 5, 2, 5), new LineBorder(GUIConstant.white1, 1)));
    c.integerField[0].setValue(product.getQuantity()); // Populate with existing data.
    panel1.add(c.integerField[0], gbc);

    myGbc = new setGridBagConstraints(0, 13, 1.0, 1.0, new Insets(2, 2, 2, 2), "both");
    gbc = myGbc.getGbc();

    submit = new JButton("UPDATE"); // Set button text for update mode.
    submit.addActionListener(this);
    submit.setForeground(GUIConstant.white1);
    submit.setBackground(GUIConstant.black);
    submit.setFont(Text);
    submit.setBorder(new CompoundBorder(new EmptyBorder(20, 0, 20, 0), new LineBorder(GUIConstant.white1, 1)));
    submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    panel1.add(submit, gbc);
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            if(update){ // Handle update logic.
                if(!textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && c.priceField[0].getValue() != null && c.priceField[1].getValue() != null && c.integerField[0].getValue() != null) {
                    Double modalPrice = (Double) c.priceField[0].getValue();
                    Double retailPrice = (Double) c.priceField[1].getValue();
                    Integer quantity = (Integer) c.integerField[0].getValue();
                    if((modalPrice > 0) && (retailPrice > 0) && (quantity >= 0) && (retailPrice >= modalPrice)) {
                        Select sc = new Select();
                        Insert insert = new Insert();
                        Update update = new Update();
                        try {
                            String query = "SELECT CURDATE();"; // Get current date.
                            sc.Select(query);

                            ResultSet rs = sc.result;

                            Date curDate = null;
                            if (rs != null && rs.next()) {
                                curDate = rs.getDate(1);
                            } else {
                                System.out.println("No date retrieved");
                            }

                            query = "SELECT `categoryID`, `Name` FROM `category` WHERE `userID` = '" + user.getUserID() + "' AND `Name` = '" + dropdown.getSelectedItem() + "';";
                            sc.Select(query);

                            ResultSet selectRs = sc.result;

                            String categoryID = "";
                            if (selectRs.next()) {
                                categoryID = selectRs.getString("categoryID");
                            }

                            query = "UPDATE `product` SET Name = '" + textField1.getText() + "', Brand = '" + textField2.getText() + "', Description = '" + textField3.getText() + "', Modal = " + c.priceField[0].getValue() + ", retailPrice = " + c.priceField[1].getValue() + ", categoryID = '" + categoryID + "', Quantity = " + integerField[0].getValue() + " WHERE `productID` = '" + product.getProductID() + "' AND `userID` = '" + user.getUserID() + "';";
                            update.Update(query);

                            int updateRs = update.result;
                            if (updateRs == 1) {
                                System.out.println("Updated Successfully");
                                pd.refreshTable(); // Refresh the product table.
                                dispose(); // Close the pop-up.
                            }

                            // Record transaction history for quantity changes.
                            String action = "";
                            if (((int) integerField[0].getValue()) != product.getQuantity()) {
                                if ((((int) integerField[0].getValue()) - product.getQuantity()) < 0) {
                                    action = "'minus'";
                                } else {
                                    action = "'add'";
                                }

                                query = "INSERT INTO transaction(`productID`, `Action`, `Quantity`, `userID`, `date`)VALUES('" + product.getProductID() + "', " + action + ", " + (Math.abs((int) integerField[0].getValue() - (int) product.getQuantity())) + ", '" + user.getUserID() + "', '" + curDate + "');";
                                insert.Insert(query);

                                int insertRs = insert.result;
                                if (insertRs == 1) {
                                    System.out.println("Inserted Successfully");
                                } else {
                                    new Alert("Alert  Update Failed", "UPDATE FAILED!\nPLEASE TRY AGAIN");
                                }
                            }
                        } catch (NullPointerException ex) {
                            new Alert("ALERT  UPDATE ERROR", "A field is empty");
                            ex.printStackTrace();
                        } catch (Exception error) {
                            System.err.println(error);
                        } finally {
                            CloseDB.closeConnection(sc.getConnection());
                            CloseDB.closeConnection(update.getConnection());
                            CloseDB.closeConnection(insert.getConnection());
                        }
                    } else {
                        new Alert("ALERT  UPDATE ERROR", "Please key in a valid number!");
                    }
                } else {
                    new Alert("ALERT  UPDATE ERROR", "Please fill in all the field!");
                }
            } else { // Handle add logic.
                if(!textField0.getText().isEmpty() && !textField1.getText().isEmpty() && !textField2.getText().isEmpty() && !textField3.getText().isEmpty() && c.priceField[0].getValue() != null && c.priceField[1].getValue() != null && c.integerField[0].getValue() != null) {
                    Double modalPrice = (Double) c.priceField[0].getValue();
                    Double retailPrice = (Double) c.priceField[1].getValue();
                    Integer quantity = (Integer) c.integerField[0].getValue();
                    if((modalPrice > 0) && (retailPrice > 0) && (quantity >= 0) && (retailPrice >= modalPrice)) {
                        Select select = new Select();
                        boolean match = false;

                        String query = "SELECT `productID` FROM `product` WHERE `productID` = '" + textField0.getText() + "' AND `userID` = '" + user.getUserID() + "';"; // Check for duplicate product ID for the user.
                        try {
                            select.Select(query);

                            ResultSet rs = select.result;

                            if (rs.next()) {
                                match = true;
                            }
                        } catch (Exception error){
                            error.printStackTrace();
                        }

                        if(!match) {
                            Insert insert = new Insert();
                            Update update = new Update();
                            try {
                                query = "SELECT CURDATE();"; // Get current date.
                                select.Select(query);

                                ResultSet rs = select.result;

                                Date curDate = null;
                                if (rs != null && rs.next()) {
                                    curDate = rs.getDate(1);
                                } else {
                                    System.out.println("No date retrieved");
                                }

                                query = "SELECT `categoryID`, `Name` FROM `category` WHERE `userID` = '" + user.getUserID() + "' AND `Name` = '" + dropdown.getSelectedItem() + "';";
                                select.Select(query);

                                ResultSet selectRs = select.result;

                                String categoryID = "";

                                if (selectRs.next()) {
                                    categoryID = selectRs.getString("categoryID");
                                }


                                double profit = ((double) c.priceField[1].getValue() - (double) c.priceField[0].getValue());
                                query = "INSERT INTO product(`productID`, `Name`, `Brand`, `Description`, `Modal`, `retailPrice`, `Profit`, `categoryID`, `dateAdded`, `userID`, `Quantity`)VALUES('" + textField0.getText() + "', '" + textField1.getText() + "', '" + textField2.getText() + "', '" + textField3.getText() + "', " + c.priceField[0].getValue() + ", " + c.priceField[1].getValue() + ", " + profit + ", '" + categoryID + "', '" + curDate + "', '" + user.getUserID() + "', " + c.integerField[0].getValue() + ");";
                                insert.Insert(query);

                                int insertRs = insert.result;
                                if (insertRs == 1) {

                                    System.out.println("Inserted Successfully");

                                }

                                query = "INSERT INTO transaction(`productID`, `Action`, `Quantity`, `userID`, `date`)VALUES('" + textField0.getText() + "', 'add', " + (int) integerField[0].getValue() + ", '" + user.getUserID() + "', '" + curDate + "');";
                                insert.Insert(query);

                                insertRs = insert.result;
                                if (insertRs == 1) {
                                    System.out.println("Inserted Successfully");
                                }

                                query = "SELECT `itemNumber` FROM `user` WHERE `userID` = '" + user.getUserID() + "';";
                                select.Select(query);

                                int productNumber = 0;
                                rs = select.result;
                                if (rs.next()) {
                                    productNumber = rs.getInt(1);
                                }

                                query = "UPDATE `user` SET `itemNumber` = " + (++productNumber) + " WHERE `userID` = '" + user.getUserID() + "';";
                                update.Update(query);
                                int updateRs = update.result;

                                if (updateRs == 1) {
                                    System.out.println("Updated Successfully");
                                    pd.refreshTable();
                                    dispose();
                                } else {
                                    new Alert("Alert Insertion Failed", "INSERTION FAILED!\nPLEASE TRY AGAIN");
                                }
                            } catch (NullPointerException ex) {
                                new Alert("ALERT INSERTION ERROR", "A field is empty");
                                ex.printStackTrace();
                            } catch (Exception error) {
                                System.err.println(error);
                            } finally {
                                CloseDB.closeConnection(select.getConnection());
                                CloseDB.closeConnection(update.getConnection());
                                CloseDB.closeConnection(insert.getConnection());
                            }
                        } else {
                            new Alert("ALERT INSERTION ERROR", "The product ID is duplicated!\nPLease key in a new one!");
                        }
                    } else {
                        new Alert("ALERT INSERTION ERROR", "Please key in valid number!");
                    }
                } else {
                    new Alert("ALERT INSERTION ERROR", "Please fill in all the field!");
                }
            }
        }
    }
}