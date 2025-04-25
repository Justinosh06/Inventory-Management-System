package GUIComponent;

import Constant.GUIConstant;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;

// The Table class is a custom extension of JTable with predefined styling and functionality.
public class Table extends JTable{
    ScrollPane scrollPane; // A ScrollPane to contain the table, allowing for scrolling.

    // Constructor for the Table class.
    // @param columnNames An array of strings representing the headers for the table columns.
    // @param parent      The JPanel to which this table (within a ScrollPane) will be added.
    public Table(String[] columnNames, JPanel parent){
        // Initialize the JTable with a DefaultTableModel that prevents cell editing.
        super(new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        });

        // Create GridBagConstraints for positioning the ScrollPane in the parent panel.
        setGridBagConstraints myGbc = new setGridBagConstraints(0, 0, 1.0, 1.0, new Insets(0, 0, 2, 0), "both");
        GridBagConstraints gbc = myGbc.getGbc();
        gbc.anchor = GridBagConstraints.CENTER;

        // Create a ScrollPane and add the table to it.
        scrollPane = new ScrollPane(this);
        scrollPane.setBackground(GUIConstant.black); // Set the background color of the ScrollPane.
        scrollPane.getViewport().setBackground(GUIConstant.black); // Set the background color of the table's viewport.

        // Add the ScrollPane (containing the table) to the parent panel.
        parent.add(scrollPane, gbc);

        // Set a border for the ScrollPane.
        scrollPane.setBorder(new LineBorder(GUIConstant.white1, 1));

        // Create renderers for styling the table cells.
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        PaddedCellRenderer paddedRenderer = new PaddedCellRenderer(5, 10); // Apply padding to cell content.
        centerRenderer.setHorizontalAlignment(JLabel.LEFT); // Align cell content to the left.

        // Apply the renderers to all columns in the table.
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            getColumnModel().getColumn(i).setCellRenderer(paddedRenderer);
        }

        // Get the table header.
        JTableHeader header = getTableHeader();
        header.setBackground(GUIConstant.black); // Set header background to black.
        header.setForeground(GUIConstant.white1); // Set header text to white.
        header.setOpaque(true); // Ensure header background is visible.
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, header.getPreferredSize().height + 1)); // Increase header height slightly.
        header.setDefaultRenderer(new HeaderRenderer(5, 10)); // Use a custom renderer for the header.

        // Set the custom header for the ScrollPane's column header view.
        scrollPane.setColumnHeaderView(header);
    }

    // Adds a new row to the table with the provided column values.
    // @param column An array of strings representing the data for the new row.
    public void addRow(String[] column){
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.addRow(column);
    }

    // Adds a new column to the table with the specified header.
    // @param header The string to be used as the new column's header.
    public void addColumn(String header){
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.addColumn(header);
    }

    // Forces the table to re-render its data.
    public void fireTableDataChanged(){
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.fireTableDataChanged();
    }

    // Returns the index of the currently selected row.
    // @return The index of the selected row, or -1 if no row is selected.
    public int getSelectedRow(){
        return super.getSelectedRow();
    }

    // Sets the number of rows in the table.
    // @param num The desired number of rows.
    public void setRowCount(int num){
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.setRowCount(num);
    }

    // Sets the number of columns in the table.
    // @param num The desired number of columns.
    public void setColumnCount(int num){
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.setColumnCount(num);
    }

    // Sets the font for the table cells.
    // @param font The Font object to be used for the table cells.
    public void setTableFont(Font font){
        this.setFont(font);
    }

    // Sets the font for the table header.
    // @param font The Font object to be used for the table header.
    public void setTableHeaderFont(Font font){
        this.getTableHeader().setFont(font);
    }

    // Sets the background color of the table. Allows for transparency.
    // @param color The Color object to be used for the table background. Null makes it transparent.
    public void setTableBackground(Color color){
        if(color == null){ this.setOpaque(false); this.setBackground(color);}
        else{ this.setBackground(color);}
    }

    // Sets the foreground color (text color) of the table. Allows for transparency.
    // @param color The Color object to be used for the table foreground. Null makes it transparent.
    public void setTableForeground(Color color){
        if(color == null){ this.setOpaque(false); this.setForeground(color);}
        else{ this.setForeground(color);}
    }

    // Sets the background color of the table header.
    // @param color The Color object to be used for the table header background.
    public void setTableHeaderBackground(Color color){
        this.getTableHeader().setBackground(color);
    }

    // Sets the foreground color (text color) of the table header.
    // @param color The Color object to be used for the table header foreground.
    public void setTableHeaderForeground(Color color){
        this.getTableHeader().setForeground(color);
    }

    // Custom TableCellRenderer to add padding and a border to the table cells.
    static class PaddedCellRenderer extends DefaultTableCellRenderer {
        private int topBottomPadding;
        private int leftRightPadding;

        // Constructor for PaddedCellRenderer.
        // @param topBottomPadding The padding in pixels to be applied to the top and bottom of the cell.
        // @param leftRightPadding The padding in pixels to be applied to the left and right of the cell.
        PaddedCellRenderer(int topBottomPadding, int leftRightPadding) {
            this.topBottomPadding = topBottomPadding;
            this.leftRightPadding = leftRightPadding;
        }

        // Overrides the getTableCellRendererComponent method to customize cell rendering.
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Create a compound border with a line border and empty padding.
            CompoundBorder CBorder = new CompoundBorder(new LineBorder(GUIConstant.white1, 1), BorderFactory.createEmptyBorder(topBottomPadding, leftRightPadding, topBottomPadding, leftRightPadding));
            setHorizontalAlignment(JLabel.LEFT); // Set the alignment of the cell content to the left.
            setBorder(CBorder); // Apply the compound border to the cell.
            return this; // Return the rendered component.
        }
    }

    // Custom TableCellRenderer for the table header to add padding and styling.
    static class HeaderRenderer extends DefaultTableCellRenderer {
        private int topBottomPadding;
        private int leftRightPadding;

        // Constructor for HeaderRenderer.
        // @param topBottomPadding The padding in pixels to be applied to the top and bottom of the header cell.
        // @param leftRightPadding The padding in pixels to be applied to the left and right of the header cell.
        HeaderRenderer(int topBottomPadding, int leftRightPadding) {
            this.topBottomPadding = topBottomPadding;
            this.leftRightPadding = leftRightPadding;
        }

        // Overrides the getTableCellRendererComponent method to customize header rendering.
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(JLabel.LEFT); // Align header text to the left.
            setBackground(GUIConstant.black); // Set header background to black.
            setForeground(GUIConstant.white1); // Set header text to white.

            // Create a compound border with a line border and empty padding.
            CompoundBorder CBorder = new CompoundBorder(new LineBorder(GUIConstant.white1, 1), BorderFactory.createEmptyBorder(topBottomPadding, leftRightPadding, topBottomPadding, leftRightPadding));
            setBorder(CBorder); // Apply the compound border to the header cell.
            return this; // Return the rendered component.
        }
    }
}