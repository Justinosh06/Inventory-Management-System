package GUIComponent;

import javax.swing.border.Border;
import java.awt.*;

// The roundifyBorder class implements the Border interface to create a rounded rectangle border for Swing components.
public class roundifyBorder implements Border {
    private int radii; // The radius of the rounded corners.

    // Constructor for the roundifyBorder class.
    // @param radii The radius to be used for rounding the corners of the border.
    public roundifyBorder(int radii){
        this.radii = radii;
    }

    // Paints the rounded rectangle border on the component.
    // @param c The component whose border is being painted.
    // @param g The Graphics object used for painting.
    // @param x The x coordinate of the border's top-left corner.
    // @param y The y coordinate of the border's top-left corner.
    // @param width The width of the border.
    // @param height The height of the border.
    @Override
    public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
        // Draw a rounded rectangle using the specified coordinates, width, height, and corner radii.
        g.drawRoundRect(x, y, width-1, height-1, radii, radii);
    }

    // Returns the insets of the border. This determines the space the border occupies.
    // @param c The component whose border insets are being requested.
    // @return An Insets object representing the top, left, bottom, and right insets.
    @Override
    public Insets getBorderInsets(java.awt.Component c) {
        // The commented-out line would create insets based on the radii, effectively adding padding around the component.
        // However, the current implementation returns zero insets, meaning the border draws directly around the component's bounds.
        return new Insets(0, 0, 0, 0);
    }

    // Returns whether the border is opaque. Rounded rectangle borders are typically not opaque.
    // @return false, indicating that the border is not opaque.
    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}