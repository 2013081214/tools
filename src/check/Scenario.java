package check;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Scenario {
	ArrayList InputList=new ArrayList();
	private String Condition;
	private String Definition;
	ArrayList OutputList=new ArrayList();
	
	public Scenario(ArrayList inputList,String condition, String definition,ArrayList outputList) {
		InputList = inputList;
		Condition = condition;
		Definition = definition;
		OutputList = outputList;
	}
	
	public String getCondition() {
		return Condition;
	}
	
	public void setInput(String condition) {
		Condition = condition;
	}
	
	public String getDefinition() {
		return Definition;
	}
	
	public void setOutput(String definition) {
		Definition = definition;
	}
	
	public ArrayList getInputList() {
		return InputList;
	}
	
	public void setInputList(ArrayList inputList) {
		InputList = inputList;
	}
	
	public ArrayList getOutputList() {
		return OutputList;
	}
	
	public void setOutputList(ArrayList outputList) {
		OutputList = outputList;
	}
}
