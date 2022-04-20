package diagrameditor;

/*import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDesktopPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;

public class Semiformaldescription extends JInternalFrame {

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Semiformaldescription frame = new Semiformaldescription();
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
/*	public Semiformaldescription() {
		setClosable(true);
		setBounds(100, 100, 617, 386);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(224, 255, 255));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("Semiformaldes") {
					{
						DefaultMutableTreeNode node_1;
						DefaultMutableTreeNode node_2;
						DefaultMutableTreeNode node_3;
						DefaultMutableTreeNode node_4;
						node_1 = new DefaultMutableTreeNode("module");
							node_1.add(new DefaultMutableTreeNode("name"));
							node_2 = new DefaultMutableTreeNode("consts");
								node_2.add(new DefaultMutableTreeNode("const"));
							node_1.add(node_2);
							node_2 = new DefaultMutableTreeNode("types");
								node_2.add(new DefaultMutableTreeNode("type"));
							node_1.add(node_2);
							node_2 = new DefaultMutableTreeNode("process");
								node_2.add(new DefaultMutableTreeNode("name"));
								node_3 = new DefaultMutableTreeNode("inputs");
									node_3.add(new DefaultMutableTreeNode("input"));
								node_2.add(node_3);
								node_3 = new DefaultMutableTreeNode("outputs");
									node_3.add(new DefaultMutableTreeNode("output"));
								node_2.add(node_3);
								node_3 = new DefaultMutableTreeNode("decompostion");
									node_4 = new DefaultMutableTreeNode("module");
										node_4.add(new DefaultMutableTreeNode("name"));
									node_3.add(node_4);
								node_2.add(node_3);
								node_2.add(new DefaultMutableTreeNode("pre"));
								node_2.add(new DefaultMutableTreeNode("post"));
							node_1.add(node_2);
						add(node_1);
					}
				}
			));
		tree.setBounds(14, 13, 287, 324);
		desktopPane.add(tree);

	}
}
*/


import java.awt.EventQueue;
import diagrameditor.Diagrammain;
import xmlfactory.Featuredatamodel;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;


