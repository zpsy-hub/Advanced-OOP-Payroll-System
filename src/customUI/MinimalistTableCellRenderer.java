package customUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.formdev.flatlaf.FlatLightLaf;

public class MinimalistTableCellRenderer extends DefaultTableCellRenderer {
    private static final int ROW_HEIGHT_PADDING = 4; // Additional pixels for row height

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Set custom background color
        if (isSelected) {
            c.setBackground(new Color(220, 240, 255)); // Selected cell background
        } else {
            c.setBackground(Color.WHITE); // Normal cell background
        }
        // Set custom font and center align text
        c.setFont(new Font("Poppins", Font.PLAIN, 14));
        ((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
        // Set row height
        table.setRowHeight(calculateRowHeight(table.getRowHeight(), ROW_HEIGHT_PADDING));
        // Remove border
        if (c instanceof JComponent) {
            ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        return c;
    }

    private int calculateRowHeight(int baseHeight, int padding) {
        return baseHeight + padding;
    }

    public static void applyMinimalistLook(JTable table) {
        // Set the custom cell renderer
        table.setDefaultRenderer(Object.class, new MinimalistTableCellRenderer());

        // Customize the table header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Poppins", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Horizontal line
        header.setDefaultRenderer(new HeaderRenderer(table.getTableHeader().getDefaultRenderer()));

        // Remove grid lines
        table.setShowGrid(false);
        table.setIntercellSpacing(new java.awt.Dimension(0, 0));

        // Auto-resize mode off to manage column widths manually
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Calculate and set preferred column widths
        TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = columnModel.getColumn(column);
            int preferredWidth = calculatePreferredColumnWidth(table, column);
            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    private static int calculatePreferredColumnWidth(JTable table, int columnIndex) {
        int maxWidth = 0;
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if (headerRenderer == null) {
            headerRenderer = table.getTableHeader().getDefaultRenderer();
        }

        Component headerComp = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, columnIndex);
        int headerWidth = headerComp.getPreferredSize().width;
        maxWidth = Math.max(maxWidth, headerWidth);

        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer cellRenderer = table.getCellRenderer(row, columnIndex);
            Component cellComp = table.prepareRenderer(cellRenderer, row, columnIndex);
            int cellWidth = cellComp.getPreferredSize().width;
            maxWidth = Math.max(maxWidth, cellWidth);
        }

        // Add padding
        maxWidth += 2 * table.getIntercellSpacing().width;

        return maxWidth;
    }

    private static class HeaderRenderer implements TableCellRenderer {
        private final TableCellRenderer delegate;

        public HeaderRenderer(TableCellRenderer delegate) {
            this.delegate = delegate;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setFont(new Font("Poppins", Font.BOLD, 14));
            c.setBackground(new Color(240, 240, 240));
            c.setForeground(Color.BLACK);
            if (c instanceof JComponent) {
                ((JComponent) c).setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Horizontal line
            }
            return c;
        }
    }
}
