package GUIComponent;

import javax.swing.*;

// The GUI abstract class serves as a base class for all graphical user interface (GUI) windows in the application.
// It extends JFrame and provides default settings for all windows.
public abstract class GUI extends JFrame {
    // Constructor for the GUI class.
    // @param title The title to be displayed in the window's title bar.
    public GUI(String title) {
        setTitle(title); // Set the title of the JFrame.
        setSize(1080, 675); // Set the initial size of the window to 1080 pixels wide and 675 pixels high.
        setResizable(false); // Prevent the user from resizing the window.
        setVisible(true); // Make the window visible to the user.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation to exit the application when the window is closed.
        setIconImage(Icon.AppIcon.getImage()); // Set the application icon for the window using the AppIcon loaded by the Icon class.
    }
}