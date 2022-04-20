package diagrameditor;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;


public class Informaldescription extends JInternalFrame {
	private JTextField textField_database;
	private JTextField textField_function;
	private JTextField textField_constraint;
	JFileChooser fileChooser = new JFileChooser();
	private JTextField textField_featureName;
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
	public Informaldescription() {
		setMaximizable(true);
		setClosable(true);
		setIconifiable(true);
		setTitle("Informal\u63CF\u8FF0");
		setBounds(100, 100, 665, 445);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(240, 255, 255));
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Feature name");
		lblNewLabel.setBounds(137, 99, 96, 18);
		desktopPane.add(lblNewLabel);
		
		JLabel lblDatabase = new JLabel("Database");
		lblDatabase.setBounds(158, 149, 72, 18);
		desktopPane.add(lblDatabase);
		
		JLabel lblFuction = new JLabel("Operation");
		lblFuction.setBounds(158, 203, 72, 18);
		desktopPane.add(lblFuction);
		
		JLabel lblConstraint = new JLabel("Constraint");
		lblConstraint.setBounds(153, 253, 80, 18);
		desktopPane.add(lblConstraint);
		
		textField_database = new JTextField();
		textField_database.setColumns(10);
		textField_database.setBounds(341, 146, 112, 24);
		desktopPane.add(textField_database);
		
		textField_function = new JTextField();
		textField_function.setColumns(10);
		textField_function.setBounds(341, 200, 112, 24);
		desktopPane.add(textField_function);
		
