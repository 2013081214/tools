package xmlfactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;



public class FeatureTree {
	//URL configpath;//指定XML文件位置
	private TreePath path;
	private JTree tree;
	Document doc;
	Element root;
	private DefaultMutableTreeNode top;
	
	public DefaultMutableTreeNode rootTree;
	public FeatureTree(){
		//configpath = this.getClass().getResource("D://model.xml");
		getTree();
	}
	/**
	 * 
	 */
	public void getTree() {
		// TODO Auto-generated method stub
		try {
			//SAXReader saxReader = new SAXReader();
			tree = createTree(new FileInputStream("D://model.xml"));
			//doc = saxReader.read("D://model.xml");
			//root = 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param treexml
	 * @return
	 */
	public JTree createTree(InputStream treexml) {
		SAXBuilder saxBuilder = new SAXBuilder();
		try 
		{
			doc = (Document) saxBuilder.build(treexml);
			Element root = doc.getRootElement();
			FeatureTreeNode rootNode = new FeatureTreeNode(root.getAttributeValue("NodeName"), root.getAttributeValue("NoodeType"));
			top = new DefaultMutableTreeNode(rootNode);
			addNode(root, top);
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return new JTree(top);
	}
	/**
	 * 
	 * @param e
	 * @param rootNode
	 */
	private void addNode(Element e, DefaultMutableTreeNode rootNode) {
		String nodeName = e.getAttributeValue("NodeName");
		String nodeType = e.getAttributeValue("NodeType");
		FeatureTreeNode node = new FeatureTreeNode(nodeName,nodeType);
		
		Element father = e.getParentElement();
		if(father != null) {
			String fName = father.getAttributeValue("NodeName");
			DefaultMutableTreeNode fatherNode = getTreeNode(fName, rootNode);
			if(fatherNode != null) {
				fatherNode.add(new DefaultMutableTreeNode(node));
			}
			
			Iterator it = e.getChildren().iterator();
		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * @param fName
	 * @param rootNode
	 * @return
	 */
	private DefaultMutableTreeNode getTreeNode(String fName, DefaultMutableTreeNode rootNode) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode returnNode = null;
		if(rootNode != null)
		{
			Enumeration<?> eunm = rootNode.breadthFirstEnumeration();
			while(eunm.hasMoreElements())
			{
				DefaultMutableTreeNode temp = (DefaultMutableTreeNode) eunm.nextElement();
				TreeNode node = (TreeNode) temp.getUserObject();
				if(node.getName().equals(fName))
				{
					returnNode = temp;
					break;
				}
			}
		}
		
		return returnNode;
	}
}
