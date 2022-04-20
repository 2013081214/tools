package xmlfactory;

import java.io.File;  
import java.util.ArrayList;  
import java.util.Collection;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
  
import javax.print.Doc;  
  
import org.dom4j.Document;  
import org.dom4j.Element;  
import org.dom4j.io.SAXReader; 

import xmlfactory.Contest;
  

public class ReadXmlcontext {  
  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
  
        try {  
            // 实例化一个类用于添加xml文件  
            List<Contest> list = new ArrayList<>();  
  
            SAXReader reader = new SAXReader();  
            Document doc = reader.read(new File("feature.xml"));  
            // 读取指定标签  
            Iterator<Element> eleit = doc.getRootElement().elementIterator("feature");  
  
            ArrayList newlist = new ArrayList(); // 创建新集合  
            while (eleit.hasNext()) {  
  
                Element ele = eleit.next();  
                Contest con = new Contest();
                con.setfeatureName(ele.attributeValue("name"));
                con.setInput(ele.elementText("inputs"));
                con.setOutput(ele.elementText("outputs"));
                con.setPre(ele.elementText("pre"));
                con.setPost(ele.elementText("post"));  
                list.add(con);  
  
                Iterator it = list.iterator(); // 通过老集合获取迭代器  
                while (it.hasNext()) { // 遍历老集合  
                    Object obj = it.next(); // 得到老集合的元素  
                    if (!newlist.contains(obj)) {  
                        newlist.add(obj);  
                    }  
                }  
  
            }  
  
        } catch (  
  
        Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
}  