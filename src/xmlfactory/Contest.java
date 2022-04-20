package xmlfactory;

public class Contest {  
    private String featureName;
    private String types;
    private String var;
    private String input;
    private String output;
    private String pre;
    private String post;
   
    /**
     * 
     * @param featureName
     * @param types
     * @param var
     * @param input
     * @param output
     * @param pre
     * @param post
     */
    public Contest(String featureName, String types, String var, String input, String output, String pre, String post) {  
        super();
        this.featureName = featureName;
        this.types = types;
        this.var = var;
        this.input = input;
        this.output = output;
        this.pre = pre;
        this.post = post;
    }  
  
    /**
     * 
     * @param featureName
     * @param types
     * @param var
     * @param input
     * @param output
     * @param pre
     * @param post
     */
    public Contest() {  
  
    }  
  
    public String getfeatureName() {  
        return featureName;  
    }  
  
    public void setfeatureName(String featureName) {  
        this.featureName = featureName;  
    }  
 
    public void setTypes(String types) {
    	this.types = types;
    }
    
    public String getTypes() {
    	return types;
    }
    
    public void setVar(String var) {
    	this.var = var;
    }
    
    public String getVar() {
    	return var;
    }
    
    public void setInput(String input) {
    	this.input = input;
    }
    
    public String getInput() {
    	return input;
    }
    
    public void setOutput(String output) {
    	this.output = output;
    }
    
    public String getOutput() {
    	return output;
    }
    
    public void setPre(String pre) {
    	this.pre = pre;
    }
    
    public String getPre() {
    	return pre;
    }
    
    public void setPost(String post) {
    	this.post = post;
    }
    
    public String getPost() {
    	return post;
    }
    	
    @Override  
    public String toString() {  
        // TODO Auto-generated method stub  
        return this.featureName;  
  
          
    }  
  
}  