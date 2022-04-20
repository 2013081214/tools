package xmlfactory;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.*;
import org.dom4j.Document;
import org.dom4j.Node;
import org.w3c.dom.NodeList;

public class XMLTree extends JTree {
	//JTree root node
	private DefaultMutableTreeNode treeNode;
	//XML parser three member varible
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private org.w3c.dom.Document doc;
	
	//initial tree
	public XMLTree(String fileName) throws Exception {
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		treeNode = LoadFile(parseXml(fileName));
		setModel(new DefaultTreeModel(treeNode));
	}
	
	//build tree from XMLfile
	public DefaultMutableTreeNode LoadFile(Node root) {
		DefaultMutableTreeNode  treeNode = null; 
         //定义要返回的根节点
		String name = root.getName();
         //获得该节点的NodeName
         String value = root.getStringValue(); 
         //获得该节点的NodeValue
         treeNode = new DefaultMutableTreeNode( root.getNodeType() == Node.TEXT_NODE ? value : name );
         //如果为值节点，那么取得该节点的值，否则取得该节点的Tag的名字 
         if ( ((org.w3c.dom.Node) root).hasChildNodes() ) 
        	 //如果该节点有孩子节点，那么递归处理该节点的孩子节点
         {  
        	 NodeList children = ((org.w3c.dom.Node) root).getChildNodes();  
        	 //取得该节点的子节点列表
        	 if( children != null ){       
        		 //判断子节点是否为空
        		 int numChildren = children.getLength();  
        		 //取得字节数目
        		 for (int i=0; i < numChildren; i++){  
        			 Node node = (Node) children.item(i); 
        			 //循环处理每个子节点
        			 if( node != null )
        			 {  
        				 if( node.getNodeType() == Node.ELEMENT_NODE )
        				 { 
        					 treeNode.add( LoadFile(node) ); 
        					 //如果该子节点还有孩子节点使用递归的方法处理该子节点
        				 }else {
        					 String data = node.getStringValue();
        					 if( data != null )
        					 {
        						 data = data.trim();
        						 if ( !data.equals("\n") && !data.equals("\r\n") && data.length() > 0 )
        						 {    
        							 treeNode.add(new DefaultMutableTreeNode(node.getStringValue()));
        							 //如果该节点没有孩子节点，那么直接加到节点下
        						 }   
        					 }  
        				 } 
        			 } 
        		 }
        	 }
         } 
      return treeNode;  //返回节点
		
	}
	
	//save Jtree to XMLfile
	public void SaveToFile(DefaultMutableTreeNode root,FileWriter fw) {
		try {
			//如果是叶子节点则直接将该节点输出到文件中
			if (root.isLeaf()) { 
				fw.write(root.toString()+"\r\n");
				}
			//不是叶子节点的话递归输出该节点
			else {
				fw.write("<"+root.toString()+">\r\n");
				//递归输出该节点的所有子节点 }
				for(int i = 0; i < root.getChildCount(); i++) {
					DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) root.getChildAt(i);
					SaveToFile(childNode, fw);
				}
				fw.write("<"+root.toString()+">\r\n");
				}
			fw.close();
			} catch (Exception e){  
				e.printStackTrace();
			} 
	}
	
	//get root node
	private Node parseXml(String text) {
		ByteArrayInputStream byteStream;
		byteStream = new ByteArrayInputStream(text.getBytes());
		//read XML into Stream
		try {
			doc = db.parse(byteStream);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return (Node)doc.getDocumentElement();
	}
}
