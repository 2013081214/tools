package diagrameditor;

import java.awt.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import java.awt.event.InputMethodListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.event.InputMethodEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLOutputFactory;

import org.dom4j.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import xmlfactory.Featuredatamodel;
import xmlfactory.MineTree;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTree;

import diagrameditor.checkQuestionDialog;
import javax.swing.JTabbedPane;

public class Diagrammain extends JFrame {

	private JPanel contentPane;
	JDesktopPane table = new JDesktopPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Diagrammain frame = new Diagrammain();
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
	public Diagrammain() {
		JScrollPane scrollPane = new JScrollPane();
		
		setTitle("SPLCombineSOFL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1502, 995);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("\u5BFC\u5165\u7279\u5F81\u6A21\u578B");
		mnNewMenu.add(mnNewMenu_1);
		
		/*
		 * 通过菜单来item_choosefile选择文件导入项目，展示SOFL描述
		 */
		
		JMenuItem item_choosefile = new JMenuItem("\u9009\u62E9\u6587\u4EF6\u4F4D\u7F6E");
		item_choosefile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(item_choosefile.equals("open")) {
					JFileChooser jf = new JFileChooser();
					jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int val = jf.showOpenDialog(null);//打开文件选择框
					if(val == jf.APPROVE_OPTION) {
						//File fileinformal = jf.getSelectedFile();
						try {							
							File file = jf.getSelectedFile();
							String fileName = file.getName();
							String informalfile = "model.informal";
							String semiformalfile = "model.semiformal";
							String formalfile = "model.formal";
							if(fileName == informalfile) {
								SAXReader reader = new SAXReader();
								Document document = reader.read(file);
								
								Element root=document.getRootElement();
								String docXmlText = document.asXML();
								//String rootXmlText = root.asXML();

							}else if(fileName == semiformalfile) {
								SAXReader reader = new SAXReader();
								Document document = reader.read(file);
								
								Element root=document.getRootElement();
								String docXmlText = document.asXML();
								//String rootXmlText = root.asXML();
							}else if(fileName == formalfile) {
								SAXReader reader = new SAXReader();
								Document document = reader.read(file);
								
								Element root=document.getRootElement();
								String docXmlText = document.asXML();
								//String rootXmlText = root.asXML();

							}
						    /*int index = fileName.lastIndexOf('.');
						    if (index > 0 && index < fileName.length() - 1) {
					            // 表示文件名称不为".xxx"现"xxx."之类型
					            String extension = fileName.substring(index + 1).toLowerCase();
					            String informal = null;
								// 若所抓到的文件扩展名等于我们所设置要显示的扩展名(即变量ext值),则返回true,表示将此文件显示出来,否则返回
					            // true.
					            if (extension == informal) {
					            	
					            }
					                
					        }*/
							SAXReader reader = new SAXReader();
							Document document = reader.read(file);
							
							Element root=document.getRootElement();
							String docXmlText = document.asXML();
							//String rootXmlText = root.asXML();

							
						}catch(Exception e1) {
							e1.printStackTrace();
						}
					}
					/*else if(val == jf.APPROVE_OPTION && jf.getSelectedFile().equals(".semiformal")) {
						try {
							
							File fileinformal = jf.getSelectedFile();
							SAXReader reader = new SAXReader();
							Document document = reader.read(fileinformal);
							
							Element root=document.getRootElement();
							String docXmlText = document.asXML();
							String rootXmlText = root.asXML();
							textArea_semiformal.setText(docXmlText);
							
						}catch(Exception e3) {
							e3.printStackTrace();
						}
					}
					else if(val == jf.APPROVE_OPTION && jf.getSelectedFile().equals(".formal")) {
						try {
							
							File fileinformal = jf.getSelectedFile();
							SAXReader reader = new SAXReader();
							Document document = reader.read(fileinformal);
							
							Element root=document.getRootElement();
							String docXmlText = document.asXML();
							String rootXmlText = root.asXML();
							textArea_formal.setText(docXmlText);
							
						}catch(Exception e3) {
							e3.printStackTrace();
						}
					}*/
				}
			//}
		});
		mnNewMenu_1.add(item_choosefile);
		
		JMenu mnNewMenu_2 = new JMenu("\u5BFC\u51FA\u7279\u5F81\u6A21\u578B");
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u9009\u62E9\u4F4D\u7F6E");
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_3 = new JMenu("\u51B2\u7A81\u68C0\u6D4B");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mnMenuItem_check = new JMenuItem("\u68C0\u6D4B");
		mnMenuItem_check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new checkQuestionDialog(title,content).setVisible(true);
				//checkQuestionDialog dialog = new checkQuestionDialog(this);
			}
		});
		mnNewMenu_3.add(mnMenuItem_check);
		
		JMenu mnNewMenu_4 = new JMenu("\u5E2E\u52A9");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u6253\u5F00\u5E2E\u52A9\u6587\u6863");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_3);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
				
		table = new JDesktopPane();
		table.setBackground(new Color(240, 255, 255));
		
		JButton InformalButton = new JButton("Informal");
		InformalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Informaldescription informaldescription = new Informaldescription();
				informaldescription.setVisible(true);
				table.add(informaldescription);
			}
		});
		
		JButton SemiformalButton = new JButton("Semiformal");
		SemiformalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Semiformaldescription semiformaldescription = new Semiformaldescription();
				semiformaldescription.setVisible(true);
				table.add(semiformaldescription);
			}
		});
		
		JButton FormalButton = new JButton("Formal");
		FormalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Formaldescription formaldescription = new Formaldescription();
				formaldescription.setVisible(true);
				table.add(formaldescription);
			}
		});
		
		DefaultMutableTreeNode rootTree;
		MineTree tree = new MineTree();
		JTree featureTree = new JTree(tree.rootTree);
		featureTree.setEditable(true);
		featureTree.setRootVisible(true);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(283)
							.addComponent(InformalButton)
							.addGap(301)
							.addComponent(SemiformalButton)
							.addGap(225)
							.addComponent(FormalButton, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(featureTree, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
									.addGap(3))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(table, GroupLayout.PREFERRED_SIZE, 1150, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(InformalButton, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
							.addComponent(SemiformalButton, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addComponent(FormalButton, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(featureTree, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
						.addComponent(table, GroupLayout.PREFERRED_SIZE, 865, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		

		
		

		Featuredatamodel featuredatamodel = new Featuredatamodel();//从XML文件中读取所有前缀为featurename的叶子节点		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Featuredatamodel();
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		JList listFeaturename = new JList(v);
		/*listFeaturename.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					Informaldescription.textFieldfeaturename.getText() = (String)listFeaturename.getSelectedValue();
				}
				
			}
		});*/
		
		listFeaturename.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listFeaturename);
		listFeaturename.setFont(new Font("宋体", Font.PLAIN, 22));
		listFeaturename.setValueIsAdjusting(true);
		contentPane.setLayout(gl_contentPane);
		
		
	}
}
