package check;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Model;
import com.microsoft.z3.RealExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

import check.Z3ReverseSolution.TestFailedException;
import diagrameditor.DiagramSecond;

public class checkFunctionScenarioPath {
	static String big = ">";
	static String small = "<";
	static String bigequal = ">=";
	static String smallequal = "<=";
	static String multiplying = "*";
	static String divide = "/";
	static String sequence = ":seq of ";
	static String set = ":set of ";
	static HashMap map = new HashMap();
	final List<Map<String, ProcessNode>> tempRetList = new ArrayList<Map<String, ProcessNode>>();
	private static final String CircleErrNodeFeatureName = "error-circle-feature";
	private static final String CircleErrNodeProcessNamePrefix = "error-circle-node";
	static List<String> featureNodes;
	public static HashMap<String,List<String>> Seq = new HashMap<>();
	public static HashMap<String,List<String>> Set = new HashMap<>();
	public HashMap<String,List<String>> Type = new HashMap<>();
	
	public static HashMap<String,List<String>> GetSequence(){
		HashMap<String,List<String>> TypeArr = new HashMap<>();
		HashMap<String,List<String>> SeqArr = new HashMap<>();
		try{
			SAXReader saxReader=new SAXReader(); 
			Document doc=saxReader.read("D:\\model.formal");
			Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
			for(Iterator i = rootElm.elementIterator();i.hasNext();) {
				Element childEle = (Element) i.next();
				Element type = childEle.element("types");
				Element var = childEle.element("vars");
				String typeContent = type.getText();
				String varContent = var.getText();
				String[] typesStrArray = typeContent.split(";");
				String[] varsStrArray = varContent.split(";");
				for(int j = 0; j < typesStrArray.length; j++) {
					if(typesStrArray[j] != null) {
						String[] x = typesStrArray[j].split("=");
						String name = x[0];						
						String[] y = x[1].split(",");
						List<String> content = new ArrayList<String>();
						for(int k = 0; k < y.length; k++) {
							content.add(y[k]);
						}
						TypeArr.putIfAbsent(name, content);
					}					
				}
				for(int j = 0; j < varsStrArray.length; j++) {
					if(kmpMatch(varsStrArray[j],sequence) == true) {
						String[] x = varsStrArray[j].split(sequence);
						String name = x[0];
						String seqName = x[1];			            
						if(TypeArr!=null && TypeArr.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = TypeArr.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value.add(entry.getValue().toString());
				                if(seqName.equals(field)) {
				                	SeqArr.putIfAbsent(name, value);
				                }
				            }
				        }
					}
				}			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return SeqArr;		
	}
	
	public static HashMap<String,List<String>> GetSet(){
		HashMap<String,List<String>> TypeArr = new HashMap<>();
		HashMap<String,List<String>> SetArr = new HashMap<>();
		List<String> setList = new ArrayList<String>();
		try{
			SAXReader saxReader=new SAXReader(); 
			Document doc=saxReader.read("D:\\model.formal");
			Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
			for(Iterator i = rootElm.elementIterator();i.hasNext();) {
				Element childEle = (Element) i.next();
				Element type = childEle.element("types");
				Element var = childEle.element("vars");
				String typeContent = type.getText();
				String varContent = var.getText();
				String[] typesStrArray = typeContent.split(";");
				String[] varsStrArray = varContent.split(";");
				/*
				 * 将所有set放入一个list中，方便set嵌套查找
				 */
				for(int j = 0; j < varsStrArray.length; j++) {
					if(varsStrArray[j] != null) {
						if(kmpMatch(varsStrArray[j],set) == true) {
							String[] x = varsStrArray[j].split(set);
							String name = x[0];
							setList.add(name);
						}						
					}
				}
				/*
				 * 将type里的数据单独进行存储
				 */
				for(int j = 0; j < typesStrArray.length; j++) {
					if(typesStrArray[j] != null) {
						String[] x = typesStrArray[j].split("=");
						String name = x[0];							
						String[] y = x[1].split(",");
						List<String> content = new ArrayList<String>();
						for(int k = 0; k < y.length; k++) {
							content.add(y[k]);
						}
						TypeArr.putIfAbsent(name, content);
					}					
				}
				/*
				 *组合生成set集合，条件判定使用 
				 */				
				for(int j = 0; j < varsStrArray.length; j++) {
					Boolean result = false;
					if(kmpMatch(varsStrArray[j],set) == true) {
						String[] x = varsStrArray[j].split(set);
						String name = x[0];
						String setName = x[1];			            
						if(TypeArr!=null && TypeArr.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = TypeArr.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value.add(entry.getValue().toString());
				                if(setName.equals(field) && result == false) {
				                	result = true;
				                	SetArr.putIfAbsent(name, value);
				                	break;
				                }else{
				                	int size = setList.size();
									for(int k = 0; k < size; k++) {
										List<String> value1 = new ArrayList<>();
										List<String> value2 = new ArrayList<>();
										String hasSet = setList.get(k);
										if(setName.equals(hasSet)) {
											Iterator<Map.Entry<String, List<String>>> has = SetArr.entrySet().iterator();
											while(has.hasNext()) {
												Map.Entry<String,List<String>> entry1 = has.next();
												String field1 = entry1.getKey().toString();
												value1=entry.getValue();
												int zuhe = value1.size();
												value2 = perm(value1);
												SetArr.putIfAbsent(name, value2);
												result = true;
												break;
											}
										}
									}
				                }
				                if(result == true)
				                	break;
				            }
				        }
					}
				}			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return SetArr;		
	}
	
	/*
	 * 
	 */
	public static List<String> perm(List<String> v) 
	{ 
		System.out.println(v);
		List<String> result = new ArrayList<String>(); 
		
		String[] str = new String[v.size()];
		for(int j =0; j < v.size(); j++) {
			str[j] = v.get(j);
		}
		int nCnt = v.size();
		int nBit = (0xFFFFFFFF >>> (32 - nCnt));
		for (int i = 1; i <= nBit; i++) {
			String x = new String();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					x += str[j];					
				}
			}
			result.add(x);
		}
		System.out.println(result);
      	return result;

	} 

