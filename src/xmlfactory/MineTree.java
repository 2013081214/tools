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
    URL configpath;//ָ��Xml�ļ�����Ŀ�е�λ��
    Document doc;
    Element root;
    public DefaultMutableTreeNode rootTree ;
    public MineTree() {
        configpath = this.getClass().getResource("D://model.xml");//��URLת��Ϊ�ַ�������SAXReader����
        getTree();
    }
    public void getTree() {
        try {
            SAXReader saxReader=new SAXReader(); 
            doc=saxReader.read("D://model.xml");     
            root = doc.getRootElement();//ʹ��dom4j�ṩ��API���XML�ĸ��ڵ�
            rootTree =new DefaultMutableTreeNode(new String(root.attributeValue("NodeName")));//����Jtree����ģ�͵ĸ��ڵ�            
            getTreenodeNames(root,rootTree);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    @param
    @author
    DefaultMutableTreeNode �� Jtree�е�����ģ��node
    Element��Xml�л�õ�Ԫ��
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
                getTreenodeNames(childEle,child);//����
            }

        }
    }
}