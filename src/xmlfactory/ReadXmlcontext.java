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
            // ʵ����һ�����������xml�ļ�  
            List<Contest> list = new ArrayList<>();  
  
            SAXReader reader = new SAXReader();  
            Document doc = reader.read(new File("feature.xml"));  
            // ��ȡָ����ǩ  
            Iterator<Element> eleit = doc.getRootElement().elementIterator("feature");  
  
            ArrayList newlist = new ArrayList(); // �����¼���  
            while (eleit.hasNext()) {  
  
                Element ele = eleit.next();  
                Contest con = new Contest();
                con.setfeatureName(ele.attributeValue("name"));
                con.setInput(ele.elementText("inputs"));
                con.setOutput(ele.elementText("outputs"));
                con.setPre(ele.elementText("pre"));
                con.setPost(ele.elementText("post"));  
                list.add(con);  
  
                Iterator it = list.iterator(); // ͨ���ϼ��ϻ�ȡ������  
                while (it.hasNext()) { // �����ϼ���  
                    Object obj = it.next(); // �õ��ϼ��ϵ�Ԫ��  
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