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
         //����Ҫ���صĸ��ڵ�
		String name = root.getName();
         //��øýڵ��NodeName
         String value = root.getStringValue(); 
         //��øýڵ��NodeValue
         treeNode = new DefaultMutableTreeNode( root.getNodeType() == Node.TEXT_NODE ? value : name );
         //���Ϊֵ�ڵ㣬��ôȡ�øýڵ��ֵ������ȡ�øýڵ��Tag������ 
         if ( ((org.w3c.dom.Node) root).hasChildNodes() ) 
        	 //����ýڵ��к��ӽڵ㣬��ô�ݹ鴦��ýڵ�ĺ��ӽڵ�
         {  
        	 NodeList children = ((org.w3c.dom.Node) root).getChildNodes();  
        	 //ȡ�øýڵ���ӽڵ��б�
        	 if( children != null ){       
        		 //�ж��ӽڵ��Ƿ�Ϊ��
        		 int numChildren = children.getLength();  
        		 //ȡ���ֽ���Ŀ
        		 for (int i=0; i < numChildren; i++){  
        			 Node node = (Node) children.item(i); 
        			 //ѭ������ÿ���ӽڵ�
        			 if( node != null )
        			 {  
        				 if( node.getNodeType() == Node.ELEMENT_NODE )
        				 { 
        					 treeNode.add( LoadFile(node) ); 
        					 //������ӽڵ㻹�к��ӽڵ�ʹ�õݹ�ķ���������ӽڵ�
        				 }else {
        					 String data = node.getStringValue();
        					 if( data != null )
        					 {
        						 data = data.trim();
        						 if ( !data.equals("\n") && !data.equals("\r\n") && data.length() > 0 )
        						 {    
        							 treeNode.add(new DefaultMutableTreeNode(node.getStringValue()));
        							 //����ýڵ�û�к��ӽڵ㣬��ôֱ�Ӽӵ��ڵ���
        						 }   
        					 }  
        				 } 
        			 } 
        		 }
        	 }
         } 
      return treeNode;  //���ؽڵ�
		
	}
	
	//save Jtree to XMLfile
	public void SaveToFile(DefaultMutableTreeNode root,FileWriter fw) {
		try {
			//�����Ҷ�ӽڵ���ֱ�ӽ��ýڵ�������ļ���
			if (root.isLeaf()) { 
				fw.write(root.toString()+"\r\n");
				}
			//����Ҷ�ӽڵ�Ļ��ݹ�����ýڵ�
			else {
				fw.write("<"+root.toString()+">\r\n");
				//�ݹ�����ýڵ�������ӽڵ� }
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
