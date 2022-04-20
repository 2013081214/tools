package xmlfactory;

import java.net.URL;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MineTree {
    URL configpath;//指定Xml文件在项目中的位置
    Document doc;
    Element root;
    public DefaultMutableTreeNode rootTree ;
    public MineTree() {
        configpath = this.getClass().getResource("D://model.xml");//将URL转换为字符串交由SAXReader解析
        getTree();
    }
    public void getTree() {
        try {
            SAXReader saxReader=new SAXReader(); 
            doc=saxReader.read("D://model.xml");     
            root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
            rootTree =new DefaultMutableTreeNode(new String(root.attributeValue("NodeName")));//创建Jtree数据模型的根节点            
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
            Element childEle = (Element) iter.next();
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