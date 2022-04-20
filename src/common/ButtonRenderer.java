package common;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton)value;
        button.setText("before");
        return button;
    }
}

