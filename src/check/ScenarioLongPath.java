package check;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScenarioLongPath {
	ArrayList InputList=new ArrayList();
	ArrayList Condition=new ArrayList();
	ArrayList Definition=new ArrayList();
	ArrayList OutputList=new ArrayList();
	
	public ScenarioLongPath(ArrayList inputList,ArrayList condition, ArrayList definition,ArrayList outputList) {
		InputList = inputList;
		Condition = condition;
		Definition = definition;
		OutputList = outputList;
	}
	
	public ArrayList getCondition() {
		return Condition;
	}
	
	public void setInput(ArrayList condition) {
		Condition = condition;
	}
	
	public ArrayList getDefinition() {
		return Definition;
	}
	
	public void setOutput(ArrayList definition) {
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
