package check;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScenarioPath {
	private String Input;
	private String Output;
	private String Post;
	
	public ScenarioPath(String input,String post, String output) {
		Input = input;
		Post = post;
		Output = output;
	}
	
	public String getInput() {
		return Input;
	}
	
	public void setInput(String input) {
		Input = input;
	}
	
	public String getOutput() {
		return Output;
	}
	
	public void setOutput(String output) {
		Output = output;
	}
	
	public String getPost() {
		return Post;
	}
	
	public void setPost(String post) {
		Post = post;
	}
}
