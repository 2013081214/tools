package xmlfactory;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

public abstract class JTreeXML
{
    private static final DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    static
    {
        dbf=DocumentBuilderFactory.newInstance();
        try
        {
            db=dbf.newDocumentBuilder();
        }
        catch(ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public JTreeXML()
    {}

    public static Document getDocument(String xmlPath)
    {
        InputStream is=JTreeXML.class.getResourceAsStream("D:\\model.xml");
        if(is == null)
        {
            System.out.println("xmlPath[" + xmlPath + "]²»´æÔÚ£¡");
            return null;
        }

        return getDocument(is);
    }

    public static Document getDocument(InputStream is)
    {
        Document document=null;
        try
        {
            document=(Document) db.parse(is);
        }
        catch(SAXException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return document;
    }

    public abstract void dealElement(Element element, int index);

    public void parseTag(Document document, String tagName)
    {
        NodeList nodeList=((org.w3c.dom.Document) document).getElementsByTagName(tagName);
        for(int index=0; index < nodeList.getLength(); index++)
        {
            Element ele=(Element) nodeList.item(index);
            dealElement(ele, index);
        }
    }

    public void parseTag(Element element, String tagName)
    {
        NodeList nodeList=((org.w3c.dom.Document) element).getElementsByTagName(tagName);
        for(int index=0; index < nodeList.getLength(); index++)
        {
            Element ele=(Element) nodeList.item(index);
            dealElement(ele, index);
        }
    }

}

	