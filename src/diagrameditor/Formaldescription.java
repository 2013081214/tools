package diagrameditor;

import java.awt.EventQueue;
import diagrameditor.Diagrammain;
import xmlfactory.Featuredatamodel;
import xmlfactory.*;

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
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
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
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Formaldescription extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_moduleName;
	private JTextField textField_const;
	JFileChooser fileChooser = new JFileChooser();
	private JTextField textField_featureName;
	private JTextField textField_types;
	private JTextField textField_processName;
	private JTextField textField_inputs;
	private JTextField textField_outputs;
	private JTextField textField_pre;
	private JTextField textField_decompositonModule;
	private JTextField textField;
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
	public Formaldescription() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Formal\u63CF\u8FF0");
		setBounds(100, 100, 813, 592);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(204, 255, 255));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JLabel lblFeaturename = new JLabel("Feature name");
		lblFeaturename.setBounds(73, 13, 96, 18);
		desktopPane.add(lblFeaturename);
		
		JLabel lblModulename = new JLabel("Modulename");
		lblModulename.setBounds(73, 61, 96, 18);
		desktopPane.add(lblModulename);
		
		JLabel lblConsts = new JLabel("Consts");
		lblConsts.setBounds(73, 105, 80, 18);
		desktopPane.add(lblConsts);
		
		textField_moduleName = new JTextField();
		textField_moduleName.setColumns(10);
		textField_moduleName.setBounds(212, 58, 132, 24);
		desktopPane.add(textField_moduleName);
		
		JTextArea textAreaPost = new JTextArea();
		textAreaPost.setBounds(474, 331, 309, 93);
		desktopPane.add(textAreaPost);
		
		JTextArea textAreaConst = new JTextArea();
		textAreaConst.setBounds(187, 103, 159, 65);
		desktopPane.add(textAreaConst);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(470, 253, 159, 65);
		desktopPane.add(textArea_1);
		
		JButton btnNewButton = new JButton("\u4FDD\u5B58");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDataToformalFile(new File("D:\\model.formal"));
			}
			//导出到xml文件中
			private void saveDataToformalFile(File file) {
				try{	
					Document doc = DocumentHelper.createDocument();
					
					Element root = doc.addElement("featureFormalDes");
					
					//String featureName = listFeaturename.getSelectItem.toString();
					String featureName = textField_featureName.getText();
					Element fName = root.addElement("feature").addAttribute("name", featureName);
					
					String Modulename = textField_moduleName.getText();
					Element moduleName = fName.addElement("module").addAttribute("name", Modulename);
					
					//moduleName.setText(Modulename);
					
					Element consts = fName.addElement("consts");
					String Consts = textAreaConst.getText();
					//splitString(Consts);
					consts.setText(Consts);
					
					Element types = fName.addElement("types");
					String Types = textField_types.getText();
					types.setText(Types);
					
					String Processname = textField_processName.getText();
					Element processName = fName.addElement("process").addAttribute("name", Processname);;
					
					//processName.setText(Processname);
					
					Element inputs = processName.addElement("inputs");
					String.valueOf(textField_inputs.getText());
					/*
					 * inputs输入使用";"判断输入，将输入的数据进行分割
					 */
					/*if(textField_inputs.getText().equals(";")) {
						
					}*/
					//Element input = inputs.addElement("input");
					String Inputs = textField_inputs.getText();
					inputs.setText(Inputs);
					
					//Element inputvar = inputs.addElement("input").addAttribute("var", arg1);
					
					//Element inputtype = inputs.addElement("input").addAttribute("type", arg1);
					
					
					
				
					Element outputs = processName.addElement("outputs");
					String Outputs = textField_outputs.getText();
					outputs.setText(Outputs);
					
					Element pre = processName.addElement("pre");
					String Pre = textField_pre.getText();
					pre.setText(Pre);
					
					Element post = processName.addElement("post");
					String Post = textAreaPost.getText();
					post.setText(Post);
					
					Element preProcess =processName.addElement("preProcess");
					
					Element postProcess = processName.addElement("postProcess");
					
					String Decompositonmodulename = textField_decompositonModule.getText();
					Element decompositonmodulename = processName.addElement("decompositonmodule").addAttribute("name", Decompositonmodulename);				
					//decompositonmodulename.setText(Decompositonmodulename);
					
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
		btnNewButton.setBounds(73, 475, 113, 27);
		desktopPane.add(btnNewButton);
		
		
		Featuredatamodel featuredatamodel = new Featuredatamodel();		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Featuredatamodel();
		//System.out.print(featurenameList.size());
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		
		textField_featureName = new JTextField();
		textField_featureName.setBounds(212, 13,132, 24);
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
		lblProcessname.setBounds(371, 13,112, 18);
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
		textField_processName.setBounds(474, 13,132, 24);
		desktopPane.add(textField_processName);
		
		textField_inputs = new JTextField();
		textField_inputs.setColumns(10);
		textField_inputs.setBounds(494, 161, 221, 24);
		desktopPane.add(textField_inputs);
		
		textField_outputs = new JTextField();
		textField_outputs.setColumns(10);
		textField_outputs.setBounds(494, 221, 221, 24);
		desktopPane.add(textField_outputs);
		
		textField_pre = new JTextField();
		textField_pre.setColumns(10);
		textField_pre.setBounds(494, 278, 221, 24);
		desktopPane.add(textField_pre);
		
		JLabel lblDecompostionmodulename = new JLabel("DecompostionModulename");
		lblDecompostionmodulename.setBounds(73, 431, 181, 18);
		desktopPane.add(lblDecompostionmodulename);
		
		JButton btnNewButton_add = new JButton("\u6DFB\u52A0Module");
		btnNewButton_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String file = "D:\\model.formal";
				//SAXReader是读取XML文件的核心类，用于将XML解析后以树的形式保存在内存中。
				SAXReader reader = new SAXReader();
				/*
				 * 加一个判断，如果xml文件中没有描述当前节点则添加
				 */
				String featureNamenow = textField_featureName.getText();
				//if()
				try {
					//SAXReader类的主要方法是read()方法，返回一个Document对象。
					Document documentnew = reader.read(file);
					
					Element rootnew = documentnew.getRootElement();
					String featureNamenew = textField_featureName.getText();
					Element fName1 = rootnew.addElement("featurename").addAttribute("name", featureNamenew);
					
					
					String Modulename = textField_moduleName.getText();
					Element moduleName = fName1.addElement("module").addAttribute("name", Modulename);
					
					//moduleName.setText(Modulename);
					
					Element consts = fName1.addElement("consts");
					String Consts = textAreaConst.getText();
					consts.setText(Consts);
					
					Element types = fName1.addElement("types");
					String Types = textField_types.getText();
					types.setText(Types);
					
					String Processname1 = textField_processName.getText();
					Element processName1 = fName1.addElement("process").addAttribute("name", Processname1);;
					
					//processName.setText(Processname);
					
					Element inputs = processName1.addElement("inputs");
					//Element input = inputs.addElement("input");
					String Inputs1 = textField_inputs.getText();
					inputs.setText(Inputs1);
					
					//Element inputvar = inputs.addElement("input").addAttribute("var", arg1);
					
					//Element inputtype = inputs.addElement("input").addAttribute("type", arg1);
						
					Element outputs = processName1.addElement("outputs");
					String Outputs = textField_outputs.getText();
					outputs.setText(Outputs);
					
					Element pre = processName1.addElement("pre");
					String Pre = textField_pre.getText();
					pre.setText(Pre);
					
					Element post = processName1.addElement("post");
					
					String Post = textAreaPost.getText();
					post.setText(Post);
					
					String Decompositonmodulename = textField_decompositonModule.getText();
					Element decompositonmodulename = processName1.addElement("decompositonmodule").addAttribute("name", Decompositonmodulename);	
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
					//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
					writer.write(documentnew);
					writer.close();
					//XMLWriter writer1 = new XMLWriter(new FileOutputStream(file),format);
					//writer1.write(doc);
					
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_add.setBounds(298, 475, 113, 27);
		desktopPane.add(btnNewButton_add);

		
		
		
		textField_decompositonModule = new JTextField();
		textField_decompositonModule.setColumns(10);
		textField_decompositonModule.setBounds(299, 428, 112, 24);
		desktopPane.add(textField_decompositonModule);
		
		JButton btnNewButton_1 = new JButton("\u6DFB\u52A0Process");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String file = "D:\\model.formal";
				//SAXReader是读取XML文件的核心类，用于将XML解析后以树的形式保存在内存中。
				SAXReader reader = new SAXReader();
				/*
				 * 加一个判断，如果xml文件中没有描述当前节点则添加
				 */
				String featureNamenow = textField_featureName.getText();
				//if()
				try {
					//SAXReader类的主要方法是read()方法，返回一个Document对象。
					Document documentnew = reader.read(file);
					
					Element rootnew = documentnew.getRootElement();
					//String featureNamenew = textField_featureName.getText();
					//Element fName1 = rootnew.addElement("featurename").addAttribute("name", featureNamenew);
					/*
					 * 还需要调整
					 */
					Element processName = rootnew.addElement("process");
					String Processname1 = textField_processName.getText();
					Element processName1 = processName.addElement("process").addAttribute("name", Processname1);;
					
					//processName.setText(Processname);
					
					Element inputs = processName1.addElement("inputs");
					//Element input = inputs.addElement("input");
					String Inputs1 = textField_inputs.getText();
					inputs.setText(Inputs1);				
						
					Element outputs = processName1.addElement("outputs");
					String Outputs = textField_outputs.getText();
					outputs.setText(Outputs);
					
					Element pre = processName1.addElement("pre");
					String Pre = textField_pre.getText();
					pre.setText(Pre);
					
					Element post = processName1.addElement("post");
					String Post = textAreaPost.getText();
					post.setText(Post);
					
					String Decompositonmodulename = textField_decompositonModule.getText();
					Element decompositonmodulename = processName1.addElement("decompositonmodule").addAttribute("name", Decompositonmodulename);	
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
					//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
					writer.write(documentnew);
					writer.close();
					//XMLWriter writer1 = new XMLWriter(new FileOutputStream(file),format);
					//writer1.write(doc);
					
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(494, 475, 135, 27);
		desktopPane.add(btnNewButton_1);
		
		JLabel lblVar = new JLabel("var");
		lblVar.setBounds(73, 333, 96, 18);
		desktopPane.add(lblVar);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(212, 330, 112, 24);
		desktopPane.add(textField);
		

		

		//textField_featureName.set494, 381tFeaturename.getSelectedValue().toString());
		
		
		
	}
}
