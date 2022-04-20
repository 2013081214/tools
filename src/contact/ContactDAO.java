package contact;

import java.util.Vector;

public interface ContactDAO {
	public abstract Vector<Vector<String>> getAll();

	public abstract void addMutiple(String name, String type);
	public abstract void addOrGroup(String name);
	public abstract void addAlternative(String name);
	
	public abstract void delete(String name);
}
