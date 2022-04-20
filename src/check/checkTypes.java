package check;

import java.awt.List;
import java.util.*;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class checkTypes {
	public void checkTypes() {
		try{
			 SAXReader saxReader=new SAXReader(); 
	         Document doc=saxReader.read("D:\\model.formal");
	         Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
	         
	         System.out.println("Root element :" + doc.getRootElement().getName());
	         
	         Element feature = rootElm.element("feature");
	         System.out.println("当前节点的名称：：" + feature.getName());
	         
	         Element typesElm = feature.element("types");
	         ListNodes(typesElm);
	         
	         Element varsElm = feature.element("vars");
	         ListNodes(varsElm);
	         
	         Element constsElm = feature.element("consts");
	         ListNodes(constsElm);

	         for (Iterator it = ((java.util.List<Element>) typesElm).iterator(); it.hasNext();) {      
	             Element types1 = (Element) it.next();      
	             for(Iterator it1=types1.attributeIterator();it1.hasNext();){
		        	 Attribute attribute = (Attribute) it.next();
		        	 String text=attribute.getText();
		        	 System.out.println(attribute.getData());
		        	 System.out.println(text);
		         }
	         }
	         
	        
		 }catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	/*
	 * ListNode遍历当前节点元素下面所有的子节点
	 */

	public void ListNodes(Element node) {
		System.out.println("当前节点的名称：：" + node.getName());
		// 获取当前节点的所有属性节点
		java.util.List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attr : list) {
			System.out.println(attr.getText() + "-----" + attr.getName()
			+ "---" + attr.getValue());
		}
		if (!(node.getTextTrim().equals(""))) {
			System.out.println("文本内容：：：：" + node.getText());
		}
		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		// 遍历
		while (it.hasNext()) {
			// 获取某个子节点对象
			Element e = it.next();
			// 对子节点进行遍历
			ListNodes(e);
		}
	}

}
