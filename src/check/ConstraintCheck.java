package check;

import java.awt.EventQueue;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import diagrameditor.DiagramSecond;
import xmlfactory.MineTree;

public class ConstraintCheck {
    Element root;
    public DefaultMutableTreeNode rootTree ;
	MineTree tree = new MineTree();
	Enumeration enumeration;
/*
 * 	
 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ConstraintCheck frame = new ConstraintCheck();
//					frame.ConstraintCheck();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
/*
 * 	ConstraintCheck返回节点级别
 */
	public int getFeatureLevel(String feature) {
		int level = 0;
		enumeration = tree.rootTree.postorderEnumeration();
		while (enumeration.hasMoreElements()) {// 遍历节点枚举对象
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();// 获得节点
			if(node.getUserObject().equals(feature)) {
				level = node.getLevel();
			}

		}
		return level;	
	}
/*
 * 添加约束时对比两个约束的级别	
 */
	public int compareFeatureRelationship(String feature1, String feature2) {
		int result;
		int a = getFeatureLevel(feature1);
		int b = getFeatureLevel(feature2);
		if(a == b) {
			result = 0;
		}
		else if(a > b) {
			result = 1;
		}
		else if(a < b){
			result = 2;
		}
		else {
			result = 3;
		}
		return result;
	}
	
	public String getFeatureType(String feature) {
		String type = null;
		 try {
	            SAXReader saxReader=new SAXReader(); 
	            Document doc=saxReader.read("D://model.xml");     
	            root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
	            
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		
		return type;
		
	}
	
	 public void getNodeType() {
	        try {
	            SAXReader saxReader=new SAXReader(); 
	            Document doc=saxReader.read("D://model.xml");     
	            root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
	            rootTree =new DefaultMutableTreeNode(new String(root.attributeValue("NodeName")));//创建Jtree数据模型的根节点            
	            //getTreenodeNames(root);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
	    /*
	    @param
	    @author
	    DefaultMutableTreeNode ： Jtree中的数据模型node
	    Element：Xml中获得的元素
	    */
	    public void getTreenodeNames(Element e, String feature) {

	        Iterator iter = e.elementIterator();
	        while (iter.hasNext()) {
	            Element childEle = (Element) iter.next();
	            DefaultMutableTreeNode child = new DefaultMutableTreeNode(new String(childEle.attributeValue("NodeName")));
	            if (childEle.nodeCount()==0) {
	                continue;
	            }
	            else{
	                getTreenodeNames(childEle, feature);//遍历
	            }

	        }
	    }
	
	public void ConstraintCheck() {
		
	}
}
