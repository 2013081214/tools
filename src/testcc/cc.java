package testcc;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class cc extends JFrame implements TreeSelectionListener {
	private JTree tree;
	private TreePath path;
	private Panel topPanel;
	private DefaultMutableTreeNode top;
	private DefaultMutableTreeNode clicknode;
	private String Nodeid ;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cc tree = new cc();

					tree.init();
					tree.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public cc() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	// **//**//**
	public void init() 
	{
		try 
		{
			this.setLayout(new GridLayout(1, 1)) ;
			tree = createTree(new FileInputStream("src/Tree.xml"));//("src/Tree.xml")) ;// new FileInputStream("src/org.xml")
			tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION) ;
			tree.putClientProperty("JTree.lineStyle", "Angled") ;
			tree.setShowsRootHandles(true) ;
			tree.setEditable(true) ;//tree.setEditable(false) ;
			tree.addTreeSelectionListener(this) ;
			//IconRender render = new IconRender() ;
			//tree.setCellRenderer(render) ;
			topPanel = new Panel(new BorderLayout()) ;
			topPanel.add(tree) ;
			this.add(topPanel) ;
		} 
		catch (Exception e) 
		{
			e.printStackTrace() ;
		}
	}

	public JTree createTree(InputStream treeXml) 
	{
		SAXBuilder builder = new SAXBuilder();
		try 
		{
			Document doc = builder.build(treeXml) ;
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
		//System.out.print("node:" + node +" " );//node:陕西供电局 node:电子路变电站 node:m1 node:m2 node:上海市变电站 node:AA node:北京供电局 node:朝阳变电站 node:CC 
		// 如有父节点
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
		// 如有子节点
		Iterator it = e.getChildren().iterator() ;
		while (it.hasNext()) 
		{
			Element child = (Element) it.next() ;
			addNode( child , rootNode ) ;
		}
	}
	///////////////////////////////////////////////////pid 两处
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
				if (node.getName().equals(name)) //pid 两处
				{
					returnNode = temp ;
					break ;
				}
			}
		}
		return returnNode ;
	}

	public void valueChanged(TreeSelectionEvent event) 
	{
		if ( event.getSource() == tree ) 
		{
			path = event.getPath() ;
			clicknode = (DefaultMutableTreeNode) path.getLastPathComponent() ;
			System.out.println("树的节点: " + clicknode) ;// 提示点击的信息
			Object uo = clicknode.getUserObject() ;
			if (uo instanceof TreeNode) 
			{
				TreeNode nd = (TreeNode) clicknode.getUserObject() ;
				Nodeid = nd.getId() ;
				System.out.println( "线路ID: " + Nodeid ) ;
			}
		}
	}
}

