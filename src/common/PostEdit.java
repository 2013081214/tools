package common;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class PostEdit extends JInternalFrame {
	private JTable table;
	private Vector<Object> vHead;
	DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PostEdit frame = new PostEdit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PostEdit() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 216);
		getContentPane().add(scrollPane);
		
		table = getTable();
		scrollPane.setViewportView(table);
		
		JButton add = new JButton("Add");
		add.setBounds(74, 224, 113, 27);
		getContentPane().add(add);
		
		JButton delete = new JButton("Delete");
		delete.setBounds(250, 224, 113, 27);
		getContentPane().add(delete);

	}
	
	private JTable getTable() {
		vHead=new Vector<Object>();
		vHead.add("Guard Conditon");
		vHead.add("Defining Conditon");
		
		dtm = new DefaultTableModel(null, vHead);
		JTable table=new JTable(dtm) {
			public boolean isCellEditable(int row, int column)
            {
                return true;//表格不允许被编辑
            }
		};
		return table;
	}
}
