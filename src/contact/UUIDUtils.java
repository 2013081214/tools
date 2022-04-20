package contact;

import java.util.UUID;
 
public class UUIDUtils {


	public static String getId() {
		String id=UUID.randomUUID().toString().replace("-", "");
		return id;
	}
}