		textField_constraint = new JTextField();
		textField_constraint.setColumns(10);
		textField_constraint.setBounds(341, 250, 112, 24);
		desktopPane.add(textField_constraint);
		/*
		 * 添加监听器点击保存按钮时将文本框中的数据以XML格式存入model.informal文件中
		 */
		JButton btnNewButton = new JButton("\u4FDD\u5B58");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDataToinformalFile(new File("D:\\model.informal"));
				//saveDataToFile(textField_database.getText(),textField_operation.getText(),textField_constraint.getText(),"model.informal");
			}
			//将数据导出到xml文件中
			private void saveDataToinformalFile(File file) {
				try{	
					Document doc = DocumentHelper.createDocument();
					
					Element root = doc.addElement("featureInformaldes");
					
					//String featureName = listFeaturename.getSelectItem.toString();
					String featureName = textField_featureName.getText();
					//String featureNamechange = featureName;
					Element fName = root.addElement("featurename").addAttribute("name", featureName);
					
					Element function = fName.addElement("function");
					String Function = textField_function.getText();
					function.setText(Function);
					
					Element db = fName.addElement("database");
					String Databse = textField_database.getText();
					db.setText(Databse);
					
					Element constraint = fName.addElement("constraint");
					String Constraint = textField_constraint.getText();
					constraint.setText(Constraint);
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
					writer.write(doc);
					writer.close();
					
					//监听featurename控件中文字变化向XML添加数据
					/*
					textField_featureName.getDocument().addDocumentListener(new DocumentListener()
					 {
						public void removeUpdate(DocumentEvent e) {
							
						}
						
						public void changedUpdate(DocumentEvent e) {
							String file = "D:\\model.informal";
							//SAXReader是读取XML文件的核心类，用于将XML解析后以树的形式保存在内存中。
							SAXReader reader = new SAXReader();
							try {
								//SAXReader类的主要方法是read()方法，返回一个Document对象。
								Document documentnew = reader.read(file);
								
								Element rootnew = documentnew.getRootElement();
								
								Element fName1 = rootnew.addElement("featurename").addAttribute("name", featureName);
								
								Element opreat1 = fName.addElement("opreation");
								String Opreation1 = textField_operation.getText();
								opreat1.setText(Opreation1);
								
								Element db1 = fName.addElement("database");
								String Databse1 = textField_database.getText();
								db1.setText(Databse1);
								
								Element constraint1 = fName.addElement("constraint");
								String Constraint1 = textField_constraint.getText();
								constraint1.setText(Constraint1);
								
								XMLWriter writer1 = new XMLWriter(new FileOutputStream(file),format);
								writer1.write(doc);
								writer1.close();
							}catch(Exception e1) {
								e1.printStackTrace();
							}
						}
						@Override
						public void insertUpdate(DocumentEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});*/
					/*if(textField_featureName.getText() != featureNamechange) {
						String filenew = "D:\\model.informal";
						//SAXReader是读取XML文件的核心类，用于将XML解析后以树的形式保存在内存中。
						SAXReader reader = new SAXReader();
						try {
							//SAXReader类的主要方法是read()方法，返回一个Document对象。
							Document documentnew = reader.read(filenew);
							
							Element rootnew = documentnew.getRootElement();
							
							Element fName1 = root.addElement("featurename").addAttribute("name", featureName);
							
							Element opreat1 = fName.addElement("opreation");
							String Opreation1 = textField_operation.getText();
							opreat1.setText(Opreation1);
							
							Element db1 = fName.addElement("database");
							String Databse1 = textField_database.getText();
							db1.setText(Databse1);
							
							Element constraint1 = fName.addElement("constraint");
							String Constraint1 = textField_constraint.getText();
							constraint1.setText(Constraint1);
							
							XMLWriter writer1 = new XMLWriter(new FileOutputStream(file),format);
							writer1.write(doc);
							writer1.close();
						}catch(Exception e) {
							e.printStackTrace();
						}
						
						
					}*/
					}catch (UnsupportedEncodingException e) {
			            e.printStackTrace();
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				
			    }
				
			/*
			 * 将数据存入txt文档
			 */
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
		btnNewButton.setBounds(137, 306, 113, 27);
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
				textField_featureName.setText(i>=0?(String) v.get(i):"");//将JList的值传给textField_featureName
			}
		});

		
		//JList listFeaturename = new JList((Vector) null);
		scrollPane.setViewportView(listFeaturename);
		listFeaturename.setValueIsAdjusting(true);
		listFeaturename.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listFeaturename.setFont(new Font("宋体", Font.PLAIN, 22));
		
		textField_featureName = new JTextField();
		textField_featureName.setBounds(341, 96, 112, 24);
		desktopPane.add(textField_featureName);
		textField_featureName.setColumns(10);
		
		JButton btnNewButton_add = new JButton("\u6DFB\u52A0");
		btnNewButton_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String file = "D:\\model.informal";
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
					
					Element function1 = fName1.addElement("function");
					String Function1 = textField_function.getText();
					function1.setText(Function1);
					
					Element db1 = fName1.addElement("database");
					String Databse1 = textField_database.getText();
					db1.setText(Databse1);
					
					Element constraint1 = fName1.addElement("constraint");
					String Constraint1 = textField_constraint.getText();
					constraint1.setText(Constraint1);
					
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
		/*btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new addElementtoxml();
			}
			public void addElementtoxml() {
				String file = "D:\\model.informal";
				//SAXReader是读取XML文件的核心类，用于将XML解析后以树的形式保存在内存中。
				SAXReader reader = new SAXReader();
				try {
					//SAXReader类的主要方法是read()方法，返回一个Document对象。
					Document documentnew = reader.read(file);
						
					Element rootnew = documentnew.getRootElement();
					
					String featureName = textField_featureName.getText();
					Element fName1 = rootnew.addElement("featurename").addAttribute("name", featureName);
						
					Element opreat1 = fName1.addElement("opreation");
					String Opreation1 = textField_operation.getText();
					opreat1.setText(Opreation1);
						
					Element db1 = fName1.addElement("database");
					String Databse1 = textField_database.getText();
					db1.setText(Databse1);
					
					Element constraint1 = fName1.addElement("constraint");
					String Constraint1 = textField_constraint.getText();
					constraint1.setText(Constraint1);
						
					Writer writer1 = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
					documentnew.write(writer1);
					writer1.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});*/
		btnNewButton_add.setBounds(341, 306, 113, 27);
		desktopPane.add(btnNewButton_add);
		//textField_featureName.setText(listFeaturename.getSelectedValue().toString());

	}
}
