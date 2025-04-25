package Constant;

import java.awt.*;
import java.io.File;
import java.io.IOException;

// The FontConstant class is responsible for loading and providing custom fonts for the application.
// It includes a utility method to load fonts from file paths and declares static instances of commonly used fonts.
public class FontConstant {
    // Loads a custom font from the specified file path.
    // @param fontPath The path to the TrueType Font (.ttf) file.
    // @return The loaded Font object.
    // @throws RuntimeException if the font file is not found or is in an invalid format.
    public static Font loadFont(String fontPath) {
        try {
            // Create a Font object from the specified TrueType font file.
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            // Get the local graphics environment.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // Register the custom font with the graphics environment, making it available for use.
            ge.registerFont(customFont);

            return customFont; // Return the loaded font.
        } catch (FontFormatException | IOException e) {
            // If a FontFormatException (invalid font file) or IOException (file not found) occurs,
            // wrap it in a RuntimeException for easier handling.
            throw new RuntimeException(e);
        }
    }

    // Static instances of Montserrat fonts with different weights.
    public static Font montserratThin = FontConstant.loadFont("Font/Montserrat-Thin.ttf");
    public static Font montserratSemiBold = FontConstant.loadFont("Font/Montserrat-SemiBold.ttf");
    public static Font montserratMedium = FontConstant.loadFont("Font/Montserrat-Medium.ttf");
    public static Font montserratExtraLight = FontConstant.loadFont("Font/Montserrat-ExtraLight.ttf");
    public static Font montserratLight = FontConstant.loadFont("Font/Montserrat-Light.ttf");
    public static Font montserratBold = FontConstant.loadFont("Font/Montserrat-Bold.ttf");

    // Static instances of IBM Plex Mono fonts with different weights.
    public static Font PlexMonoBold = FontConstant.loadFont("Font/IBMPlexMono-Bold.ttf");
    public static Font PlexMonoSemiBold = FontConstant.loadFont("Font/IBMPlexMono-SemiBold.ttf");
    public static Font PlexMonoMedium = FontConstant.loadFont("Font/IBMPlexMono-Medium.ttf");
    public static Font PlexMonoExtraLight = FontConstant.loadFont("Font/IBMPlexMono-ExtraLight.ttf");
    public static Font PlexMonoLight = FontConstant.loadFont("Font/IBMPlexMono-Light.ttf");
    public static Font PlexMonoThin = FontConstant.loadFont("Font/IBMPlexMono-Thin.ttf");
}