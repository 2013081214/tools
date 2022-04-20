package common;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ButtonEditor extends DefaultCellEditor {
    private static final long serialVersionUID = -6546334664166791132L;

    public ButtonEditor() {
        super(new JTextField());
        this.setClickCountToStart(1);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JButton button = (JButton)value;
        button.setText("after");
        button.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("edit!!!!"); 
            }
        });
        return button;
    }
}