public class Semiformaldescription extends JInternalFrame {
	private JTextField textField_moduleName;
	private JTextField textField_const;
	JFileChooser fileChooser = new JFileChooser();
	private JTextField textField_featureName;
	private JTextField textField_types;
	private JTextField textField_processName;
	private JTextField textField_inputs;
	private JTextField textField_outputs;
	private JTextField textField_pre;
	private JTextField textField_post;
	private JTextField textField_decompositonModule;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Informaldescription frame = new Informaldescription();
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
	public Semiformaldescription() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Semiformal\u63CF\u8FF0");
		setBounds(100, 100, 766, 565);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(240, 255, 255));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JLabel lblFeaturename = new JLabel("Feature name");
		lblFeaturename.setBounds(73, 99, 96, 18);
		desktopPane.add(lblFeaturename);
		
		JLabel lblModulename = new JLabel("Modulename");
		lblModulename.setBounds(73, 164, 96, 18);
		desktopPane.add(lblModulename);
		
		JLabel lblConsts = new JLabel("Consts");
		lblConsts.setBounds(73, 224, 80, 18);
		desktopPane.add(lblConsts);
		
		textField_moduleName = new JTextField();
		textField_moduleName.setColumns(10);
		textField_moduleName.setBounds(212, 161, 112, 24);
		desktopPane.add(textField_moduleName);
		
		textField_const = new JTextField();
		textField_const.setColumns(10);
		textField_const.setBounds(212, 221, 112, 24);
		desktopPane.add(textField_const);
		
		JButton btnNewButton = new JButton("\u4FDD\u5B58");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDataTosemiformalFile(new File("D:\\model.semiformal"));
				//saveDataToFile(textField_database.getText(),textField_operation.getText(),textField_constraint.getText(),"model.informal");
			}
			//导出到xml文件中
			private void saveDataTosemiformalFile(File file) {
				try{	
					Document doc = DocumentHelper.createDocument();
					
					Element root = doc.addElement("featureSemiformaldes");
					
					//String featureName = listFeaturename.getSelectItem.toString();
					String featureName = textField_featureName.getText();
					Element fName = root.addElement("featurename").addAttribute("name", featureName);
					
					Element moduleName = fName.addElement("modulename");
					String Modulename = textField_moduleName.getText();
					moduleName.setText(Modulename);
					
					Element consts = fName.addElement("consts");
					String Consts = textField_const.getText();
					consts.setText(Consts);
					
					Element types = fName.addElement("types");
					String Types = textField_types.getText();
					types.setText(Types);
					
					String Processname = textField_processName.getText();
					Element processName = fName.addElement("processname").addAttribute("name", Processname);;
					
					//processName.setText(Processname);
					
					Element inputs = processName.addElement("inputs");
					String Inputs = textField_inputs.getText();
					inputs.setText(Inputs);
				
					Element outputs = processName.addElement("outputs");
					String Outputs = textField_outputs.getText();
					outputs.setText(Outputs);
					
					Element pre = processName.addElement("pre");
					String Pre = textField_pre.getText();
					pre.setText(Pre);
					
					Element post = processName.addElement("post");
					String Post = textField_post.getText();
					post.setText(Post);
					
					Element decompositonmodulename = processName.addElement("decompositonmodulename");
					String Decompositonmodulename = textField_decompositonModule.getText();
					decompositonmodulename.setText(Decompositonmodulename);
					
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

			/*private void saveDataToFile(String databse,String operation,String constraint, String fileName) {
				fileChooser.setSelectedFile(new File(fileName));
				fileChooser.showSaveDialog(null);
				
				String filePath = fileChooser.getSelectedFile().toString();
				try {
					FileWriter writer = new FileWriter(filePath);
					writer.append(databse);
					writer.append(operation);
					writer.append(constraint);
					writer.flush();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}*/
		});
		btnNewButton.setBounds(73, 451, 113, 27);
		desktopPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(337, 32, 100, 37);
		desktopPane.add(scrollPane);
		
		
		Featuredatamodel featuredatamodel = new Featuredatamodel();		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Featuredatamodel();
		//System.out.print(featurenameList.size());
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		JList listFeaturename = new JList(v);
		listFeaturename.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int i = listFeaturename.getSelectedIndex();
				textField_featureName.setText(i>=0?(String) v.get(i):"");
			}
		});

		
		//JList listFeaturename = new JList((Vector) null);
		scrollPane.setViewportView(listFeaturename);
		listFeaturename.setValueIsAdjusting(true);
		listFeaturename.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFeaturename.setFont(new Font("宋体", Font.PLAIN, 22));
		
		textField_featureName = new JTextField();
		textField_featureName.setBounds(212, 96, 112, 24);
		desktopPane.add(textField_featureName);
		textField_featureName.setColumns(10);
		
		JLabel lblPrecondition = new JLabel("pre");
		lblPrecondition.setBounds(371, 281, 96, 18);
		desktopPane.add(lblPrecondition);
		
		JLabel lblPostcondition = new JLabel("post");
		lblPostcondition.setBounds(371, 333, 96, 18);
		desktopPane.add(lblPostcondition);
		
		JLabel lblTypes = new JLabel("Types");
		lblTypes.setBounds(73, 281, 96, 18);
		desktopPane.add(lblTypes);
		
		JLabel lblProcessname = new JLabel("Processname");
		lblProcessname.setBounds(371, 99, 96, 18);
		desktopPane.add(lblProcessname);
		
		JLabel lblInputs = new JLabel("Inputs");
		lblInputs.setBounds(371, 164, 96, 18);
		desktopPane.add(lblInputs);
		
		JLabel lblOutputs = new JLabel("Outputs");
		lblOutputs.setBounds(371, 224, 96, 18);
		desktopPane.add(lblOutputs);
		
		textField_types = new JTextField();
		textField_types.setColumns(10);
		textField_types.setBounds(212, 278, 112, 24);
		desktopPane.add(textField_types);
		
		textField_processName = new JTextField();
		textField_processName.setColumns(10);
		textField_processName.setBounds(494, 96, 112, 24);
		desktopPane.add(textField_processName);
		
		textField_inputs = new JTextField();
		textField_inputs.setColumns(10);
		textField_inputs.setBounds(494, 161, 112, 24);
		desktopPane.add(textField_inputs);
		
		textField_outputs = new JTextField();
		textField_outputs.setColumns(10);
		textField_outputs.setBounds(494, 221, 112, 24);
		desktopPane.add(textField_outputs);
		
		textField_pre = new JTextField();
		textField_pre.setColumns(10);
		textField_pre.setBounds(494, 278, 112, 24);
		desktopPane.add(textField_pre);
	
		textField_post = new JTextField();
		textField_post.setColumns(10);
		textField_post.setBounds(494, 322, 112, 24);
		desktopPane.add(textField_post);
		
		JLabel lblDecompostionmodulename = new JLabel("DecompostionModulename");
		lblDecompostionmodulename.setBounds(292, 384, 181, 18);
		desktopPane.add(lblDecompostionmodulename);
		
		
		textField_decompositonModule = new JTextField();
		textField_decompositonModule.setColumns(10);
		textField_decompositonModule.setBounds(494, 381, 112, 24);
		desktopPane.add(textField_decompositonModule);
		
		JButton btnNewButton_1 = new JButton("\u6DFB\u52A0process");
		btnNewButton_1.setText("\u6DFB\u52A0Process");
		btnNewButton_1.setBounds(461, 451, 145, 27);
		desktopPane.add(btnNewButton_1);
		//textField_featureName.set494, 381tFeaturename.getSelectedValue().toString());

	}
}
