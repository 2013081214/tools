package diagrameditor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import template.addAlternativeFeature;
import template.addMultipeFeature;
import template.addOrGroupFeature;
import template.ChangeFeatureType;
import template.featureConstraint;
import xmlfactory.BuildNewProject;
import xmlfactory.MineTree;
import xmlfactory.XMLTree;
import xmlfactory.newfile;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import org.dom4j.io.SAXReader;
import org.eclipse.jface.viewers.TreeNode;
//import org.jdom.*;
//import org.jdom.input.SAXBuilder;

import check.Scenario;
import check.ScenarioLongPath;
import check.checkFunctionScenarioPath;

//import org.jdom.Document;
//import org.jdom.Element;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JMenuItem;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class DiagramSecond extends JFrame {
	String warning1 = "Paths overlap, please re-edit the range";
	
	public static String nodeChoice;
	private JPanel contentPane;
	int template = 999;
	private JTable TemplateTable;
	private DefaultMutableTreeNode top;
	Enumeration enumeration;
	Enumeration enumeration1;
	private JTable table;
	private Vector<Object> vHead;
	DefaultTableModel dtm;
	public static List<String> FormalDesTitle = new ArrayList();
	public static int index = 0;
	public static int templateNum = 0;
	public static HashMap<String,Integer> tabIndex = new HashMap<>();
	
	JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
    private Vector<Boolean> closable;
	JButton btnNewButton_1 = new JButton("Refresh");
	
	//checkFunctionScenarioPath path = new checkFunctionScenarioPath();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiagramSecond frame = new DiagramSecond();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public DiagramSecond() throws Exception {
		closeImpl();
		setBackground(new Color(220, 220, 220));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1593, 932);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(153, 180, 209));
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_2 = new JMenu("file");
		menuBar.add(mnNewMenu_2);
		
		JMenu mnNewMenu_newProject = new JMenu("new project");
		mnNewMenu_newProject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BuildNewProject newfile = new BuildNewProject();
				newfile.setModal(true);
				newfile.setVisible(true);
			}
		});
		mnNewMenu_2.add(mnNewMenu_newProject);
		
		JMenu mnNewMenu_3 = new JMenu("save");
		mnNewMenu_2.add(mnNewMenu_3);
		
		JMenu mnNewMenu_6 = new JMenu("import file");
		mnNewMenu_3.add(mnNewMenu_6);
		
		JMenu mnNewMenu_5 = new JMenu("export file");
		mnNewMenu_3.add(mnNewMenu_5);
		
		JMenu mnNewMenu = new JMenu("Conflict Detection");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_7 = new JMenu("formal check");
		mnNewMenu_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dtm.setRowCount(0);
				List list = new ArrayList();
				//checkFunctionScenarioPath.CheckScope(GetSingleFeatureScenarioPath(GetSinglePath()));
				list = checkFunctionScenarioPath.CheckScope(checkFunctionScenarioPath.GetSingleFeatureScenarioPath(checkFunctionScenarioPath.GetSinglePath()));
				for(int i = 0; i < list.size();i++) {
					Vector data = new Vector();
					data.add(list.get(i));
					i++;
					data.add(list.get(i));
					i++;
					data.add(list.get(i));
					dtm.addRow(data);
				}
			}
		});
		mnNewMenu_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	
			}
		});
		mnNewMenu.add(mnNewMenu_7);
		
		JMenu mnNewMenu_4 = new JMenu("help");
		menuBar.add(mnNewMenu_4);
		
		JMenu mnNewMenu_8 = new JMenu("open help file");
		mnNewMenu_4.add(mnNewMenu_8);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane_1.getSelectedIndex();
                // 获得指定索引的选项卡标签
                String title = tabbedPane_1.getTitleAt(selectedIndex);
                System.out.println(title);              
			}
		});
		tabbedPane_1.setBounds(775, 13, 793, 637);
		tabbedPane_1.setToolTipText("FormalDes");
		tabbedPane_1.setBackground(new Color(240, 255, 240));
		contentPane.add(tabbedPane_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 13, 558, 184);
		contentPane.add(scrollPane);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(UIManager.getColor("Viewport.background"));
		scrollPane.setViewportView(desktopPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 13, 465, 156);
		desktopPane.add(scrollPane_1);
		
		TemplateTable = new JTable(){
			public boolean isCellEditable(int row, int column)
            {
                return false;//表格不允许被编辑
            }
		};
		TemplateTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//TemplateTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TemplateTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				template = TemplateTable.getSelectedRow();
				System.out.println(template);
			}
		});
		TemplateTable.setFillsViewportHeight(true);
		TemplateTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"multiple", "Splitting features into Multiple sub-features"},
				{"OrGroup", "Splitting features into OrGroup sub-features"},
				{"Alternative", "Splitting features into Alternative sub-features"},
				{"Constraint", "Describe the relationship between feature"},
				{"Change feature type", "Modify the feature type"},
			},
			new String[] {
				"Template Type", "Template Description"
			}
		));
		TemplateTable.getColumnModel().getColumn(0).setPreferredWidth(153);
		TemplateTable.getColumnModel().getColumn(1).setPreferredWidth(351);
		scrollPane_1.setViewportView(TemplateTable);
		
		

		
		JPanel panel = new JPanel();
		panel.setBounds(14, 209, 558, 637);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(UIManager.getColor("Viewport.background"));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setBounds(new Rectangle(2, 2, 2, 2));
		btnSelect.setBackground(UIManager.getColor("activeCaption"));
		btnSelect.setForeground(SystemColor.desktop);
		btnSelect.setFont(new Font("方正粗黑宋简体", Font.PLAIN, 14));
		btnSelect.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSelect.addActionListener(new ActionListener() {
			/*
			 * 判断选中的是哪个模板，随后在模板编辑区展示。
			 */
			public void actionPerformed(ActionEvent e) {
				//panel.get
				if(templateNum == 0) {
					if(template == 0 ) {
						addMultipeFeature subFeature = new addMultipeFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						subFeature.setBounds(x, y, width, height);
						subFeature.setVisible(true);
						panel.add(subFeature);
					}
					else if(template == 1) {
						addOrGroupFeature orgroup = new addOrGroupFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						orgroup.setBounds(x, y, width, height);
						orgroup.setVisible(true);
						panel.add(orgroup);				
					}
					else if(template == 2) {
						addAlternativeFeature alternative = new addAlternativeFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						alternative.setBounds(x, y, width, height);
						alternative.setVisible(true);
						panel.add(alternative);				
					}
					else if(template == 3) {
						featureConstraint constraint = new featureConstraint();
						int x=10;
						int y=30;
						int width = 450;
						int height = 200;
						constraint.setBounds(x, y, width, height);
						constraint.setVisible(true);
						panel.add(constraint);				
					}
					else if(template == 4) {
						ChangeFeatureType type = new ChangeFeatureType();
						int x=100;
						int y=100;
						int width = 420;
						int height = 280;
						type.setBounds(x, y, width, height);
						type.setVisible(true);
						panel.add(type);
					}
					templateNum++;
				}else {
					panel.removeAll();
					panel.add(btnNewButton_1);
					templateNum--;
					if(template == 0 ) {
						addMultipeFeature subFeature = new addMultipeFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						subFeature.setBounds(x, y, width, height);
						subFeature.setVisible(true);
						panel.add(subFeature);
					}
					else if(template == 1) {
						addOrGroupFeature orgroup = new addOrGroupFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						orgroup.setBounds(x, y, width, height);
						orgroup.setVisible(true);
						panel.add(orgroup);				
					}
					else if(template == 2) {
						addAlternativeFeature alternative = new addAlternativeFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						alternative.setBounds(x, y, width, height);
						alternative.setVisible(true);
						panel.add(alternative);				
					}
					else if(template == 3) {
						featureConstraint constraint = new featureConstraint();
						int x=10;
						int y=30;
						int width = 450;
						int height = 200;
						constraint.setBounds(x, y, width, height);
						constraint.setVisible(true);
						panel.add(constraint);				
					}
					else if(template == 4) {
						ChangeFeatureType type = new ChangeFeatureType();
						int x=100;
						int y=100;
						int width = 420;
						int height = 280;
						type.setBounds(x, y, width, height);
						type.setVisible(true);
						panel.add(type);
					}
					templateNum++;
				}
				
				
			}
		});
		btnSelect.setBounds(475, 13, 84, 156);
		desktopPane.add(btnSelect);
		
		DefaultMutableTreeNode rootTree;
		MineTree tree = new MineTree();
		JTree featureTree = new JTree(tree.rootTree);		
		featureTree.setEditable(false);
		featureTree.setRootVisible(true);
		
		JScrollPane scrollPane_modeltree = new JScrollPane();
		scrollPane_modeltree.setBounds(577, 13, 197, 833);
		scrollPane_modeltree.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(scrollPane_modeltree);
		

		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(UIManager.getColor("activeCaption"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//featureTree.update(getGraphics());
				DefaultMutableTreeNode rootTree;
				MineTree newTree = new MineTree();
				JTree newFeatureTree = new JTree(newTree.rootTree);						
				featureTree.setEditable(true);
				featureTree.setRootVisible(true);
				scrollPane_modeltree.setViewportView(newFeatureTree);
				newFeatureTree.setBackground(Color.WHITE);
				newFeatureTree.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) newFeatureTree.getLastSelectedPathComponent();
						if(node.isLeaf()) 
						{	
							if(FormalDesTitle.contains(node.toString()) == false) {
								tabbedPane_1.addTab(node.toString(), createTextPanel(node.toString()));
								FormalDesTitle.add(node.toString());
								tabIndex.putIfAbsent(node.toString(), index);
								index++;
							}else {
								String name = node.toString();
								if(tabIndex!=null && tabIndex.size()>0) {
						            Iterator<Entry<String, Integer>> it = tabIndex.entrySet().iterator(); //利用迭代器循环输出
						            while (it.hasNext()) {
						                Map.Entry<String,Integer> entry=it.next();
						                String field = entry.getKey().toString();
						                int x = entry.getValue();
						                if(name.equals(field)) {
						                	tabbedPane_1.setSelectedIndex(x);
						                }
						            }
						        }
							}				
						}
					}
				});
			}
		});
		btnNewButton_1.setBounds(465, 604, 93, 33);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Template");
		lblNewLabel.setBounds(0, 0, 75, 18);
		panel.add(lblNewLabel);
		

		/*
		 * JTree操作
		 */
		enumeration = tree.rootTree.postorderEnumeration();
		enumeration1 = tree.rootTree.preorderEnumeration();
		while (enumeration.hasMoreElements()) {// 遍历节点枚举对象
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();// 获得节点
			for (int l = 0; l < node.getLevel(); l++) {// 根据节点级别输出占位符
				System.out.print("----");
			}
			System.out.println(node.getUserObject());// 输出节点标签
		}
		while (enumeration1.hasMoreElements()) {// 遍历节点枚举对象
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration1.nextElement();// 获得节点
			for (int l = 0; l < node.getLevel(); l++) {// 根据节点级别输出占位符
				System.out.print("----");
			}
			System.out.println(node.getUserObject());// 输出节点标签
		}
		
		//JTree tree = new JTree();
		featureTree.getModel();
		featureTree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) featureTree.getLastSelectedPathComponent();
				if(node.isLeaf()) 
				{	
					if(FormalDesTitle.contains(node.toString()) == false) {
						tabbedPane_1.addTab(node.toString(), createTextPanel(node.toString()));
						FormalDesTitle.add(node.toString());
						tabIndex.putIfAbsent(node.toString(), index);
						index++;
					}else {
						String name = node.toString();
						if(tabIndex!=null && tabIndex.size()>0) {
				            Iterator<Entry<String, Integer>> it = tabIndex.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,Integer> entry=it.next();
				                String field = entry.getKey().toString();
				                int x = entry.getValue();
				                if(name.equals(field)) {
				                	tabbedPane_1.setSelectedIndex(x);
				                }
				            }
				        }
					}				
				}
			}
		});
		featureTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) featureTree.getLastSelectedPathComponent();
				if(node == null) {
					return;
				}
				Object object = node.getUserObject();
				//TreeNode user = (TreeNode) object;
				System.out.println("选择了：" + object);
				nodeChoice = (String) object;
			}
		});
		scrollPane_modeltree.setViewportView(featureTree);
		featureTree.setBackground(Color.WHITE);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(775, 655, 793, 191);
		contentPane.add(scrollPane_2);
		
		table = getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row=table.getSelectedRow();
				int col=table.getSelectedColumn();
				String res = table.getValueAt(row, col).toString();
				String[] signal = res.split(":");
				String father = signal[0];
				if(FormalDesTitle.contains(signal[0]) == false && col != 2) {
					tabbedPane_1.addTab(signal[0], createTextPanel(signal[0]));
					FormalDesTitle.add(signal[0]);
					tabIndex.putIfAbsent(signal[0], index);
					index++;
					//System.out.println(signal[0]);				
				}else {
					if(tabIndex!=null && tabIndex.size()>0) {
			            Iterator<Entry<String, Integer>> it = tabIndex.entrySet().iterator(); //利用迭代器循环输出
			            while (it.hasNext()) {
			                Map.Entry<String,Integer> entry=it.next();
			                String field = entry.getKey().toString();
			                int x = entry.getValue();
			                if(signal[0].equals(field)) {
			                	tabbedPane_1.setSelectedIndex(x);
			                }
			            }
			        }
				}
				if(res.equals(warning1)) {
					try{
						//获取SAXReader对象
						SAXReader reader = new SAXReader();
						//创建一个File类
						File file = new File("D:\\model.xml");
						Document doc = reader.read(file);
						//获取跟节点
						Element root = doc.getRootElement();
						List nodes = root.elements("feature");      
						for (Iterator it = nodes.iterator(); it.hasNext();) {      
						    Element elm = (Element) it.next();
						    String name = elm.attributeValue("NodeName");
						    if(name.equals(signal[0])) {
						    	Element fa = elm.getParent();
						    	father = fa.attributeValue("NodeName");
						    }
						   // do something      
						}

					}
					catch (DocumentException x) {
							x.printStackTrace();
					}
					if(templateNum == 0) {
						addMultipeFeature subFeature = new addMultipeFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						subFeature.setBounds(x, y, width, height);
						subFeature.setVisible(true);
						subFeature.list.setSelectedValue(father, true);
						panel.add(subFeature);
						templateNum++;
					}else {
						panel.removeAll();
						panel.add(btnNewButton_1);
						templateNum--;
						addMultipeFeature subFeature = new addMultipeFeature();
						int x=10;
						int y=30;
						int width = 550;
						int height = 480;
						subFeature.setBounds(x, y, width, height);
						subFeature.setVisible(true);
						subFeature.list.setSelectedValue(father, true);
						panel.add(subFeature);
						templateNum++;
					}
				}
				
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(209);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(92);
		scrollPane_2.setViewportView(table);
	}
	 public void addTab(String title,Component component) {
	        addTab(title, component);
	        this.closable.add(true);
	  }

	

	/*
	 * 初始化树形结构
	 */
	public JTree createTree(InputStream treeXml) {
		SAXReader saxReader=new SAXReader(); 
		//SAXBuilder builder = new SAXBuilder();
		try
		{
			Document doc=saxReader.read("D:\\model.formal");
			Element root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
			//Document doc = builder.build(treeXml);
			//Element root = doc.getRootElement();
			TreeNode rootNode = new TreeNode(root.attributeValue("NodeName"));
			top = new DefaultMutableTreeNode(rootNode);
			//addNode(root, top);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return new JTree(top);
	}

	public static void saveToXmlFile(DefaultMutableTreeNode rootNode, String leafType, FileWriter fw)
    {
        try {
            String space = "";
            //做自动缩进
            for(int i = rootNode.getPath().length; i>1; i --)
            {
                space = space+"    ";
            }
            if(rootNode.isLeaf()) 
                {
                    //如果是叶子节点，则直接写入文件； leaftype是我自己的需要指定叶子节点在xml中的属性，不需要的可以删除该参数；
                    fw.write(space+"<"+leafType+">\r\n");
                    String[]testCase = rootNode.toString().split("\n");
                    for(int i =0;i<testCase.length;i++)
                    {
                        fw.write(space+"    "+testCase[i]+"\r\n");
                    }
                    fw.write(space+"</"+leafType+">\r\n");
                }
            else{
                //不是叶子节点，则递归遍历；
                fw.write(space + "<"+rootNode.toString()+">\r\n");
                for(int i = 0; i<rootNode.getChildCount(); i++)
                {
                    DefaultMutableTreeNode childeNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
                    saveToXmlFile(childeNode, leafType, fw);
                }
                fw.write(space + "</"+rootNode.toString()+">\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	private JTable getTable() {
		vHead=new Vector<Object>();
		vHead.add("Issue A");
		vHead.add("Issue B");
		vHead.add("We Suggest");
		
		dtm=new DefaultTableModel(null, vHead);
		JTable table=new JTable(dtm) {
			public boolean isCellEditable(int row, int column)
            {
                return false;//表格不允许被编辑
            }
		};
		return table;
	}
	/*
	 * 
	 */
	
	
	/*
	 * 初始化tab页
	 */
	protected Component createTextPanel(String x) {
		String name = x;
		DesFormal formaldescription = new DesFormal();
		formaldescription.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		String types = null;
		String vars = null;
		String consts = null;
		String decom = null;
		String process = null;
		String input = null;
		String output = null;
		String pre = null;
		String feature = x;
	    try {
	    	SAXReader saxReader=new SAXReader(); 
			Document doc=saxReader.read("D:\\model.formal");
			Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
	        for(Iterator i = rootElm.elementIterator();i.hasNext();) {
				Element childEle = (Element) i.next();
				String featureName = childEle.attributeValue("name");
				if(featureName.equals(feature)) {
					Element Type = childEle.element("types");
					types = Type.getText();
					Element Var = childEle.element("vars");
					vars = Var.getText();
					Element Const = childEle.element("consts");
					consts = Const.getText();
					//Element Decom = childEle.element("decompositonmodule");
					//decom = Decom.attributeValue("name");							
					List nodes = childEle.elements("process");
					for (Iterator it = nodes.iterator(); it.hasNext();) {
						Vector v = new Vector();
					    Element elm = (Element) it.next();
					    process = elm.attributeValue("name");
					    Element Input = elm.element("inputs");
					    input = Input.getText();				            	
		            	Element Output = elm.element("outputs");
		            	output = Output.getText();
		            	Element Pre = elm.element("pre");				            	
		            	Element Post = elm.element("post");
		            	//获取post里的所有路径
		            	List paths = Post.elements("scenarios");
		            	v.add(process);
		            	v.add(input);
		            	v.add(output);
		            	if(Pre.getText() != null) {
		            		pre = Pre.getText();
		            		v.add(pre);
		            	}else {
		            		String T = "ture";
		            		v.add(T);
		            	}
						String post = "";
		            	for (Iterator item = paths.iterator(); item.hasNext();) {
		            		Element Scenarios = (Element) item.next();
		            		Element C = Scenarios.element("C");
		            		String Conditon = C.getText();
		            		
		            		post += Conditon;
		            		post += ";";
		            		Element D = Scenarios.element("D");
		            		String Data = D.getText();
		            		post += Data;
		            		post += ";";
		            	}
		            	v.add(post);
		            	formaldescription.dtm.addRow(v);
					}
				}											
			}						
	    } catch (Exception ex) {  
	        ex.printStackTrace();  
	    }  
		formaldescription.setTitle(x);
		formaldescription.setVisible(true);
		formaldescription.textFieldFeatureName.setText(x);
		formaldescription.textFieldModuleName.setText(x);
		formaldescription.textAreaConsts.setText(consts);
		formaldescription.textAreaTypes.setText(types);
		formaldescription.textAreaVar.setText(vars);					
		formaldescription.textFieldDecomModule.setText(decom);			
	
		return formaldescription;
	}
	
	public Boolean CheckHave(String x,ArrayList y) {
		Boolean bool = true;
		for(int i = 0; i < y.size();i++) {
			if(y.get(i).equals(x)) {
				bool = false;
			}else {
				bool = true;
				break;
			}
		}
		return bool;
	}
	public void closeImpl() {
		final JPopupMenu popupMenu = new JPopupMenu();
		final JMenuItem closeTabMenuItem = new JMenuItem("关闭");

		popupMenu.add(closeTabMenuItem);

		// 关闭当前标签
		closeTabMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer tabIndex = (Integer) closeTabMenuItem
						.getClientProperty("CloseIndex");
				if (tabIndex != null
						&& tabIndex.intValue() <= tabbedPane_1.getTabCount() - 1) {
					tabbedPane_1.remove(tabIndex.intValue());
				}
			}
		});
	}
}
