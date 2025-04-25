package GUIComponent;

import Constant.GUIConstant;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

// The Component class provides a utility for declaring and managing common Swing components
// such as labels, text fields, password fields, buttons, panels, and dropdowns.
// It also includes specific functionality for handling formatted text fields for prices and integers.
public class Component {
    public static JLabel[] label; // Array to hold JLabel components.
    public static JTextField[] textField; // Array to hold JTextField components.
    public static JPasswordField passwordField; // Instance to hold a JPasswordField component.
    public static JButton[] Button; // Array to hold JButton components.
    public static JPanel[] panel; // Array to hold JPanel components.
    public static JComboBox[] dropdown; // Array to hold JComboBox components.

    public static JFormattedTextField[] priceField; // Array to hold JFormattedTextField components for price input.
    public static JFormattedTextField[] integerField; // Array to hold JFormattedTextField components for integer input.


    // Method to declare an array of JFormattedTextFields specifically for handling price input.
    // @param num The number of price fields to declare.
    public static void declarePriceField(int num){
        priceField = new JFormattedTextField[num]; // Initialize the priceField array.

        // Define the format for displaying and parsing decimal numbers (e.g., currency).
        NumberFormat format = new DecimalFormat("#,##0.00");
        // Create a NumberFormatter based on the defined format.
        NumberFormatter formatter = new NumberFormatter(format);
        // Specify that the formatter should handle Double objects.
        formatter.setValueClass(Double.class);
        // Prevent the user from entering invalid characters.
        formatter.setAllowsInvalid(false);
        // Ensure that the value is committed to the field when a valid edit occurs.
        formatter.setCommitsOnValidEdit(true);

        // Create the specified number of JFormattedTextFields with the configured formatter.
        for (int i = 0; i < num; i++){
            priceField[i] = new JFormattedTextField(formatter);
        }
    }

    // Method to set the value of a specific price field.
    // @param index The index of the price field to set.
    // @param amount The Double value to set in the price field.
    public static void setPriceField(int index, Double amount){
        priceField[index].setValue(amount);
    }

    // Method to retrieve the Double value from a specific price field.
    // Returns 0.0 if there is an error in retrieving the value (e.g., field is empty or invalid).
    // @param index The index of the price field to get the value from.
    // @return The Double value entered in the price field, or 0.0 if an error occurs.
    public static Double getPriceFieldValue(int index){
        try{
            return (Double) priceField[index].getValue();
        }
        catch(Exception e){
            return 0.0;
        }
    }

    // Method to declare an array of JFormattedTextFields specifically for handling integer input.
    // @param num The number of integer fields to declare.
    public static void declareIntegerField(int num){
        integerField = new JFormattedTextField[num]; // Initialize the integerField array.

        // Get the default NumberFormat for integers.
        NumberFormat format = NumberFormat.getIntegerInstance();
        // Create a NumberFormatter based on the integer format.
        NumberFormatter formatter = new NumberFormatter(format);
        // Specify that the formatter should handle Integer objects.
        formatter.setValueClass(Integer.class);
        // Prevent the user from entering invalid characters.
        formatter.setAllowsInvalid(false);
        // Ensure that the value is committed to the field when a valid edit occurs.
        formatter.setCommitsOnValidEdit(true);

        // Create the specified number of JFormattedTextFields with the configured formatter.
        for(int i = 0; i < num; i++){
            integerField[i] = new JFormattedTextField(formatter);
        }
    }

    // Method to set the value of a specific integer field.
    // @param index The index of the integer field to set.
    // @param amount The integer value to set in the integer field.
    public static void setIntegerField(int index, int amount){
        integerField[index].setValue(amount);
    }

    // Method to retrieve the Integer value from a specific integer field.
    // Returns 0 if there is an error in retrieving the value (e.g., field is empty or invalid).
    // @param index The index of the integer field to get the value from.
    // @return The Integer value entered in the integer field, or 0 if an error occurs.
    public Integer getIntegerFieldValue(int index){
        try{
            return (Integer) integerField[index].getValue();
        }
        catch (Exception e) {
            return 0;
        }
    }
}