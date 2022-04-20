package testcc;

import java.awt.*;
import java.awt.List;
import java.util.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.*;
import org.dom4j.io.*;

import java.awt.event.*;
import java.io.File;

import xmlfactory.Featuredatamodel;

public class mini {	
	static String filepos = "D:\\model.formal";
	public static void inited() throws DocumentException {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File("D:\\model.formal"));
			Element root = doc.getRootElement();
		    System.out.println("根节点：" + root.getName()); // 拿到根节点的名称
		    Element featurenameEle = root.element("feature");
            String name2 = featurenameEle.attributeValue("name");
            System.out.println("featurename:" + name2); 
            Element typesEle1 = featurenameEle.element("types");
            System.out.println("types:" + typesEle1.elementText("typesEle1"));
            
	        Iterator Featurename = root.elementIterator("feature"); ///获取2级节点

	        System.out.println("name:"+Featurename);  
	           while (Featurename.hasNext()) {
	               Element FeaturenameEle = (Element) Featurename.next();
	   	           String text=FeaturenameEle.element("types").attributeValue("name");
	               String name = FeaturenameEle.elementText("FeaturenameEle"); 
                   //String index = FeaturenameEle.elementTextTrim("index");
	               System.out.println("name:" + text); 
	               Iterator types = FeaturenameEle.elementIterator("types"); //获取3级节点
	               System.out.println("name:" + name); 
                   //System.out.println("index:" + index);
	               while (types.hasNext()) {
	                   Element typesEle = (Element) types.next();
	                   String name1 = typesEle.elementText("typesEle"); 
	                   System.out.println("name1"+name1);
	              }
	          }
	}
	public static void main(String[] args) throws DocumentException {
		inited();
	}
}

