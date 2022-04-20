package xmlfactory;

public class TreeNode 
{
	private String ParentId ;
	private String NodeId;
	private String NodeName;

	public TreeNode(String pid , String id, String name) 
	{
		this.ParentId = pid ;
		this.NodeId = id;
		this.NodeName = name;		
	}

	public String getPId() 
	{
		return ParentId;
	}

	public void setPId(String Id) 
	{
		this.ParentId = Id;
	}
	
	public String getId() 
	{
		return NodeId;
	}

	public void setId(String Id) 
	{
		this.NodeId = Id;
	}

	public void setName(String Name) 
	{
		this.NodeName = Name;
	}

	public String getName() 
	{
		return NodeName;
	}

	public String toString() 
	{
		return NodeName;
	}
}