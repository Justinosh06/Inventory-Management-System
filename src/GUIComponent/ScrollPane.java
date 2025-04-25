package GUIComponent;

import Constant.GUIConstant;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

// The ScrollPane class is a custom extension of JScrollPane with customized styling for the scrollbar.
public class ScrollPane extends JScrollPane {
    // Constructor for the ScrollPane class.
    // @param viewport The JComponent to be displayed within the scrollable pane.
    ScrollPane(JComponent viewport){
        super(viewport); // Call the constructor of the superclass (JScrollPane).

        setBackground(GUIConstant.white1); // Set the background color of the ScrollPane.


        JScrollBar verticalScrollBar = this.getVerticalScrollBar(); // Get the vertical scrollbar.
        verticalScrollBar.setBackground(GUIConstant.black); // Set the background color of the vertical scrollbar.
        verticalScrollBar.setUnitIncrement(12); // Set the scrolling increment when using arrow keys or small steps.

        // Set a custom UI for the vertical scrollbar to customize its appearance.
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            private final int SCROLLBAR_WIDTH = 0; // Set the width of the scrollbar to 0 to hide it.

            // Configures the colors of the scrollbar thumb and track.
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GUIConstant.black; // Set the color of the scrollbar thumb.
                this.trackColor = GUIConstant.white1; // Set the color of the scrollbar track.
            }

            // Creates the decrease button (arrow) for the scrollbar and hides it.
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton(); // Use a method to create a zero-sized button.
            }

            // Creates the increase button (arrow) for the scrollbar and hides it.
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton(); // Use a method to create a zero-sized button.
            }

            // Creates a zero-sized button to effectively hide the scrollbar buttons.
            private JButton createZeroButton() {
                JButton button = new JButton();
                Dimension zeroDim = new Dimension(0, 0); // Create a dimension with zero width and height.
                button.setPreferredSize(zeroDim); // Set the preferred size to zero.
                button.setMinimumSize(zeroDim); // Set the minimum size to zero.
                button.setMaximumSize(zeroDim); // Set the maximum size to zero.
                return button;
            }

            // Overrides the getPreferredSize method to set the preferred width of the scrollbar to 0.
            @Override
            public Dimension getPreferredSize(JComponent c) {
                Dimension dim = super.getPreferredSize(c); // Get the default preferred size.
                dim.width = SCROLLBAR_WIDTH; // Set the width to the defined SCROLLBAR_WIDTH (0).
                return dim;
            }
        });

    }
}