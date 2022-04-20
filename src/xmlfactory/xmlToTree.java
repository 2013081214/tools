package xmlfactory;

import java.awt.Color;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import xmlfactory.TreeNode;

public class xmlToTree {
	public JTree createTree(InputStream treeXml) 
	{
		SAXBuilder builder = new SAXBuilder();
		DefaultMutableTreeNode top = null;
		try 
		{
			Document doc = builder.build( treeXml ) ;
			Element root = doc.getRootElement() ;
			TreeNode rootNode = new TreeNode(
						root.getAttributeValue("ParentId") ,
						root.getAttributeValue("NodeId") ,
						root.getAttributeValue("NodeName") ) ;
			top = new DefaultMutableTreeNode(rootNode) ;
			addNode(root, top) ;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace() ;
		}
		UIManager.put( "Tree.hash" , new ColorUIResource(Color.blue)) ;
		return new JTree(top) ;
	}

	private void addNode( Element e, DefaultMutableTreeNode rootNode ) 
	{
		String pid = e.getAttributeValue("ParentId") ;
		String id = e.getAttributeValue( "NodeId" ) ;
		String name = e.getAttributeValue( "NodeName" ) ;
		TreeNode node = new TreeNode( pid, id, name ) ;
		//System.out.print("node:" + node +" " );//node:��������� node:����·���վ node:m1 node:m2 node:�Ϻ��б��վ node:AA node:��������� node:�������վ node:CC 
		// ���и��ڵ�
		Element father = e.getParentElement() ;
		if (father != null) 
		{
			String fid = father.getAttributeValue("NodeName") ;//ParentId
			DefaultMutableTreeNode fatherNode = getTreeNode( fid, rootNode ) ;
			if (fatherNode != null) 
			{
				fatherNode.add( new DefaultMutableTreeNode(node)) ;
			}
		}
		// �����ӽڵ�
		Iterator it = e.getChildren().iterator() ;
		while (it.hasNext()) 
		{
			Element child = (Element) it.next() ;
			addNode( child , rootNode ) ;
		}
	}
	///////////////////////////////////////////////////pid ����
	private DefaultMutableTreeNode getTreeNode( String name , DefaultMutableTreeNode rootNode ) //pid 
	{
		DefaultMutableTreeNode returnNode = null ;
		if (rootNode != null) 
		{
			Enumeration<?> eunm = rootNode.breadthFirstEnumeration() ;
			while (eunm.hasMoreElements()) 
			{
				DefaultMutableTreeNode temp = (DefaultMutableTreeNode) eunm.nextElement() ;
				TreeNode node = (TreeNode) temp.getUserObject() ;
				if (node.getName().equals(name)) //pid ����
				{
					returnNode = temp ;
					break ;
				}
			}
		}
		return returnNode ;
	}
}
