package testcc;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestButtonTextField {
	Enumeration enumeration;
	URL configpath;//指定Xml文件在项目中的位置
    Document doc;
    Element root;
    public DefaultMutableTreeNode rootTree ;
    String[] consts = null;
    String[] constWarn = null;
    public void getTree() {
        try {
            SAXReader saxReader=new SAXReader(); 
            doc=saxReader.read("D://model.formal");     
            root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
            
            
            rootTree =new DefaultMutableTreeNode(new String(root.attributeValue("NodeName")));//创建Jtree数据模型的根节点 
            
           // getTreenodeNames(root,rootTree);
            enumeration = rootTree.postorderEnumeration();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    public void getTreenodeNames(Element e,DefaultMutableTreeNode node) {

        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
            Element childEle = (Element) iter.next();
            if(childEle.getName() == "consts") {
            	String nodeConsts = childEle.getText();
            	String[] sourceStrArray = nodeConsts.split(";");
            	consts = ArrayUtils.addAll(consts, sourceStrArray);
            }
            for(int i = 0 ;i < consts.length; i++) {
            	String test = consts[i];
            	for(int j = 1; j< consts.length;j++) {
            		if(test == consts[j]) {
            			constWarn = ArrayUtils.addAll(constWarn, test);
            		}
            	}
           	
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
	public void StringSplit(){
		Iterator iter = e.elementIterator();
		Vector<Vector<String>> moduleProcess = new Vector<Vector<String>>();
		for(int k = 0; k < featureNameList.size(); k++) {
    		Vector<String> v= new Vector<String>();
    		v.add(ModuleList.get(k));
    		while (iter.hasNext()) {
    			 v.add(processList.get(k));
    		}	    		
    		result.add(v);
    	}
		
	}
	
	
	public void preorder() {
		
	}*/
	
	
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new TestButtonTextField();
            }
        });
    }
}