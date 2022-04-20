package template;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import xmlfactory.Dom4jGetFeatureList;
import javax.swing.UIManager;

public class addOrGroupFeature extends JInternalFrame {

	private JTable table;
	private Vector<Object> vHead;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addOrGroupFeature frame = new addOrGroupFeature();
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
	public addOrGroupFeature() {
		setClosable(true);
		setResizable(true);
		setTitle("Decompose Feature to OrGroup Feature");
		getContentPane().setBackground(new Color(192, 192, 192));
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setForeground(new Color(255, 255, 255));
		scrollPane.setBounds(179, 13, 213, 31);
		getContentPane().add(scrollPane);
		
		//JList list = new JList();
		Dom4jGetFeatureList featuredatamodel = new Dom4jGetFeatureList();//从XML文件中读取所有前缀为featurename的叶子节点		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Dom4jGetOrGroupFeatureList();
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		JList list = new JList(v);
		scrollPane.setViewportView(list);
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(14, 156, 467, 163);
		getContentPane().add(scrollPane_3);
		table = getTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_3.setViewportView(table);
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
					dtm = new DefaultTableModel(vDateNew, vHead);
					table = new JTable(dtm);
					scrollPane_3.setViewportView(table);					
		        }
		    	catch (Exception ex) {
		            ex.printStackTrace();
		        }
			}
		});
		
		JLabel lblNewLabel = new JLabel("select feature");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 12));
		lblNewLabel.setBounds(14, 13, 166, 31);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("\u786E\u8BA4\u4FEE\u6539");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(UIManager.getColor("activeCaption"));
		btnNewButton_1.setBounds(415, 387, 93, 33);
		getContentPane().add(btnNewButton_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setForeground(Color.WHITE);
		scrollPane_1.setBounds(179, 74, 213, 25);
		getContentPane().add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"alternative", "orgroup"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(list_1);
		
		JLabel lblNewLabel_1 = new JLabel("Select the type of input feature");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(14, 74, 166, 31);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("\u9009\u62E9\u9700\u8981\u6DFB\u52A0\u53F6\u5B50\u8282\u70B9\u7684\u8282\u70B9");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(14, 126, 166, 31);
		getContentPane().add(lblNewLabel_1_1);
		
		JTextArea textAreaA = new JTextArea();
		textAreaA.setBounds(256, 332, 166, 24);
		getContentPane().add(textAreaA);
		
		
		String[] columnNames = {"Feature Name"};   //列名
		Object[][] VData = null;
		Vector<Vector<String>> vData= getAll();
        //String [][]tableVales={{"A1"},{"A2"},{"A3"},{"A4"},{"A5"}}; //数据
		DefaultTableModel tableModel;
		tableModel = new DefaultTableModel(VData,columnNames);
		table = getTable();
		
		
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = "Optional";
				Vector<Vector<String>> vDataNew = null;
				Vector<String> v= new Vector<String>();
				v.add(textAreaA.getText());
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
		            	if(childEle.attributeValue("NodeName").equals(tagName)) {
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
				String type = "Optional";
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
		btnModify.setBounds(189, 369, 80, 27);
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
		btnDelete.setBounds(328, 369, 80, 27);
		getContentPane().add(btnDelete);
		
		JLabel lblNewLabel_2 = new JLabel("input new feature name");
		lblNewLabel_2.setBounds(14, 334, 213, 18);
		getContentPane().add(lblNewLabel_2);
		setBackground(new Color(204, 204, 255));
		setBounds(100, 100, 538, 469);

	}
	 public Vector<Vector<String>> getAll(){
	    	List<String> featureNameList = new ArrayList<>();
	    	Vector<Vector<String>> result = new Vector<Vector<String>>();
	    	//List<Element> list = current.elements("contact");
	    	try {
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
				}
				//System.out.println(typeList);
				featureNameList = nameList;
				for(int j = 0; j < featureNameList.size(); j++) {
					featureNameList.get(j);
				}
				for(int k = 0; k < featureNameList.size(); k++) {
		    		Vector<String> v= new Vector<String>();
		    		v.add(featureNameList.get(k));		    		
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
			Vector<Vector<String>> vDate= getAll();
			dtm=new DefaultTableModel(vDate, vHead);
			JTable table=new JTable(dtm);
			return table;
	}
	 
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
}