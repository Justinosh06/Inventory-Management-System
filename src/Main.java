import javax.swing.SwingUtilities;

import Pages.*;

public class Main { // Declare Main class inherits Pages.Login class
    public static void main(String[] args) { // Main Method
        SwingUtilities.invokeLater(() -> { // Allows to safely call methods in swing classes
            Login loginWindow = new Login(); // Create a Pages.Login object with name of loginWindow
        });
    }
}