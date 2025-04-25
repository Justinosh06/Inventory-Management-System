package GUIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// The Icon class manages the loading and scaling of image icons used throughout the application.
// It provides static instances of these icons for easy access.
public class Icon {
    public static ImageIcon AppIcon; // Stores the original application icon.
    public static ImageIcon AlertIcon; // Stores the original alert icon.
    public static ImageIcon scaledHomeIcon; // Stores a scaled version of the alert icon (used as home icon).
    public static ImageIcon scaledAppIcon; // Stores a scaled version of the application icon.

    static final int iconWidth = 25; // The desired width for scaled icons.
    static final int iconHeight = 25; // The desired height for scaled icons.

    // Static initializer block: This code runs once when the class is loaded.
    static {
        try {
            String appIconPath = "Img/APPICON.png"; // Path to the application icon image file.
            String alertIconPath = "Img/Icon/alert.png"; // Path to the alert icon image file.

            AppIcon = new ImageIcon(appIconPath); // Load the application icon.
            AlertIcon = new ImageIcon(alertIconPath); // Load the alert icon.

            // Scale the application icon if it was loaded successfully.
            if (AppIcon.getImage() != null) {
                scaledAppIcon = new ImageIcon(getScaledImage(AppIcon.getImage(), iconWidth, iconHeight));
            } else {
                System.err.println("Error loading AppIcon: " + appIconPath);
            }

            // Scale the alert icon (used as home icon) if it was loaded successfully.
            if (AlertIcon.getImage() != null) {
                scaledHomeIcon = new ImageIcon(getScaledImage(AlertIcon.getImage(), iconWidth, iconHeight));
            } else {
                System.err.println("Error loading HomeIcon: " + alertIconPath);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions that occur during icon loading.
        }
    }

    // Helper method to scale an image to a specified width and height while maintaining aspect ratio.
    // @param srcImg The original Image object to be scaled.
    // @param w The desired width of the scaled image.
    // @param h The desired height of the scaled image.
    // @return A BufferedImage object representing the scaled image.
    public static BufferedImage getScaledImage(Image srcImg, int w, int h) {
        int originalWidth = srcImg.getWidth(null); // Get the original width of the image.
        int originalHeight = srcImg.getHeight(null); // Get the original height of the image.

        // Calculate the scaling ratios for both width and height.
        double scaleWidth = (double) w / originalWidth;
        double scaleHeight = (double) h / originalHeight;
        // Use the smaller scale factor to maintain aspect ratio and fit within the bounds.
        double scale = Math.min(scaleWidth, scaleHeight);

        // Calculate the new scaled width and height.
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);

        // Create a BufferedImage with the scaled dimensions and alpha channel for transparency.
        BufferedImage resizedImg = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics(); // Get the Graphics2D object for drawing.

        // Set rendering hints for smoother image scaling.
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        // Draw the original image onto the scaled BufferedImage.
        g2.drawImage(srcImg, 0, 0, scaledWidth, scaledHeight, null);
        g2.dispose(); // Release the Graphics2D resources.

        return resizedImg; // Return the scaled BufferedImage.
    }
}