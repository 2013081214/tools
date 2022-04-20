package contact;

import org.dom4j.*;
import java.util.*;

import contact.ContactDAO;
import contact.DomFactory;

public class ContactDAOImpl implements ContactDAO {
	private Element feature;
	
	public Vector<Vector<String>> getAll() {
		Vector<Vector<String>> result=new Vector<Vector<String>>();
		List<Element> list = feature.elements("contact");
		for(int i=0;i<list.size();i++){
			Vector<String> v=new Vector<String>();
			v.add(list.get(i).attributeValue("NodeName"));
			v.add(list.get(i).attributeValue("NodeType"));
			
			result.add(v);
		}
		return result;
	}
	/**
	 * 
	 * @param name
	 * @param type
	 */
	public void addMutiple(String name, String type) {
		String id = UUIDUtils.getId();
		Element lyfe=feature.addElement("feature");
		lyfe.addAttribute("NodeName", name);
		lyfe.addAttribute("NodeType", type);
		DomFactory.save();
	}
	/**
	 * 
	 * @param id
	 */
	public void deleteRow(String id) {
		List<Element> elements=feature.elements();
		for(Element e:elements){
			String str=e.attributeValue("id");
			if(str.equals(id)){
				e.getParent().remove(e);
				
				//³Ö¾Ã»¯
				DomFactory.save();
				return;
			}
		}
	}
	/**
	 * 
	 * @param name
	 */
	public void addOrGroup(String name) {
		String id = UUIDUtils.getId();
		Element lyfe=feature.addElement("feature");
		lyfe.addAttribute("NodeName", name);
		lyfe.addAttribute("NodeType", "Mandatory");
		DomFactory.save();
	}
	/**
	 * 
	 * @param name
	 */
	public void addAlternative(String name) {
		String id = UUIDUtils.getId();
		Element lyfe=feature.addElement("feature");
		lyfe.addAttribute("NodeName", name);
		lyfe.addAttribute("NodeType", "Mandatory");
		DomFactory.save();
	}
	/**
	 * 
	 * @param name
	 */
	public void changeMulToOrGroup(String name) {
		String id = UUIDUtils.getId();
	}
	@Override
	public void delete(String name) {
		// TODO Auto-generated method stub
		
	}

}
