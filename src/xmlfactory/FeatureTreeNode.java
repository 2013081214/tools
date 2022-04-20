package xmlfactory;

public class FeatureTreeNode {
	private String NodeName;
	private String NodeType;
	
	public FeatureTreeNode(String name, String type) {
		this.NodeName = name;
		this.NodeType = type;
	}
	
	public String getName() {
		return NodeName;
	}
	
	public void setName(String Name) {
		this.NodeName = Name;
	}
	
	public String toString() {
		return NodeName;
	}
	
	public String getType() {
		return NodeType;
	}
	
	public void setType(String Type) {
		this.NodeType = Type;
	}
}
