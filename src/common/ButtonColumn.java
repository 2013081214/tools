package common;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
 
public class ButtonColumn extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor, ActionListener {
	JTable table;
	JButton renderButton;
	JButton editButton;
	String text;
	private Vector<Object> vHead;
	DefaultTableModel dtm;
 
	public ButtonColumn(JTable table, int column) {
		super();
		this.table = table;
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);
 
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
	}
 
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		} else if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}
 
		renderButton.setText((value == null) ? " " : value.toString());
		return renderButton;
	}
 
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		text = (value == null) ? " " : value.toString();
		editButton.setText(text);
		return editButton;
	}
 
	public Object getCellEditorValue() {
		return text;
	}
 
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow();
		if(table.getValueAt(row, 0).toString() != null) {
			String processName = table.getValueAt(row, 0).toString();
		}
		
		String post = e.getActionCommand();
		System.out.println(post);
		String[] postArray = post.split(";");
		JTable jt = getTable();
		
		for(int j = 0; j < postArray.length-1; j++) {
			if(postArray[j] != null) {
				Vector v = new Vector();
				v.add(postArray[j]);
				System.out.println(postArray[j]);
				j++;
				if(postArray[j] != null) {
					System.out.println(postArray[j]);
					v.add(postArray[j]);
				}
				dtm.addRow(v);
			}
		}
		
		//String featureName = textFieldModuleName.getText();
		//fireEditingStopped();
		JDialog jdl = new JDialog();//弹出对话框
		JPanel panel = new JPanel();
		//public JDialog(Frame frame, String title,  boolean model);
        jdl.setSize(470, 520);//对话框大小
        jdl.setLocation(900, 400);
        jdl.setTitle("PostEdit");
        
        //jdl.setLayout(new BoxLayout(jdl, BoxLayout.Y_AXIS));
        //String info = firstFeature+" "+constraintType+" "+secondFeature+" has been add";

        JScrollPane scrollPane_2 = new JScrollPane();
        //jdl.getContentPane().add(scrollPane_2);
		scrollPane_2.setViewportView(jt);
		JButton addBtn = new JButton("add");
		addBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector v = new Vector();				
				dtm.addRow(v);		
			}
		});
	    JButton deleteBtn = new JButton("delete");
	    deleteBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = jt.getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1) {
                	dtm.removeRow(selectedRow);
                }
			}
		});
	    JButton confirmBtn = new JButton("confirm");
	    confirmBtn.addMouseListener(new MouseAdapter(){
	    	public void mouseClicked(MouseEvent e) {
				int num = jt.getRowCount();
				String newPost = "";
				for(int i = 0; i < num; i++) {					
					newPost += jt.getValueAt(i, 0).toString();
					newPost += ";";
					newPost += jt.getValueAt(i, 1).toString();
					newPost += ";";
				}
				text = newPost;
			}
	    });
	    panel.add(scrollPane_2);
	    panel.add(addBtn);
	    panel.add(deleteBtn);
	    panel.add(confirmBtn);
	    jdl.add(panel);
	    //jdl.getContentPane().add(addBtn);
	    //jdl.getContentPane().add(deleteBtn);
        jdl.setVisible(true);//可见
		//System.out.println(e.getActionCommand() + "   :    "+ table.getSelectedRow());
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