package xmlfactory;

import java.awt.List;
import java.util.Iterator;

import org.dom4j.*;
import org.dom4j.io.*;

public class formalXmlCheck {
    Document doc;
    Element root;
	public void checkTypes() {
		 try{
			 SAXReader saxReader=new SAXReader(); 
	         doc=saxReader.read("D:\\model.formal");     
	         root = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
	         
	         List nodes = (List) root.elements("types");      
	         for (Iterator it = ((java.util.List<Element>) nodes).iterator(); it.hasNext();) {      
	             Element types = (Element) it.next();      
	             for(Iterator it1=types.attributeIterator();it1.hasNext();){
		        	 Attribute attribute = (Attribute) it.next();
		        	 String text=attribute.getText();
		        	 System.out.println(text);
		         }
	         }
	         
	        
		 }catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