	/*
	 * 
	 */
	public static HashMap<ScenarioPath, String> checkFunctionScenarioPath(){
		HashMap<String, HashMap<ScenarioPath, String>> pathMap = new HashMap<>();
		HashMap<ScenarioPath, String> processPath = new HashMap<>();
		HashMap<Scenario, String> SingleScenarioPath = new HashMap<>();
		try{
			SAXReader saxReader=new SAXReader(); 
			Document doc=saxReader.read("D:\\model.formal");
			Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
			for(Iterator i = rootElm.elementIterator();i.hasNext();) {
				Element childEle = (Element) i.next();
				String featureName = childEle.attributeValue("name");
				List nodes = childEle.elements("process");
				for (Iterator it = nodes.iterator(); it.hasNext();) {
					String processName = "membership";
				    Element elm = (Element) it.next();
				    Element input = elm.element("inputs");
				    String inputs = input.getText();
				    System.out.println(inputs);
	            	String[] inputsStrArray = inputs.split(";");
	            	Element output = elm.element("outputs");
	            	String outputs = output.getText();
				    System.out.println(outputs);
	            	String[] outputsStrArray = outputs.split(";");
	            	//System.out.println(outputsStrArray[0]);
	            	Element post = elm.element("post");
	            	String posts = post.getText();
	            	//System.out.println(posts);
	            	String[] postStrArray = posts.split("if|elseif|else");
	            	//System.out.println(postStrArray[1]);	            	
	            	//判断post判定条件中是否出现input的变量
	            	for(int k = 1; k < postStrArray.length;k++) {
	            		for(int p = 0; p < inputsStrArray.length; p++) {
	            			boolean haveInput = kmpMatch(postStrArray[k],inputsStrArray[p]);
	            			if(haveInput == true) {
	        	            	/*String anchorStart = "{print\""; //锚点
	        	                String anchorEnd = "\"}"; //锚点
	        	                String str =postStrArray[1];
	        	                System.out.println(str);
	        	                for(int out = 1; out < postStrArray.length-1; out++) {
	        	                	while ((str = postStrArray[out]) != null) {
	        	                		if (str.contains(anchorStart)) {
	        	                			int startIndex = str.indexOf(anchorStart);
	        	                			int endIndex = str.lastIndexOf(anchorEnd);
	        	                			//选取outputs中输出的部分进行比对，排除判断条件的干扰
	        	                			String name = str.substring(startIndex + anchorStart.length(), endIndex);
	        	                			postStrArray[out] = name;
	        	                			System.out.println("name=" + name);
	        	                		}
	        	                	}
	        	                }*/
	            				for(int w = 0; w < outputsStrArray.length; w++) {
		            				boolean haveOutput = kmpMatch(postStrArray[k],outputsStrArray[w]);
		            				if(haveOutput == true) {
		            					processPath.put(new ScenarioPath(inputsStrArray[p],postStrArray[k],outputsStrArray[w]),processName);           				
		            				}
		            			}            				
	            			}            			
	            		}
	            		//pathMap.put(featureName,processPath);
	            	}
	            	Set<ScenarioPath> path1 = processPath.keySet();
	            	Iterator<ScenarioPath> iterator = path1.iterator();
	            	while(iterator.hasNext()) {
	            		ScenarioPath next = iterator.next();
	            		System.out.println(processPath.get(next)+":"+ next.getInput()+"->"+next.getPost()+"->"+next.getOutput());
	            	}
	            	//return processPath;
				}				
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return processPath;
	}
	/*
	 * 
	 */
	public static HashMap<Scenario, String> GetSinglePath(){
		HashMap<String, HashMap<ScenarioPath, String>> pathMap = new HashMap<>();
		HashMap<ScenarioPath, String> processPath = new HashMap<>();
		HashMap<Scenario, String> SingleScenarioPath = new HashMap<>();
		List<String> FeatureNodes = new ArrayList<String>();
		try{
			SAXReader saxReader=new SAXReader(); 
			Document doc=saxReader.read("D:\\model.formal");
			Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
			for(Iterator i = rootElm.elementIterator();i.hasNext();) {
				Element childEle = (Element) i.next();
				String featureName = childEle.attributeValue("name");				
				FeatureNodes.add(featureName);
				List nodes = childEle.elements("process");
				for (Iterator it = nodes.iterator(); it.hasNext();) {
				    Element elm = (Element) it.next();
					String processName = elm.attributeValue("name");
				    Element input = elm.element("inputs");
				    String inputs = input.getText();
	            	String[] inputsStrArray = inputs.split(";");
	            	Element output = elm.element("outputs");
	            	String outputs = output.getText();
	            	String[] outputsStrArray = outputs.split(";");	            	
	            	//System.out.println(outputsStrArray[0]);
	            	Element post = elm.element("post");
	            	//获取post里的所有路径
	            	List paths = post.elements("scenarios");
	            	for (Iterator item = paths.iterator(); item.hasNext();) {
	            		Element Scenarios = (Element) item.next();
	            		Element C = Scenarios.element("C");
	            		String Conditon = C.getText();
	            		ArrayList<String> inputVar = new ArrayList();
	            		for(int k = 0 ; k< inputsStrArray.length;k++) {
	            			int result1 = Conditon.indexOf(inputsStrArray[k]);
		            		//和输入变量进行匹配，查找条件中存在的变量
		            		if(result1 != -1) {
		            			String start = inputsStrArray[k];
		            			inputVar.add(start);
		            		}
		            	}
	            		Element D = Scenarios.element("D");
	            		String Data = D.getText();
	            		ArrayList<String> outputVar = new ArrayList();
		            	for(int j = 0 ; j< outputsStrArray.length;j++) {
		            		//和输出变量进行匹配，查找条件中存在的变量
		            		if(kmpMatch(Data,outputsStrArray[j]) == true) {		            			
		            			String end = outputsStrArray[j];
		            			outputVar.add(end);
		            		}
		            	}
		            	SingleScenarioPath.put(new Scenario(inputVar, Conditon, Data, outputVar), featureName);
	            	}
	            	
				}				
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Set<Scenario> path1 = SingleScenarioPath.keySet();
    	Iterator<Scenario> iterator = path1.iterator();
    	while(iterator.hasNext()) {
    		Scenario next = iterator.next();
    		System.out.println(SingleScenarioPath.get(next)+":"+ next.getInputList()+"->"+next.getCondition()+"->"+next.getDefinition()+"->"+next.getOutputList());
    	}
    	featureNodes=FeatureNodes;
		return SingleScenarioPath;
	}
	/*
	 * 
	 */
	public static HashMap<ScenarioLongPath,String> GetSingleFeatureScenarioPath(HashMap<Scenario, String> singleScenarioPath) {		
		HashMap<ScenarioLongPath,String> LongPath = new HashMap<>();
		HashMap<Scenario, String> ScenarioPath = new HashMap<>();
		for(int i = 0; i < featureNodes.size();i++) {
			Set<Scenario> path = singleScenarioPath.keySet();
			Iterator<Scenario> iterator = path.iterator();
			while(iterator.hasNext()) {
				Scenario next = iterator.next();
				if(singleScenarioPath.get(next).equals(featureNodes.get(i))) {
					ScenarioPath.put(next, featureNodes.get(i));
				}				
			}
			Set<Scenario> path1 = ScenarioPath.keySet();
			Iterator<Scenario> iterator1 = path1.iterator();
			while(iterator1.hasNext()) {				
				Scenario next1 = iterator1.next();
				ArrayList initialinput = next1.InputList;
				ArrayList out = next1.OutputList;
				Set<Scenario> path2 = ScenarioPath.keySet();
				Iterator<Scenario> iterator2 = path2.iterator();
				while(iterator2.hasNext()) {
					Scenario next2 = iterator2.next();
					ArrayList input =  next2.InputList;
					ArrayList finaloutput = next2.OutputList;
					if(checkDiffrent(out,input)==true) {
						ArrayList<String> con = new ArrayList();
						ArrayList<String> define = new ArrayList();
						for(int i1 = 0; i1 < input.size(); i1++) {
							initialinput.add(input.get(i1));
							for(int k =0;k<initialinput.size()-1;k++){
					            for(int j=initialinput.size()-1;j>k;j--){
					                if(initialinput.get(k).equals(initialinput.get(j)))
					                	initialinput.remove(j);
					            }
					        }
						}
						con.add(next1.getCondition());
						con.add(next2.getCondition());
						define.add(next1.getDefinition());
						define.add(next2.getDefinition());
						LongPath.put(new ScenarioLongPath(initialinput,con,define,finaloutput), featureNodes.get(i));
					}
				}
				//System.out.println(ScenarioPath.get(next1)+":"+ next1.getInputList()+"->"+next1.getCondition()+"->"+next1.getDefinition()+"->"+next1.getOutputList());
			}
			ScenarioPath.clear();
			
		}
		Set<ScenarioLongPath> path11 = LongPath.keySet();
		Iterator<ScenarioLongPath> it = path11.iterator();
		while(it.hasNext()) {
			ScenarioLongPath longPath = it.next();
			System.out.println(LongPath.get(longPath)+":"+ longPath.getInputList()+"->"+longPath.getCondition()+"->"+longPath.getDefinition()+"->"+longPath.getOutputList());
		}
		return LongPath;
	}
	/*
	 * 检查不同的特征路径是否有重复的范围
	 */
	public static List CheckScope(HashMap<ScenarioLongPath,String> longPath){
		//Z3ReverseSolution p = new Z3ReverseSolution();
		List list = new ArrayList();
		Set<ScenarioLongPath> path = longPath.keySet();
		Iterator<ScenarioLongPath> it = path.iterator();
		//LinkedHashMap<ScenarioLongPath,String> result1 = new LinkedHashMap<>();
		BoolExpr left = null;
		BoolExpr right = null;
		HashMap<String, String> cfg = new HashMap<String, String>();  
        cfg.put("model", "true");  
		Context ctx = new Context(cfg);
		while(it.hasNext()) {			
			ScenarioLongPath Path = it.next();
			String FirstFeature = longPath.get(Path);
			ArrayList input = Path.getInputList();
			ArrayList output = Path.getOutputList();
			ArrayList condition = Path.Condition;
			ArrayList define = Path.Definition;
			Set<ScenarioLongPath> path1 = longPath.keySet();
			Iterator<ScenarioLongPath> itr = path1.iterator();
			while(itr.hasNext()) {
				ScenarioLongPath Path1 = itr.next();
				String SecondFeature = longPath.get(Path1);
				ArrayList sameInput = Path1.getInputList();
				ArrayList sameOutput = Path1.getOutputList();
				boolean feature = FirstFeature.equals(SecondFeature);
				boolean out = sameOutput.equals(output);
				if(feature == false && out == false && input.size() == sameInput.size()) {
					ArrayList condition1 = Path1.Condition;
					ArrayList define1 = Path1.Definition;				
					try{
						Z3ReverseSolution p = new Z3ReverseSolution();
						Seq = GetSequence();
				    	Set = GetSet();
						boolean result = p.findModel(ctx,condition,define,condition1,define1,Seq,Set);
			            if(result == true){
			            	System.out.println("There is overlap in scope");
			            	String p1 = longPath.get(Path);
			            	String str= String.join(",", Path.getInputList());
			            	String str2= String.join(",", Path.getCondition());
			            	String str3 = String.join(",", Path.getDefinition());			            	
			            	String str4 = String.join(",", Path.getOutputList());
			            	p1 = (p1+":"+str+str2+str3+str4);
			            	String p2 = longPath.get(Path1);
			            	String str1= String.join(",", Path1.getInputList());
			            	String str12= String.join(",", Path1.getCondition());
			            	String str13 = String.join(",", Path1.getDefinition());			            	
			            	String str14 = String.join(",", Path1.getOutputList());
			            	p2 = (p2+":"+str1+str12+str13+str14);
			            	list.add(p1);
			            	list.add(p2);
			            	String suggest = "Paths overlap, please re-edit the range";
			            	list.add(suggest);
			            	System.out.println(longPath.get(Path)+":"+ Path.getInputList()+"->"+Path.getCondition()+"->"+Path.getDefinition()+"->"+Path.getOutputList());
			            	System.out.println(longPath.get(Path1)+":"+ Path1.getInputList()+"->"+Path1.getCondition()+"->"+Path1.getDefinition()+"->"+Path1.getOutputList());			            	
			            }  
			            else if(result == false)  
			                    System.out.println("no scope question");  
			            else   
			                System.out.println("unknow");  		              
			        }catch(Exception e){  
			            System.out.println("z3 exception");  
			            e.printStackTrace();  
			        }    
				}
			}
		}       
		return list;
		
	}
    /*
     * Z3模型检测
     */
    Model check(Context ctx, BoolExpr f, Status sat) throws TestFailedException
    {
        Solver s = ctx.mkSolver();
        s.add(f);
        if (s.check() != sat) {
        	System.out.println("that is all");
            throw new TestFailedException();
        }
        if (sat == Status.SATISFIABLE)
            return s.getModel();
        else
            return null;
    }
    /*
     * 错误提示
     */
    class TestFailedException extends Exception
    {
        public TestFailedException()
        {
            super("Check FAILED");
        }
    };
	/*
	 * 比对两个ArrayList是否一致
	 */
	private static boolean checkDiffrent(ArrayList list, ArrayList list1) { 
		list.sort(Comparator.comparing(String::hashCode));  
		list1.sort(Comparator.comparing(String::hashCode)); 
		return list.toString().equals(list1.toString());
	}
	/*
	 * 
	 */
	public static String CheckSinglePathIO(HashMap<ScenarioPath, String> processPath) {
		String result = null;
		Set<ScenarioPath> path1 = processPath.keySet();
    	String[] inputsStrArray = null;
    	String[] outputsStrArray = null;
    	Iterator<ScenarioPath> iterator = path1.iterator();
    	
    	while(iterator.hasNext()) {
    		int i = 0;
    		ScenarioPath next = iterator.next();
    		inputsStrArray[i] = next.getInput();
    		outputsStrArray[i] = next.getOutput();
    		i++;
    	}
    	
		return result;		
	}
	
	/*
	 * 
	 */
	public static HashMap<HashMap<String, String>,HashMap<String, Integer>> SingleFeaturePath(HashMap<ScenarioPath, String> processPath){
		HashMap<HashMap<String, String>,HashMap<String, Integer>> singleFeaturePath = new HashMap();
		Set<ScenarioPath> path1 = processPath.keySet();
    	Iterator<ScenarioPath> iterator = path1.iterator();
    	//存放每次合并的结果路径
    	String result = null;
    	//存放首位元素用于匹配路径
    	String[] inputsStrArray = null;
    	String[] outputsStrArray = null;
    	//路径设置索引判断匹配次数
    	int index = 0;
    	//加载所有单一路径的输入输出
    	while(iterator.hasNext()) {
    		int i = 0;
    		ScenarioPath next = iterator.next();
    		inputsStrArray[i] = next.getInput();
    		outputsStrArray[i] = next.getOutput();
    		i++;
    	}
    	//通过IO进行路径匹配
    	for(int j = 0; j < inputsStrArray.length; j++) {
    		for(int k =0; k < outputsStrArray.length; k++) {
    			if(outputsStrArray[k].equals(inputsStrArray[j])) {
    				String firPath = null;
    				String secPath = null;
    				while(iterator.hasNext()) {
    		    		int i = 0;
    		    		ScenarioPath next = iterator.next();
    		    		if(j == i) {
    		    			firPath = next.getInput()+next.getPost()+next.getOutput();
    		    		}
    		    		if(k == i) {
    		    			secPath = next.getInput()+next.getPost()+next.getOutput();
    		    		}
    		    		i++;
    		    	}
    				result = firPath + secPath;
    				//singleFeaturePath.put(HashMap<HashMap<inputsStrArray[j], outputsStrArray[k]>,HashMap<rsult, index>>);
    			}
    		}   		
    	}
    	//singleFeaturePath.put(HashMap<inputsStrArray[0],outputsStrArray[0]>, HashMap);
		return singleFeaturePath;
		
	}
	
	public static void PrintSingleProcessInputOutputRela(Map<String, List<ProcessNode>> processMap) {
		for (final String processName : processMap.keySet()) {
			for (final ProcessNode node : processMap.get(processName)) {
				final String nodeStr = "[" + String.join(",", node.input) + "]" + node.processName + node.inputIndex + node.outputIndex + "["
					+ String.join(",", node.output) + "]";
				//printToConsole(nodeStr, true);
			}
		}
	}
	
	public static List<Map<String, ProcessNode>> GetSingleFeatureAllLinkList(Map<String, List<ProcessNode>> processMap, boolean isPrint) {
		final List<ProcessNode> firstProcessNode = GetFirstProcessNode(processMap);
		final List<Map<String, ProcessNode>> tempRetList = new ArrayList<Map<String, ProcessNode>>();
		for (final ProcessNode node : firstProcessNode) {
			final Map<String, ProcessNode> nodeLinkMap = new LinkedHashMap<String, ProcessNode>();
			final List<Map<String, ProcessNode>> linkMapList = GetSingleProcessNodeLinkMapList(node, nodeLinkMap);
			tempRetList.addAll(linkMapList);
			if (isPrint) {
				PrintSingleFeatureLinkMapList(linkMapList);
			}
		}
		// 移除环状链路
		final List<Map<String, ProcessNode>> retList = new ArrayList<Map<String, ProcessNode>>();
		for (final Map<String, ProcessNode> linkMap : tempRetList) {
			if (!linkMap.keySet().stream().anyMatch(a -> a.startsWith(CircleErrNodeProcessNamePrefix))) {
				retList.add(linkMap);// 此链路不是循环链路，可以使用
			}
		}
		return retList;
	}
	
	public static List<Map<String, ProcessNode>> GetSingleProcessNodeLinkMapList(ProcessNode node, Map<String, ProcessNode> nodeLinkMap) {
		final List<Map<String, ProcessNode>> ret = new ArrayList<Map<String, ProcessNode>>();
		ret.add(nodeLinkMap);
		if (nodeLinkMap.containsKey(node.processName)) {
			// 出现循环节点，
			final ProcessNode circleErrNode = new ProcessNode();
			circleErrNode.setFeatureName(CircleErrNodeFeatureName);
			circleErrNode.setProcessName(CircleErrNodeProcessNamePrefix + node.processName);
			nodeLinkMap.put(CircleErrNodeProcessNamePrefix + node.processName, circleErrNode);
			return ret;
		}

		nodeLinkMap.put(node.processName, node);
		if (node.nextNode.size() <= 0) {
			return ret;// 没有后继节点了
		}
		for (final ProcessNode nextNode : node.nextNode) {
			final LinkedHashMap<String, ProcessNode> nextNodeLinkMap = CopyNodeLinkMap(nodeLinkMap);
			final List<Map<String, ProcessNode>> nextNodeRet = GetSingleProcessNodeLinkMapList(nextNode, nextNodeLinkMap);
			ret.addAll(nextNodeRet);
		}
		ret.remove(nodeLinkMap);// 移除掉重复的子链路
		return ret;
	}
	
	public static void PrintSingleFeatureLinkMapList(List<Map<String, ProcessNode>> linkMapList) {
		for (final Map<String, ProcessNode> linkMap : linkMapList) {
			final String linkStr = String.join("->", linkMap.values().stream().map(a -> {
				final String inputStr = "[" + String.join(",", a.input) + "]";
				final String outputStr = "[" + String.join(",", a.output) + "]";
				final String tempStr = inputStr + a.processName + a.inputIndex + a.outputIndex + outputStr;
				return tempStr;
			}).collect(Collectors.toList()));
			// 打印链路
			//printToConsole(linkStr, true);
		}
	}
	
	public static LinkedHashMap<String, ProcessNode> CopyNodeLinkMap(Map<String, ProcessNode> nodeLinkMap) {
		final LinkedHashMap<String, ProcessNode> newNodeLinkMap = new LinkedHashMap<String, ProcessNode>();
		for (final String key : nodeLinkMap.keySet()) {
			newNodeLinkMap.put(key, nodeLinkMap.get(key));
		}
		return newNodeLinkMap;
	}
	
	public static List<ProcessNode> GetFirstProcessNode(Map<String, List<ProcessNode>> processMap) {
		final List<ProcessNode> firstProcessNode =
			processMap.values().stream().flatMap(a -> a.stream()).filter(a -> a.preNode.size() <= 0).collect(Collectors.toList());
		return firstProcessNode;
	}
	
	 public static int[] getNextArray(char[] t) {
	        int[] next = new int[t.length];
	        //System.out.println(t.length);
	        next[0] = -1;
	        next[1] = 0;
	        int k;
	        for (int j = 2; j < t.length; j++) {
	            k=next[j-1];
	            while (k!=-1) {
	                if (t[j - 1] == t[k]) {
	                    next[j] = k + 1;
	                    break;
	                }
	                else {
	                    k = next[k];
	                }
	                next[j] = 0;  //当k==-1而跳出循环时，next[j] = 0，否则next[j]会在break之前被赋值
	            }
	        }
	        return next;
	    }

	    /**
	     * 对主串s和模式串t进行KMP模式匹配
	     * @param s 主串
	     * @param t 模式串
	     * @return 若匹配成功，返回t在s中的位置（第一个相同字符对应的位置），若匹配失败，返回-1
	     */
	    public static boolean kmpMatch(String s, String t){
	        char[] s_arr = s.toCharArray();
	        char[] t_arr = t.toCharArray();
	        int[] next = getNextArray(t_arr);
	        int i = 0, j = 0;
	        while (i<s_arr.length && j<t_arr.length){
	            if(j == -1 || s_arr[i]==t_arr[j]){
	                i++;
	                j++;
	            }
	            else
	                j = next[j];
	        }
	        if(j == t_arr.length)
	            return true;
	        else
	            return false;
	    }	   
	    
	    public static void main(String[] args) {	    	
	    	CheckScope(GetSingleFeatureScenarioPath(GetSinglePath()));
	    	GetSequence();
	    }

}
