package xmlfactory;

public class Informalxml {
	
	private String featurename;
	private String database;
	private String constraint;
	private String operation;
	
	public Informalxml() {
		
	}
	public Informalxml(String featurename, String database, String constraint, String operation) {
		this.featurename = featurename;
		this.database = database;
		this.constraint = constraint;
		this.operation = operation;
	}
	
	public String getFeaturename() {
		return featurename;
	}
	public void setFeaturename(String featurename) {
		this.featurename = featurename;
	}
	
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String featurename) {
		this.database = database;
	}
	
	public String getConstraint() {
		return constraint;
	}
	public void setConstraint(String featurename) {
		this.constraint = constraint;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String featurename) {
		this.operation = operation;
	}
	

}
