package diagrameditor;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import common.ButtonColumn;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

public class DesFormal extends JInternalFrame {
	JTextField textFieldFeatureName;
	JTextField textFieldModuleName;
	JTextField textFieldDecomModule;
	JTextArea textAreaConsts;
	JTextArea textAreaTypes;
	JTextArea textAreaVar;
	JTable table;
	private Vector<Object> vHead;
	DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesFormal frame = new DesFormal();
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
	public DesFormal() {
		try {
			setSelected(true);
		} catch (PropertyVetoException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			setClosed(true);
		} catch (PropertyVetoException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(UIManager.getColor("controlHighlight"));
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Feature name");
		lblNewLabel.setBounds(14, 13, 122, 18);
		getContentPane().add(lblNewLabel);
		
		textFieldFeatureName = new JTextField();
		textFieldFeatureName.setBounds(170, 10, 204, 24);
		getContentPane().add(textFieldFeatureName);
		textFieldFeatureName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Module name");
		lblNewLabel_1.setBounds(14, 58, 122, 18);
		getContentPane().add(lblNewLabel_1);
		
		textFieldModuleName = new JTextField();
		textFieldModuleName.setBounds(170, 55, 204, 24);
		getContentPane().add(textFieldModuleName);
		textFieldModuleName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("consts");
		lblNewLabel_2.setBounds(64, 138, 72, 18);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("types");
		lblNewLabel_3.setBounds(433, 13, 72, 18);
		getContentPane().add(lblNewLabel_3);
		
		textAreaTypes = new JTextArea();
		textAreaTypes.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaTypes.setBounds(496, 12, 204, 91);
		getContentPane().add(textAreaTypes);
		
		JLabel lblNewLabel_4 = new JLabel("var");
		lblNewLabel_4.setBounds(433, 116, 72, 18);
		getContentPane().add(lblNewLabel_4);
		
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(496, 115, 204, 94);
		getContentPane().add(scrollPane_2);
		
		textAreaVar = new JTextArea();
		scrollPane_2.setViewportView(textAreaVar);
		textAreaVar.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaVar.setLineWrap(true);
		textAreaVar.setWrapStyleWord(true);
		/*
		textAreaVar = new JTextArea();
		textAreaVar.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaVar.setBounds(496, 115, 204, 94);
		getContentPane().add(textAreaVar);
		*/
		JLabel lblNewLabel_5 = new JLabel("Decom module name");
		lblNewLabel_5.setBounds(14, 203, 143, 18);
		getContentPane().add(lblNewLabel_5);
		
		textFieldDecomModule = new JTextField();
		textFieldDecomModule.setBounds(170, 200, 204, 24);
		getContentPane().add(textFieldDecomModule);
		textFieldDecomModule.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(table.getSelectedRow());
			}
		});
		scrollPane.setBounds(14, 278, 689, 201);
		getContentPane().add(scrollPane);
		
		//table = new JTable();
		table = getTable();
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		
		JButton btnNewButton = new JButton("save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveDataToformalFile(new File("D:\\model.formal"));
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void saveDataToformalFile(File file) throws DocumentException {
				try{
					SAXReader saxReader=new SAXReader(); 
					Document doc=saxReader.read("D:\\model.formal");
					//Document doc = DocumentHelper.createDocument();
					
					//Element root = doc.addElement("featureFormalDes");
					Element root = doc.getRootElement();
					String featureName = textFieldFeatureName.getText();
					for(Iterator i = root.elementIterator();i.hasNext();) {
						Element childEle = (Element) i.next();
						String fName = childEle.attributeValue("name");
						if(fName.equals(featureName)) {
							root.remove(childEle);
						}else {
							
						}
					}
					Element fName = root.addElement("feature").addAttribute("name", featureName);
					
					String Modulename = textFieldModuleName.getText();
					Element moduleName = fName.addElement("module").addAttribute("name", Modulename);
					
					Element consts = fName.addElement("consts");
					String Consts = textAreaConsts.getText();
					consts.setText(Consts);
					
					Element types = fName.addElement("types");
					String Types = textAreaTypes.getText();
					types.setText(Types);
					
					Element vars = fName.addElement("vars");
					String Vars = textAreaVar.getText();
					vars.setText(Vars);
					
					int num = table.getRowCount();
					for(int i = 0; i < num; i++) {
						String Processname = table.getValueAt(i, 0).toString();
						Element processName = fName.addElement("process").addAttribute("name", Processname);
						
						Element inputs = processName.addElement("inputs");
						inputs.setText(table.getValueAt(i, 1).toString());
				
						Element outputs = processName.addElement("outputs");
						outputs.setText(table.getValueAt(i, 2).toString());
						
						Element pre = processName.addElement("pre");
						if(table.getValueAt(i, 3)!=null) {
							String Pre = table.getValueAt(i, 3).toString();
							pre.setText(Pre);
						}else {
							String Pre = "";
							pre.setText(Pre);
						}

						
						Element post = processName.addElement("post");
						String Post = table.getValueAt(i, 4).toString();
						String[] postStrArray = Post.split(";");
						for(int k = 0 ; k< postStrArray.length;k++) {
							Element sen = post.addElement("scenarios");
							Element C = sen.addElement("C");
							String c = postStrArray[k];
							C.setText(c);
							k++;
							Element D = sen.addElement("D");
							String d = postStrArray[k];
							D.setText(d);
						}
					}
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
					writer.write(doc);
					writer.close();
					}catch (UnsupportedEncodingException e) {
			            e.printStackTrace();
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				
			}
		});
		btnNewButton.setBounds(594, 492, 113, 27);
		getContentPane().add(btnNewButton);
		
		
		/*table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"ProcessName", "inputs", "outputs", "Pre", "Post"
			}
		));*/
		
		JButton btnNewButton_1 = new JButton("Add Process");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Vector v = new Vector();
				
				dtm.addRow(v);		
			}
		});
		btnNewButton_1.setBounds(14, 247, 143, 27);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_6 = new JLabel("Separated by ; when entering post conditions");
		lblNewLabel_6.setBounds(14, 481, 445, 18);
		getContentPane().add(lblNewLabel_6);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(170, 101, 204, 86);
		getContentPane().add(scrollPane_1);
		
		textAreaConsts = new JTextArea();
		scrollPane_1.setViewportView(textAreaConsts);
		textAreaConsts.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaConsts.setLineWrap(true);
		textAreaConsts.setWrapStyleWord(true);
		
		JButton btnNewButton_2 = new JButton("Delete Process");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1) {
                	dtm.removeRow(selectedRow);
                }
			}
		});
		btnNewButton_2.setBounds(164, 247, 153, 27);
		getContentPane().add(btnNewButton_2);
		table.getColumnModel().getColumn(0).setPreferredWidth(118);
		table.getColumnModel().getColumn(4).setPreferredWidth(344);
		
		setTitle("FormalDes");
		setBounds(100, 100, 735, 574);

	}
	private JTable getTable() {
		vHead=new Vector<Object>();
		vHead.add("ProcessName");
		vHead.add("Inputs");
		vHead.add("Outputs");
		vHead.add("Pre");
		vHead.add("Post");
		
		dtm=new DefaultTableModel(null, vHead);
		JTable table=new JTable(dtm);
		ButtonColumn buttonsColumn = new ButtonColumn(table, 4);
		return table;
	}
}
