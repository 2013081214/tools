package xmlfactory;

import java.io.File;
import java.util.*;

import javax.swing.tree.DefaultMutableTreeNode;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class Dom4jGetFeatureList {
	List<String> featureNameList = new ArrayList<>();
    public DefaultMutableTreeNode rootTree ;
	List<String> namelist = new ArrayList<>();
	String Multipe = "Multiple";
	String OrGroup = "OrGroup";
	String Alternative = "Alternative";
	
	public List<String>Dom4jGetFeatureList(){
		
		try{
			//获取SAXReader对象
			SAXReader reader = new SAXReader();
			//创建一个File类
			File file = new File("D:\\model.xml");
			Document doc = reader.read(file);
			//获取跟节点
			Element root = doc.getRootElement();

			namelist.add(root.attributeValue("NodeName"));
		
			rootTree =new DefaultMutableTreeNode(new String(root.attributeValue("NodeName")));//创建Jtree数据模型的根节点            
            getTreenodeNames(root,rootTree);
			
			featureNameList = namelist;
			for(int j = 0; j < featureNameList.size(); j++) {
				featureNameList.get(j);

			}
		}
		catch (DocumentException e) {
				e.printStackTrace();
		}
		
		
		
		return featureNameList;
	}
	
	public List<String>Dom4jGetMultipeFeatureList(){
		
		try{
			//获取SAXReader对象
			SAXReader reader = new SAXReader();
			//创建一个File类
			File file = new File("D:\\model.xml");
			Document doc = reader.read(file);
			//获取跟节点
			Element root = doc.getRootElement();
			if(root.attributeValue("NodeType").equals(Multipe)) {
				namelist.add(root.attributeValue("NodeName"));
			}		   
            getMultipeTreenodeNames(root);
			
			featureNameList = namelist;
			for(int j = 0; j < featureNameList.size(); j++) {
				featureNameList.get(j);
				//System.out.println(featureNameList);
			}
			
			//System.out.println(featureNameList);
			//System.out.println(root.getName());
			//System.out.println(root.getText());
			}catch (DocumentException e) {
				e.printStackTrace();
			}
		
		
		
		return featureNameList;
	}
	
	public List<String>Dom4jGetOrGroupFeatureList(){
		
		try{
			//获取SAXReader对象
			SAXReader reader = new SAXReader();
			//创建一个File类
			File file = new File("D:\\model.xml");
			Document doc = reader.read(file);
			//获取跟节点
			Element root = doc.getRootElement();
			if(root.attributeValue("NodeType").equals(OrGroup)) {
				namelist.add(root.attributeValue("NodeName"));
			}		
     
            getOrGroupTreenodeNames(root);
			
			featureNameList = namelist;
			for(int j = 0; j < featureNameList.size(); j++) {
				featureNameList.get(j);
				//System.out.println(featureNameList);
			}
			
			//System.out.println(featureNameList);
			//System.out.println(root.getName());
			//System.out.println(root.getText());
			}catch (DocumentException e) {
				e.printStackTrace();
			}
		return featureNameList;
	}
	
	public List<String>Dom4jGetAlternativeFeatureList(){
		
		try{
			//获取SAXReader对象
			SAXReader reader = new SAXReader();
			//创建一个File类
			File file = new File("D:\\model.xml");
			Document doc = reader.read(file);
			//获取跟节点
			Element root = doc.getRootElement();
			if(root.attributeValue("NodeType").equals(Alternative)) {
				namelist.add(root.attributeValue("NodeName"));
			}		
            getAlternativeTreenodeNames(root);
			
			featureNameList = namelist;
			for(int j = 0; j < featureNameList.size(); j++) {
				featureNameList.get(j);
				//System.out.println(featureNameList);
			}
			
			//System.out.println(featureNameList);
			//System.out.println(root.getName());
			//System.out.println(root.getText());
			}catch (DocumentException e) {
				e.printStackTrace();
			}
		return featureNameList;
	}
	
    public void getMultipeTreenodeNames(Element e) {
        Iterator iter = e.elementIterator();
        /*List nodes = e.elements("feature");
        for (Iterator it = nodes.iterator(); it.hasNext();) {
        	Element childEle = (Element) iter.next();
            if(childEle.attributeValue("NodeType").equals(Multipe)) {
				namelist.add(childEle.attributeValue("NodeName"));
			}	
           // namelist.add(childEle.attributeValue("NodeName"));
            if (childEle.nodeCount()==0) {
                continue;
            }
            else{
                getMultipeTreenodeNames(childEle);//遍历
            }
        }*/
        while (iter.hasNext()) {
            Element childEle = (Element) iter.next();
            if(childEle.attributeValue("NodeType").equals(Multipe)) {
				namelist.add(childEle.attributeValue("NodeName"));
			}	
           // namelist.add(childEle.attributeValue("NodeName"));
            if (childEle.nodeCount()==0) {
                continue;
            }
            else{
                getMultipeTreenodeNames(childEle);//遍历
            }

        }
    }
    
    public void getTreenodeNames(Element e,DefaultMutableTreeNode node) {
        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
            Element childEle = (Element) iter.next();
			namelist.add(childEle.attributeValue("NodeName"));	
           // namelist.add(childEle.attributeValue("NodeName"));
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
    
    public void getOrGroupTreenodeNames(Element e) {
        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
            Element childEle = (Element) iter.next();
            if(childEle.attributeValue("NodeType").equals(OrGroup)) {
				namelist.add(childEle.attributeValue("NodeName"));
			}	
           // namelist.add(childEle.attributeValue("NodeName"));

            if (childEle.nodeCount()==0) {
                continue;
            }
            else{
                getOrGroupTreenodeNames(childEle);//遍历
            }

        }
    }
    
    public void getAlternativeTreenodeNames(Element e) {
        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
            Element childEle = (Element) iter.next();
            if(childEle.attributeValue("NodeType").equals(Alternative)) {
				namelist.add(childEle.attributeValue("NodeName"));
			}	
           // namelist.add(childEle.attributeValue("NodeName"));
            if (childEle.nodeCount()==0) {
                continue;
            }
            else{
            	getAlternativeTreenodeNames(childEle);//遍历
            }

        }
    }

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	/*public static void main(String[] args){
		try{
			
		List<String> namelist = new ArrayList<>();
		//获取SAXReader对象
		SAXReader reader = new SAXReader();
		//创建一个File类
		File file = new File("D:\\model.xml");
		Document doc = reader.read(file);
		//获取跟节点
		Element root = doc.getRootElement();
		//namelist.add(root.getText());
		
		for(Iterator i = root.elementIterator();i.hasNext();) {
			Element element = (Element)i.next();	
			namelist.add(element.getText());
		}
		System.out.println(namelist);

		featureNameList = namelist;
		for(int i = 0; i < featureNameList.size(); i++) {
			featureNameList.get(i);			
		}
		//System.out.println(featureNameList);
		//System.out.println(root.getName());
		//System.out.println(root.getText());
		}catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}*/
}







/*
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jGetFeatureList {
	public void Dom4jGetFeatureList() {
		Document doc = getDocumnetFromText("D://model.xml");
		getRootElement(doc);
	}
	public static Document getDocumnetFromText(String xmlText) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText("D://model.xml");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return doc;
		}
	public static Element getRootElement(Document doc) {
		Element element = null;
		if(Objects.nonNull(doc)) {
			element = doc.getRootElement();
			System.out.println(element);
		}
		
		return element;
		
	}
}
*/