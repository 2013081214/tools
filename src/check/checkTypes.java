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
	         Element rootElm = doc.getRootElement();//ʹ��dom4j�ṩ��API���XML�ĸ��ڵ�
	         
	         System.out.println("Root element :" + doc.getRootElement().getName());
	         
	         Element feature = rootElm.element("feature");
	         System.out.println("��ǰ�ڵ�����ƣ���" + feature.getName());
	         
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
	 * ListNode������ǰ�ڵ�Ԫ���������е��ӽڵ�
	 */

	public void ListNodes(Element node) {
		System.out.println("��ǰ�ڵ�����ƣ���" + node.getName());
		// ��ȡ��ǰ�ڵ���������Խڵ�
		java.util.List<Attribute> list = node.attributes();
		// �������Խڵ�
		for (Attribute attr : list) {
			System.out.println(attr.getText() + "-----" + attr.getName()
			+ "---" + attr.getValue());
		}
		if (!(node.getTextTrim().equals(""))) {
			System.out.println("�ı����ݣ�������" + node.getText());
		}
		// ��ǰ�ڵ������ӽڵ������
		Iterator<Element> it = node.elementIterator();
		// ����
		while (it.hasNext()) {
			// ��ȡĳ���ӽڵ����
			Element e = it.next();
			// ���ӽڵ���б���
			ListNodes(e);
		}
	}

}
