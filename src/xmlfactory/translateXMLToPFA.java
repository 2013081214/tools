package xmlfactory;

import java.net.URL;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class translateXMLToPFA {
    private static final String Multiple = "Multiple";
    private static final String Alternative = "Alternative";
    private static final String OrGroup = "OrGroup";
    private static final String Mandatory = "Mandatory";
    private static final String Optional = "Optional";
	URL configpath;//指定Xml文件在项目中的位置
    Document doc;
    Element root;
    StringBuilder PFA;
    public DefaultMutableTreeNode rootTree ;
    public StringBuilder translateXMLToPFA() {
        configpath = this.getClass().getResource("D://model.xml");//将URL转换为字符串交由SAXReader解析
        getTree();
        return PFA;
    }
    public void getTree() {
        try {
           // System.out.println(configpath);
            SAXReader saxReader=new SAXReader(); 
            doc=saxReader.read("D://model.xml");     
            root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
            String type = new String(root.attributeValue("NodeType"));
            if(type == Multiple) {
            	PFA.append(new String(root.attributeValue("NodeName")));
            	PFA.append("=");
            }else if(type == Alternative) {
            	PFA.append(new String(root.attributeValue("NodeName")));
            	PFA.append("=");
            }else if(type == OrGroup) {
            	PFA.append(new String(root.attributeValue("NodeName")));
            	PFA.append("=");
            }    
            getTreenodeNames(root,rootTree);
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
    public void getTreenodeNames(Element e,DefaultMutableTreeNode node) {

        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
        	Element father = (Element) iter;
            Element childEle = (Element) iter.next();
            if(childEle.attributeValue("NodeName") == Mandatory) {
            	PFA.append("(");
            	PFA.append(new String(root.attributeValue("NodeName")));
            	PFA.append(")");
            }else if(childEle.attributeValue("NodeName") == Optional) {
            	PFA.append("(opt[");
            	PFA.append(new String(root.attributeValue("NodeName")));
            	PFA.append("])");
            }
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(new String(childEle.attributeValue("NodeName")));
            node.add(child);
            if (childEle.nodeCount()==0) {
                continue;
            }
            else{
                getTreenodeNames(childEle,child);//遍历
            }

        }
    }
    
    
}