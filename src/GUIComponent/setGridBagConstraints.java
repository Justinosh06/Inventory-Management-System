package GUIComponent;

import java.awt.*;

// The setGridBagConstraints class is a utility class to simplify the creation and configuration
// of GridBagConstraints objects for use with GridBagLayout.
public class setGridBagConstraints {
    private GridBagConstraints gbc = new GridBagConstraints(); // The GridBagConstraints object to be configured.

    // Constructor for the setGridBagConstraints class.
    // @param gridx The gridx coordinate of the component.
    // @param gridy The gridy coordinate of the component.
    // @param weightx The weightx value for the component's horizontal distribution.
    // @param weighty The weighty value for the component's vertical distribution.
    // @param inset The Insets object defining the external padding of the component.
    // @param fill A String specifying how the component should be resized if its display area is larger than its requested size ("both", "vertical", "horizontal", "none").
    public setGridBagConstraints(int gridx, int gridy, double weightx, double weighty, Insets inset, String fill) {
        gbc = new GridBagConstraints(); // Initialize the GridBagConstraints object.
        gbc.gridx = gridx; // Set the gridx coordinate.
        gbc.weightx = weightx; // Set the horizontal weight.
        gbc.gridy = gridy; // Set the gridy coordinate.
        gbc.weighty = weighty; // Set the vertical weight.
        gbc.insets = inset; // Set the insets (padding).

        // Determine the fill constraint based on the provided String.
        switch (fill) {
            case "both" -> gbc.fill = GridBagConstraints.BOTH; // Resize the component both horizontally and vertically.
            case "vertical" -> gbc.fill = GridBagConstraints.VERTICAL; // Resize the component vertically.
            case "horizontal" -> gbc.fill = GridBagConstraints.HORIZONTAL; // Resize the component horizontally.
            case "none" -> gbc.fill = GridBagConstraints.NONE; // Do not resize the component.
            default -> gbc.fill = GridBagConstraints.NONE; // Default to NONE if an invalid fill string is provided.
        }
    }

    // Returns the configured GridBagConstraints object.
    // @return The GridBagConstraints object.
    public GridBagConstraints getGbc() {
        return gbc;
    }
}