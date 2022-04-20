package common;


import java.util.Date;
 
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
 
/**
 * 
 * @author __USER__
 */
public class TableButton3 extends javax.swing.JFrame {
 
	public TableButton3() {
		String[] columnNames = { "Date ", "String ", "Integer ", "Number ", " " };
		Object[][] data = {
				{ new Date(), "A ", new Integer(1), new Double(5.1), "Delete0 " },
				{ new Date(), "B ", new Integer(2), new Double(6.2), "Delete1 " },
				{ new Date(), "C ", new Integer(3), new Double(7.3), "Delete2 " },
				{ new Date(), "D ", new Integer(4), new Double(8.4), "Delete3 " } };
 
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
 
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add( scrollPane );
		ButtonColumn buttonsColumn = new ButtonColumn(table, 4);
	}
 
	public static void main(String[] args) {
		TableButton3 frame = new TableButton3();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
 
	
}

