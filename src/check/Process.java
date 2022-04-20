package check;

import java.util.ArrayList;

public class Process {
	private String processname;
	
	private String input;
	
	private String output;
	
	private String pre;
	
	private String post;
	
	public Process(String Processname, String Input, String Output, String Pre, String Post) {
		processname = Processname;
		input = Input;
		output = Output;
		pre = Pre;
		post = Post;
	}
	
	public String getProcessname() {
		return processname;
	}
	
	public void setProcessname(String Processname) {
		processname = Processname;
	}
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String Input) {
		input = Input;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String Output) {
		output = Output;
	}
	
	public String getPre() {
		return pre;
	}
	
	public void setPre(String Pre) {
		pre = Pre;
	}
	
	public String getPost() {
		return post;
	}
	
	public void setPost(String Post) {
		post = Post;
	}
}
