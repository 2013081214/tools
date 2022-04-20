package contact;

import java.io.*;
import org.dom4j.*;
import org.dom4j.io.*;

public class DomFactory {
	private static Document dom;
	private static final String PATH=ClassLoader.getSystemResource("D://model.xml").getPath();
	static {
		System.out.println(PATH);
		File file = new File(PATH);
		try {
			dom = new SAXReader().read(file);
			System.out.println(dom);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private DomFactory() {
		
	}
	
	public static Document getDom() {
		return dom;
	}
	
	public static void save() {
		try {
			XMLWriter w = new XMLWriter(new FileWriter(PATH));
			w.write(dom);
			w.close();
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
