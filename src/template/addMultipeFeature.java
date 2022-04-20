package template;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import xmlfactory.Dom4jGetFeatureList;
import xmlfactory.translateXMLToPFA;

import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import contact.ContactDAO;
import common.ListObject;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

public class addMultipeFeature extends JInternalFrame {
	private JTable table;
	private Element current;
	private Vector<Object> vHead;
	private DefaultTableModel dtm;
	private ContactDAO dao;
	List<String> typeListAll = new ArrayList<>();
	List<String> nameListAll = new ArrayList<>();
    public DefaultMutableTreeNode rootTree ;
	public Vector v = new Vector();
	public JList list = new JList(v);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addMultipeFeature frame = new addMultipeFeature();
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
	public addMultipeFeature() {
		setClosable(true);
		setResizable(true);
		setTitle("Decompose Feature to Multipe Feature");
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(14, 118, 467, 201);
		getContentPane().add(scrollPane_3);
		table = getTable();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setForeground(new Color(255, 255, 255));
		scrollPane.setBounds(179, 49, 213, 31);
		getContentPane().add(scrollPane);
		
		//JList list = new JList();
		Dom4jGetFeatureList featuredatamodel = new Dom4jGetFeatureList();//从XML文件中读取所有前缀为featurename的叶子节点		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Dom4jGetMultipeFeatureList();
		
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}


		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
				    String featureSelectName = (String)list.getSelectedValue();
					//获取SAXReader对象
					SAXReader reader = new SAXReader();
					//创建一个File类
					File file = new File("D://model.xml");
					Document doc = reader.read(file);
					//获取跟节点
					Element root = doc.getRootElement();
					Vector<Vector<String>> vDateNew = getTreenodeNames(root,featureSelectName);
					//System.out.println(vDateNew);
					vHead=new Vector<Object>();
					vHead.add("name");
					vHead.add("type");
					dtm = new DefaultTableModel(vDateNew, vHead);
					table = new JTable(dtm);
					scrollPane_3.setViewportView(table);					
		        }
		    	catch (Exception ex) {
		            ex.printStackTrace();
		        }
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int i = list.getSelectedIndex();
				String featureSelect = (i>=0?(String) v.get(i):"");
				//featureSelectName = (String)list.getSelectedValue();
				System.out.println("选择了：" + featureSelect);		    	
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel = new JLabel("select feature");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(14, 49, 166, 31);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("save");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(UIManager.getColor("activeCaption"));
		btnNewButton_1.setBounds(415, 387, 93, 33);
		getContentPane().add(btnNewButton_1);
		
		JTextArea textAreaA = new JTextArea();
		textAreaA.setBounds(14, 332, 166, 24);
		getContentPane().add(textAreaA);
		
		JScrollPane scrollPane_2_1 = new JScrollPane();
		scrollPane_2_1.setBounds(256, 332, 166, 25);
		getContentPane().add(scrollPane_2_1);
		
		JList list_3 = new JList();
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setModel(new AbstractListModel() {
			String[] values = new String[] {"mandatory", "optional"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_2_1.setViewportView(list_3);
		
		/**
		 * 获取选中节点的所有下一层叶子节点名字和属性
		 */	
		/*Dom4jGetFeatureList featureLeafNodeName = new Dom4jGetFeatureList();//从XML文件中读取所有前缀为featurename的叶子节点		
		List<String> featureNameList = new ArrayList<>();
		featureNameList = featuredatamodel.Dom4jGetFeatureList();
		Vector<String> v1 = new Vector<String>();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v1.addElement(featurenameList.get(i));
		}
		String leafFeatureNameNode[] = new String[v1.size()];
		for(int i=0; i < v1.size(); i++) 
		{
			leafFeatureNameNode[i] = v1.get(i) ;
		}*/
		

		
		
		String[] columnNames = {"Feature Name","Type"};   //列名
		String [][]featureInf = {getTreeNodeInf(),getTreeNodeType()};

		
		//String [][]featureInf = {getTreeNodeType(),getTreeNodeInf()};
        //String [][]tableVales= {};//{{"A1","B1"},{"A2","B2"},{"A3","B3"},{"A4","B4"},{"A5","B5"}}; //数据
		DefaultTableModel tableModel;
		Object[][] VData = null;
		Vector<Vector<String>> vData= getAll();
		System.out.println(vData);
		//tableModel = new DefaultTableModel(tableVales,columnNames);
		tableModel = new DefaultTableModel(VData,columnNames);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) list_3.getSelectedValue();
				Vector<Vector<String>> vDataNew = null;
				Vector<String> v= new Vector<String>();
				v.add(textAreaA.getText());
				v.add(type);
				//vDataNew.add(v);
				dtm.addRow(v);
				String file = "D://model.xml";
				String tagName = (String) list.getSelectedValue();				
				try {
					Document doc;
					Element root;
					SAXReader saxReader=new SAXReader(); 
		            doc=saxReader.read(file);     
		            root = doc.getRootElement();
		            if(root.attributeValue("NodeName").equals(tagName)) {
		            	Element feature = root.addElement("feature");
	            		feature.addAttribute("NodeName", textAreaA.getText()).addAttribute("NodeType", type);
	            		OutputFormat format = OutputFormat.createPrettyPrint();
						format.setEncoding("UTF-8");
						XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
						//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
						writer.write(doc);
						writer.close();
		            }
		            for(Iterator i = root.elementIterator();i.hasNext();) {
		            	Element childEle = (Element) i.next();
		            	//没有子节点则寻找是否model.formal中是否有当前节点的形式化描述，有则改变名称
		            	if(childEle.attributeValue("NodeName").equals(tagName)) {
		            		List list = childEle.elements("feature");
		            		if(list.size() == 0) {
		            			try {
		            				Document doc1;
		            				Element root1;
		            				SAXReader sax = new SAXReader();
		            				doc1 = sax.read("D://model.formal");
		            				root1 = doc1.getRootElement();
		            				for(Iterator i1 = root1.elementIterator();i1.hasNext();) {
		            					String featureName = childEle.attributeValue("name");	
		            					Element child = (Element) i1.next();
		            					String f = child.attributeValue("name");	
		            					Element module = child.element("module");
		            					if(tagName.equals(f)) {
		            						child.setAttributeValue("name", textAreaA.getText());
		            						module.setAttributeValue("name", textAreaA.getText());
		            					}		            				
		            				}
		            				OutputFormat format1 = OutputFormat.createPrettyPrint();
		        					format1.setEncoding("UTF-8");
		        					XMLWriter writer1 = new XMLWriter(new FileOutputStream("D://model.formal"),format1);
		        					writer1.write(doc1);
		        					writer1.close();
		            			}catch(Exception ex) {
		        					ex.printStackTrace();
		        				}
		            		}
		            		Element feature = childEle.addElement("feature");
		            		feature.addAttribute("NodeName", textAreaA.getText()).addAttribute("NodeType", type);
		            		OutputFormat format = OutputFormat.createPrettyPrint();
							format.setEncoding("UTF-8");
							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
							writer.write(doc);
							writer.close();
		            	}
		            }
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}			
		});
		

		
		
		btnNewButton.setBounds(14, 369, 80, 27);
		getContentPane().add(btnNewButton);
		
		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();//获得选中行的索引
				String type = (String) list_3.getSelectedValue();
                if(selectedRow!= -1)   //是否存在选中行
                {	
                	String tagName = (String) dtm.getValueAt(selectedRow, 0);
                	String file = "D://model.xml";			
    				try {
    					Document doc;
    					Element root;
    					SAXReader saxReader=new SAXReader(); 
    		            doc=saxReader.read(file);     
    		            root = doc.getRootElement();
    		            for(Iterator i = root.elementIterator();i.hasNext();) {
    		            	Element childEle = (Element) i.next();
    		            	if(childEle.attributeValue("NodeName").equals(tagName)) {   		       
    		            		childEle.setAttributeValue("NodeName", textAreaA.getText());
    		            		childEle.setAttributeValue("NodeType", type);
    		            		OutputFormat format = OutputFormat.createPrettyPrint();
    							format.setEncoding("UTF-8");
    							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
    							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
    							writer.write(doc);
    							writer.close();
    		            	}
    		            	for(Iterator i1 = childEle.elementIterator();i1.hasNext();) {
    		            		Element child = (Element) i1.next();
        		            	if(child.attributeValue("NodeName").equals(tagName)) {   		       
        		            		child.setAttributeValue("NodeName", textAreaA.getText());
        		            		child.setAttributeValue("NodeType", type);
        		            		OutputFormat format = OutputFormat.createPrettyPrint();
        							format.setEncoding("UTF-8");
        							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
        							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
        							writer.write(doc);
        							writer.close();
        		            	}
        		            	for(Iterator i11 = childEle.elementIterator();i11.hasNext();) {
        		            		Element child1 = (Element) i11.next();
            		            	if(child1.attributeValue("NodeName").equals(tagName)) {   		       
            		            		child1.setAttributeValue("NodeName", textAreaA.getText());
            		            		child1.setAttributeValue("NodeType", type);
            		            		OutputFormat format = OutputFormat.createPrettyPrint();
            							format.setEncoding("UTF-8");
            							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
            							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
            							writer.write(doc);
            							writer.close();
            		            	}
            		            	for(Iterator i111 = childEle.elementIterator();i111.hasNext();) {
            		            		Element child2 = (Element) i111.next();
                		            	if(child2.attributeValue("NodeName").equals(tagName)) {   		       
                		            		child2.setAttributeValue("NodeName", textAreaA.getText());
                		            		child2.setAttributeValue("NodeType", type);
                		            		OutputFormat format = OutputFormat.createPrettyPrint();
                							format.setEncoding("UTF-8");
                							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
                							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
                							writer.write(doc);
                							writer.close();
                		            	}
            		            	}
        		            	}
    		            	}
    		            }
    				}
    				catch(Exception ex) {
    					ex.printStackTrace();
    				}
                	//String tagName = (String) dtm.getValueAt(selectedRow, 0);
                	
                	dtm.setValueAt(textAreaA.getText(), selectedRow, 0);
                	dtm.setValueAt(type, selectedRow, 1);
                    //table.setValueAt(arg0, arg1, arg2)
                }
			}
		});
		btnModify.setBounds(137, 369, 80, 27);
		getContentPane().add(btnModify);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {	
                	String tagName = (String) dtm.getValueAt(selectedRow, 0);
                	String file = "D://model.xml";			
    				try {
    					Document doc;
    					Element root;
    					SAXReader saxReader=new SAXReader(); 
    		            doc=saxReader.read(file);     
    		            root = doc.getRootElement();
    		            for(Iterator i = root.elementIterator();i.hasNext();) {
    		            	Element childEle = (Element) i.next();
    		            	if(childEle.attributeValue("NodeName").equals(tagName)) {   		       
    		            		root.remove(childEle);
    		            		OutputFormat format = OutputFormat.createPrettyPrint();
    							format.setEncoding("UTF-8");
    							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
    							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
    							writer.write(doc);
    							writer.close();
    		            	}
    		            	for(Iterator i1 = childEle.elementIterator();i1.hasNext();) {
    		            		Element child = (Element) i1.next();
        		            	if(child.attributeValue("NodeName").equals(tagName)) {   		       
        		            		childEle.remove(child);
        		            		OutputFormat format = OutputFormat.createPrettyPrint();
        							format.setEncoding("UTF-8");
        							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
        							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
        							writer.write(doc);
        							writer.close();
        		            	}
        		            	for(Iterator i11 = childEle.elementIterator();i11.hasNext();) {
        		            		Element child1 = (Element) i11.next();
            		            	if(child1.attributeValue("NodeName").equals(tagName)) {   		       
            		            		childEle.remove(child1);
            		            		OutputFormat format = OutputFormat.createPrettyPrint();
            							format.setEncoding("UTF-8");
            							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
            							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
            							writer.write(doc);
            							writer.close();
            		            	}
            		            	for(Iterator i111 = childEle.elementIterator();i111.hasNext();) {
            		            		Element child2 = (Element) i111.next();
                		            	if(child2.attributeValue("NodeName").equals(tagName)) {   		       
                		            		childEle.remove(child2);
                		            		OutputFormat format = OutputFormat.createPrettyPrint();
                							format.setEncoding("UTF-8");
                							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
                							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
                							writer.write(doc);
                							writer.close();
                		            	}
            		            	}
        		            	}
    		            	}
    		            }
    				}
    				catch(Exception ex) {
    					ex.printStackTrace();
    				}
                    dtm.removeRow(selectedRow);  //删除行
                }
			}
		});
		btnDelete.setBounds(256, 369, 80, 27);
		getContentPane().add(btnDelete);
		

		
		//table = new JTable(tableModel);
		scrollPane_3.setViewportView(table);
		
		JLabel lblNewLabel_2 = new JLabel("input the feature name and select it type");
		lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_2.setBounds(14, 137, 389, 18);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("you can add modify or delete the feature by the button");
		lblNewLabel_3.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_3.setBounds(14, 168, 467, 18);
		getContentPane().add(lblNewLabel_3);
		setBackground(new Color(204, 204, 255));
		setBounds(100, 100, 538, 469);

	}
	
	private void updateTable() {
		Vector<Vector<String>> vDate=dao.getAll();
		dtm.setDataVector(vDate, vHead);
		table.validate();
		 // TODO Auto-generated method stub		
	}

	public String[] getTreeNodeInf() {
		  List<String> featureNameList = new ArrayList<>();
		  String[] featureNodeString = null;
	        try {
	        	List<String> nameList = new ArrayList<>();
				//获取SAXReader对象
				SAXReader reader = new SAXReader();
				//创建一个File类
				File file = new File("D://model.xml");
				Document doc = reader.read(file);
				//获取跟节点
				Element root = doc.getRootElement();
				nameList.add(root.attributeValue("NodeName"));
				
				Iterator i = root.elementIterator();
				while(i.hasNext()) {
					Element element = (Element)i.next();
					nameList.add(element.attributeValue("NodeName"));
					
				}
				featureNameList = nameList;
				featureNodeString = new String[featureNameList.size()];
				for(int k=0; k < featureNameList.size(); k++)
				{
					featureNodeString[k] = featureNameList.get(k);
					System.out.println(featureNodeString[k]);
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return featureNodeString;

	    }
	  public String[] getTreeNodeType() 
	  {
		  List<String> featureTypeList = new ArrayList<>();
		  String[] featureTypeString = null;
		  try {
	        	List<String> typeList = new ArrayList<>();
				//获取SAXReader对象
				SAXReader reader = new SAXReader();
				//创建一个File类
				File file = new File("D://model.xml");
				Document doc = reader.read(file);
				//获取跟节点
				Element root = doc.getRootElement();

				typeList.add(root.attributeValue("NodeType"));
				
				Iterator i = root.elementIterator();
				while(i.hasNext()) {
					Element element = (Element)i.next();
					typeList.add(element.attributeValue("NodeType"));
					
				}
				featureTypeList = typeList;
				for(int j = 0; j < featureTypeList.size(); j++) {
					featureTypeList.get(j);
				}
				featureTypeString = new String[featureTypeList.size()];
				for(int k=0; k < featureTypeList.size(); k++)
				{
					featureTypeString[k] = featureTypeList.get(k);
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return featureTypeString;

	  }
	    /*
	    @param
	    @author
	    DefaultMutableTreeNode ： Jtree中的数据模型node
	    Element：Xml中获得的元素
	    */
	    public Vector<Vector<String>> getTreenodeNames(Element e,String name) {
			List<String> featureNameList = new ArrayList<>();
	    	List<String> featureTypeList = new ArrayList<>();
	    	Vector<Vector<String>> result = new Vector<Vector<String>>();
	    	List<String> typeList = new ArrayList<>();
        	List<String> nameList = new ArrayList<>();
	        Iterator iter = e.elementIterator();
	        String featurename = e.attributeValue("NodeName");
	        if(name.equals(featurename)) {
	        	result = getAll();
	        }
	        while (iter.hasNext()) {
	        	Element childEle = (Element) iter.next();
	        	DefaultMutableTreeNode child = new DefaultMutableTreeNode(new String(childEle.attributeValue("NodeName")));
	        	featurename = childEle.attributeValue("NodeName");
	        	if(name.equals(featurename)) {
	        		Iterator i1 = childEle.elementIterator();
	       			while (i1.hasNext()) {
	       				Element childE = (Element) i1.next();
	       				nameList.add(childE.attributeValue("NodeName"));
	       				typeList.add(childE.attributeValue("NodeType"));
	       			}
	       			featureNameList = nameList;
	       			featureTypeList = typeList;
	       			for(int j = 0; j < featureNameList.size(); j++) {
        				featureNameList.get(j);	        				
        				featureTypeList.get(j);
	        		}
	        		for(int k = 0; k < featureNameList.size(); k++) {
	        			Vector<String> v= new Vector<String>();
	       				v.add(featureNameList.get(k));
	       				v.add(featureTypeList.get(k));		    		
	       				result.add(v);
	       			}
	       		}
	        	else if (childEle.nodeCount()==0) {
	       			continue;
	       		}
	       		else{
        			getTreenodeNames(childEle,name);//遍历
	        	}
	       	}      
	        return result;
	    }  
	    
	    public Vector<Vector<String>> getAll(){
	    	List<String> featureNameList = new ArrayList<>();
	    	List<String> featureTypeList = new ArrayList<>();
	    	Vector<Vector<String>> result = new Vector<Vector<String>>();
	    	//List<Element> list = current.elements("contact");
	    	try {
	        	List<String> typeList = new ArrayList<>();
	        	List<String> nameList = new ArrayList<>();
				//获取SAXReader对象
				SAXReader reader = new SAXReader();
				//创建一个File类
				File file = new File("D://model.xml");
				Document doc = reader.read(file);
				//获取跟节点
				Element root = doc.getRootElement();
				
				//typeList.add(root.attributeValue("NodeType"));
				//nameList.add(root.attributeValue("NodeName"));
				
				Iterator i = root.elementIterator();
				while(i.hasNext()) {
					Element element = (Element)i.next();
					nameList.add(element.attributeValue("NodeName"));
					typeList.add(element.attributeValue("NodeType"));
				}
				//System.out.println(typeList);
				featureNameList = nameList;
				featureTypeList = typeList;
				for(int j = 0; j < featureNameList.size(); j++) {
					featureNameList.get(j);
					featureTypeList.get(j);
				}
				for(int k = 0; k < featureNameList.size(); k++) {
		    		Vector<String> v= new Vector<String>();
		    		v.add(featureNameList.get(k));
		    		v.add(featureTypeList.get(k));		    		
		    		result.add(v);
		    	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
	    	return result;
	    }
	    
	    private JTable getTable() {
			vHead=new Vector<Object>();
			vHead.add("name");
			vHead.add("type");
			Vector<Vector<String>> vDate= getAll();
			dtm=new DefaultTableModel(vDate, vHead);
			JTable table=new JTable(dtm);
			return table;
		}
	    
	    

}
