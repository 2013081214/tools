package contact;

import contact.ContactDAO;
import contact.ContactDAOImpl;

public class ContactDAOFactory {
	private ContactDAOFactory() {
		
	}
	
	public static ContactDAO getContactDAO() {
		return new ContactDAOImpl();
	}
}
