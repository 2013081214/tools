package template;

import java.awt.EventQueue;
import diagrameditor.DiagramSecond;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextField;
import javax.swing.JTree;

import diagrameditor.DiagramSecond;
import xmlfactory.Dom4jGetFeatureList;
import xmlfactory.Featuredatamodel;

import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

public class ChangeFeatureType extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeFeatureType frame = new ChangeFeatureType();
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
	public ChangeFeatureType() {
		setClosable(true);
		setResizable(true);
		setTitle("ChangeFeatureType");
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setForeground(new Color(255, 255, 255));
		scrollPane.setBounds(120, 13, 272, 31);
		getContentPane().add(scrollPane);
		
		//读取XML文件，提供对应特征节点的名称
		
		
		//JTree tree = new JTree();
		//JTree tree= tree.getModel(diagrameditor.DiagramSecond);
		/*Vector nodeValue = new Vector();
		DefaultMutableTreeNode gen=(DefaultMutableTreeNode)tree.getModel().getRoot();
		for (int i=0;i<gen.getChildCount();i++)
		{
			String[] str=new String[gen.getChildCount()];

			for (int a=0;a<gen.getChildAt(i).getChildCount();a++)
			{
				nodeValue.addElement(gen.getChildAt(i).getChildAt(a).toString());
			}		
		}*/
		 
		
		Dom4jGetFeatureList featuredatamodel = new Dom4jGetFeatureList();//从XML文件中读取所有前缀为featurename的叶子节点		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Dom4jGetFeatureList();
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		JList list = new JList(v);
		
		//JList list = new JList(nodeValue);
		//JList list = new JList();
		scrollPane.setViewportView(list);


		
		JLabel lblNewLabel = new JLabel("select feature");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setBounds(14, 13, 106, 31);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select the type of  feature");
		lblNewLabel_1.setBounds(14, 85, 302, 18);
		getContentPane().add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 134, 302, 25);
		getContentPane().add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"Mandatory", "Optional", "Multiple", "Alternative", "OrGroup"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_1.setViewportView(list_1);
		setBackground(new Color(204, 204, 255));
		setBounds(100, 100, 416, 280);
		
		JButton btnNewButton_1 = new JButton("Confirm");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String title = "Change Success";
				JDialog jdl = new JDialog();//弹出对话框
				//public JDialog(Frame frame, String title,  boolean model);
		        jdl.setSize(470, 200);//对话框大小
		        jdl.setLocation(200, 400);
		        jdl.setTitle(title);
		        String info = "The type has been modified, please go to the corresponding panel to operate!";
				JLabel jl = new JLabel(info );
		        jdl.getContentPane().add(jl);
		        jdl.setVisible(true);//可见
			}
		});
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setBackground(UIManager.getColor("activeCaption"));
		btnNewButton_1.setBounds(307, 200, 93, 31);
		getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) list_1.getSelectedValue();
                String tagName = (String) list.getSelectedValue();
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
                }
			
		});

		


	}
}
