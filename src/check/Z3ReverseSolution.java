package check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.microsoft.z3.*;

import check.JavaExample.TestFailedException;

public class Z3ReverseSolution {
	static String big = ">";
	static String small = "<";
	static String bigequal = ">=";
	static String smallequal = "<=";
	static String multiplying = "*";
	static String divide = "/";
	//Sequence操作符号
	static String hd = "hd(";
	static String tl = "tl(";
	static String conc = "conc(";
	static String len = "len(";
	//Set操作符号
	static String inset = " inset ";
	static String notin = " notin ";
	static String get = "get(";
	static String inter = "inter(";
	static String diff = "diff(";
	
	static int upperLimit = 999;
	static int lowerLimit = -999;
	
	
	class TestFailedException extends Exception
    {
        public TestFailedException()
        {
            super("Check FAILED");
        }
    };
	public void Z3ReverseSolution() {
		
	}
	
	public String GetPostCondition(String s) {
		String result = null;
		String[] condition = s.split("(|>|<|)");
		for(int i = 0; i < condition.length; i++) {
			//if
		}
		
		return result;
	}
	
	public void Bigger() {
		
	}
	
	public void Smaller() {
		
	}
	

	public boolean findModel(Context ctx, ArrayList con, ArrayList d,ArrayList con1, ArrayList d1, HashMap<String,List<String>> s1, HashMap<String,List<String>> s2) throws TestFailedException
    {
		IntExpr xyz = ctx.mkIntConst("xyzzzzzzz");
        IntExpr one = ctx.mkInt(1);
		BoolExpr left = ctx.mkGt(xyz,one);
		BoolExpr right = ctx.mkGt(xyz,one);
		for(int o = 0; o < 2; o++) {
			if(o == 0) {
				String a = "aaa";
				for(int j = 0 ; j < con.size(); j++) {
		    		String tet = (String) con.get(j);
		    		if(tet.indexOf(big) != -1) { 			
			    		String[] expression = tet.split(">");
			    		String variable = expression[0];
			    		//IntExpr [] ccc = new IntExpr[con.size()];
			    		String num = expression[1];
			    		if(variable != null) {
			    			//ccc[j] = ctx.mkIntConst(a);
			    			IntExpr x = ctx.mkIntConst(a);
				    		IntExpr shu = ctx.mkInt(num);
				    		left = ctx.mkAnd(left,ctx.mkGt(x, shu));
			    		}	    		
			    	}
			    	//小于关系
			    	else if(tet.indexOf(small) != -1) {
			    		String[] expression = tet.split("<");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(a);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkLt(x, shu));
			    	}
			    	//大于等于关系
			    	else if(tet.indexOf(bigequal) != -1) {
			    		String[] expression = tet.split(">=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(a);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkGe(x, shu));
			    	}
			    	//小于等于关系
			    	else if(tet.indexOf(smallequal) != -1) {
			    		String[] expression = tet.split("<=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(a);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkLe(x, shu));
			    	}
			    	else if(tet.indexOf(hd) != -1) {
			    		String[] expression = tet.split("=hd(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(0);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(a);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(tl) != -1) {
			    		String[] expression = tet.split("=tl(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(value.size()-1);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(a);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(len) != -1) {
			    		String[] expression = tet.split("=len(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = String.valueOf(value.size());
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(a);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
			    	}
			    	else {
			    		if(tet.indexOf(inset) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(inset);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	
					            }else {
						    		IntExpr x = ctx.mkIntConst(a);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
					            }
					        }
			    		}
			    		else if(tet.indexOf(notin) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(notin);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	IntExpr x = ctx.mkIntConst(a);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
					            }else {
						    		
					            }
					        }
			    		}
			    	}
		    		a = createNext(a);
		    	}
			}else {
				String aa = "aab";
				for(int j = 0 ; j < d.size()-1; j++) {
		    		String tet = (String) d.get(j);
		    		if(tet.indexOf(big) != -1) { 			
			    		String[] expression = tet.split(">");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(variable != null) {
			    			IntExpr x = ctx.mkIntConst(aa);
				    		IntExpr shu = ctx.mkInt(num);
				    		left = ctx.mkAnd(left,ctx.mkGt(x, shu));
			    		}	    		
			    	}
			    	//小于关系
			    	else if(tet.indexOf(small) != -1) {
			    		String[] expression = tet.split("<");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(aa);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkLt(x, shu));
			    	}
			    	//大于等于关系
			    	else if(tet.indexOf(bigequal) != -1) {
			    		String[] expression = tet.split(">=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(aa);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkGe(x, shu));
			    	}
			    	//小于等于关系
			    	else if(tet.indexOf(smallequal) != -1) {
			    		String[] expression = tet.split("<=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(aa);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkLe(x, shu));
			    	}
			    	else if(tet.indexOf(hd) != -1) {
			    		String[] expression = tet.split("=hd(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(0);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(aa);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(tl) != -1) {
			    		String[] expression = tet.split("=tl(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(value.size()-1);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(aa);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(len) != -1) {
			    		String[] expression = tet.split("=len(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = String.valueOf(value.size());
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(aa);
			    		IntExpr shu = ctx.mkInt(num);
			    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
			    	}
			    	else {
			    		if(tet.indexOf(inset) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(inset);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	
					            }else {
						    		IntExpr x = ctx.mkIntConst(aa);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
					            }
					        }
			    		}
			    		else if(tet.indexOf(notin) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(notin);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	IntExpr x = ctx.mkIntConst(aa);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		left = ctx.mkAnd(left,ctx.mkEq(x, shu));
					            }else {
						    		
					            }
					        }
			    		}
			    	}
		    		aa = createNext(aa);
		    	}
		    }
		}
		for(int o = 0; o < 2; o++) {
			if(o == 0) {
				String b = "aaa";
				for(int j = 0 ; j < con1.size(); j++) {
		    		String tet = (String) con1.get(j);
		    		if(tet.indexOf(big) != -1) { 			
			    		String[] expression = tet.split(">");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(variable != null) {
			    			IntExpr x = ctx.mkIntConst(b);
				    		IntExpr shu = ctx.mkInt(num);
				    		right = ctx.mkAnd(right,ctx.mkGt(x, shu));
			    		}	    		
			    	}
			    	//小于关系
			    	else if(tet.indexOf(small) != -1) {
			    		String[] expression = tet.split("<");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(b);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkLt(x, shu));
			    	}
			    	//大于等于关系
			    	else if(tet.indexOf(bigequal) != -1) {
			    		String[] expression = tet.split(">=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(b);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkGe(x, shu));
			    	}
			    	//小于等于关系
			    	else if(tet.indexOf(smallequal) != -1) {
			    		String[] expression = tet.split("<=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(b);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkLe(x, shu));
			    	}
			    	else if(tet.indexOf(hd) != -1) {
			    		String[] expression = tet.split("=hd(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(0);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(b);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(tl) != -1) {
			    		String[] expression = tet.split("=tl(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(value.size()-1);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(b);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(len) != -1) {
			    		String[] expression = tet.split("=len(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = String.valueOf(value.size());
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(b);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
			    	}
			    	else {
			    		if(tet.indexOf(inset) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(inset);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	
					            }else {
						    		IntExpr x = ctx.mkIntConst(b);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
					            }
					        }
			    		}
			    		else if(tet.indexOf(notin) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(notin);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	IntExpr x = ctx.mkIntConst(b);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
					            }else {
						    		
					            }
					        }
			    		}
			    	}
					b = createNext(b);
		    	}
			}else {
				String bb = "aab";
				for(int j = 0 ; j < d1.size()-1; j++) {
		    		String tet = (String) d1.get(j);
		    		if(tet.indexOf(big) != -1) { 			
			    		String[] expression = tet.split(">");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(variable != null) {
			    			IntExpr x = ctx.mkIntConst(bb);
				    		IntExpr shu = ctx.mkInt(num);
				    		right = ctx.mkAnd(right,ctx.mkGt(x, shu));
			    		}	    		
			    	}
			    	//小于关系
			    	else if(tet.indexOf(small) != -1) {
			    		String[] expression = tet.split("<");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(bb);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkLt(x, shu));
			    	}
			    	//大于等于关系
			    	else if(tet.indexOf(bigequal) != -1) {
			    		String[] expression = tet.split(">=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(bb);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkGe(x, shu));
			    	}
			    	//小于等于关系
			    	else if(tet.indexOf(smallequal) != -1) {
			    		String[] expression = tet.split("<=");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		IntExpr x = ctx.mkIntConst(bb);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkLe(x, shu));
			    	}
			    	else if(tet.indexOf(hd) != -1) {
			    		String[] expression = tet.split("=hd(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(0);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(bb);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(tl) != -1) {
			    		String[] expression = tet.split("=tl(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = value.get(value.size()-1);
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(bb);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
			    	}
			    	else if(tet.indexOf(len) != -1) {
			    		String[] expression = tet.split("=len(|)");
			    		String variable = expression[0];
			    		String num = expression[1];
			    		if(s1!=null && s1.size()>0) {
							List<String> value = new ArrayList<>();
				            Iterator<Map.Entry<String, List<String>>> it = s1.entrySet().iterator(); //利用迭代器循环输出
				            while (it.hasNext()) {
				                Map.Entry<String,List<String>> entry=it.next();
				                String field = entry.getKey().toString();
				                value = entry.getValue();
				                if(expression[1].equals(field)) {
				                	num = String.valueOf(value.size());
				                }
				            }
				        }
			    		IntExpr x = ctx.mkIntConst(bb);
			    		IntExpr shu = ctx.mkInt(num);
			    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
			    	}
			    	else {
			    		if(tet.indexOf(inset) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(inset);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	
					            }else {
						    		IntExpr x = ctx.mkIntConst(bb);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
					            }
					        }
			    		}
			    		else if(tet.indexOf(notin) != -1) {
			    			Boolean isTrue = false;
			    			String[] expression = tet.split(notin);
			    			String num = expression[0];
			    			String setName = expression[1];
				    		if(s2!=null && s2.size()>0) {
								List<String> value = new ArrayList<>();
					            Iterator<Map.Entry<String, List<String>>> it = s2.entrySet().iterator(); //利用迭代器循环输出
					            while (it.hasNext()) {
					                Map.Entry<String,List<String>> entry=it.next();
					                String field = entry.getKey().toString();
					                value = entry.getValue();
					                if(setName.equals(field)) {
					                	for(int i = 0; i < value.size(); i++) {
					                		if(num.equals(value.get(i))) {
					                			isTrue = true;
					                			break;
					                		}
					                	}					                	
					                }
					            }
					            if(isTrue == true) {
					            	IntExpr x = ctx.mkIntConst(bb);
						    		IntExpr shu = ctx.mkInt(9999999);
						    		right = ctx.mkAnd(right,ctx.mkEq(x, shu));
					            }else {
						    		
					            }
					        }
			    		}
			    	}
					bb = createNext(bb);
		    	}
		    }
		}
		System.out.println(left);
		System.out.println(right);
		BoolExpr equation = ctx.mkEq(left,  right);
		boolean model = check(ctx, equation, Status.SATISFIABLE);
		return model;
		/*
        System.out.println("FindModel");
        IntExpr x = ctx.mkIntConst("x");
        IntExpr y = ctx.mkIntConst("y");
        String en = "8";
        int eight = Integer.parseInt(en);

        IntExpr two = ctx.mkInt(2);
        IntExpr five = ctx.mkInt(eight);

        ArithExpr y_plus_one = ctx.mkAdd(y, one);
        //x = ctx.mkIntConst("y");

        BoolExpr c1 = ctx.mkLt(x, y_plus_one);
        BoolExpr c2 = ctx.mkGt(x, two);
        BoolExpr c4 = ctx.mkLt(x, five);

        BoolExpr q = ctx.mkAnd(c1, c2, c4);

        System.out.println("model for: x < y + 1, x > 2");
        model = check(ctx, q, Status.SATISFIABLE);
        System.out.println("x = " + model.evaluate(x, false) + ", y ="
                + model.evaluate(y, false));

        /* assert not(x = y) */
		/*
        BoolExpr x_eq_y = ctx.mkEq(x, y);
        BoolExpr c3 = ctx.mkNot(x_eq_y);

        q = ctx.mkAnd(q, c3);

        System.out.println("model for: x < y + 1, x > 2, not(x = y)");
        model = check(ctx, q, Status.SATISFIABLE);
        System.out.println("x = " + model.evaluate(x, false) + ", y = "
                + model.evaluate(y, false));
        
        
        Expr cc = model.evaluate(x, false);
        BoolExpr x_eq = ctx.mkEq(x, cc);
        BoolExpr c5 = ctx.mkNot(x_eq);
        q= ctx.mkAnd(q, c5);
        model = check(ctx, q, Status.SATISFIABLE);
        System.out.println("x = " + model.evaluate(x, false) + ", y = "
                + model.evaluate(y, false));
        
        Solver s = ctx.mkSolver();
        while(s.check() == Status.SATISFIABLE) {
        	Expr ccc = model.evaluate(x, false);
        	BoolExpr x_eq1 = ctx.mkEq(x, ccc);
            BoolExpr c6 = ctx.mkNot(x_eq1);
            q= ctx.mkAnd(q, c6);
            model = check(ctx, q, Status.SATISFIABLE);
            System.out.println("x = " + model.evaluate(x, false) + ", y = "
                    + model.evaluate(y, false));
        } */       
        /*Expr ccc = model.evaluate(x, false);
        BoolExpr x_eq1 = ctx.mkEq(x, ccc);
        BoolExpr c6 = ctx.mkNot(x_eq1);
        q= ctx.mkAnd(q, c6);
        model = check(ctx, q, Status.SATISFIABLE);
        System.out.println("x = " + model.evaluate(x, false) + ", y = "
                + model.evaluate(y, false));
        */
    }
    
    boolean check(Context ctx, BoolExpr f, Status sat) throws TestFailedException
    {
        Solver s = ctx.mkSolver();
        s.add(f);
        if (s.check() != sat) {
        	System.out.println("that is all");
            throw new TestFailedException();
        }
        if (sat == Status.SATISFIABLE)
        	return true;
            //return s.getModel();
        else
            return false;
    }
    
    /*
     *根据给定字符串返回一个约束条件 
     */
    public static BoolExpr GetContext(ArrayList List){
    	//获取条件       	
    	Context ctx = null;
    	BoolExpr result = null;
    	//判断表达式中的关系,按括号拆分第一个字符串为空串，所以从第二个字符串开始比较
    	//大于关系
    	for(int j = 0 ; j < List.size(); j++) {
    		String tet = (String) List.get(j);
    		if(tet.indexOf(big) != -1) { 			
	    		String[] expression = tet.split(">");
	    		String variable = expression[0];
	    		System.out.println(variable);
	    		String num = expression[1];
	    		if(variable != null) {
	    			IntExpr x = ctx.mkIntConst(variable);
		    		IntExpr shu = ctx.mkInt(num);
		    		result = ctx.mkAnd(result,ctx.mkGt(x, shu));
	    		}	    		
	    	}
	    	//小于关系
	    	else if(tet.indexOf(small) != -1) {
	    		String[] expression = tet.split("<");
	    		String variable = expression[0];
	    		String num = expression[1];
	    		IntExpr x = ctx.mkIntConst(variable);
	    		IntExpr shu = ctx.mkInt(num);
	    		result = ctx.mkAnd(result,ctx.mkLt(x, shu));
	    	}
	    	//大于等于关系
	    	else if(tet.indexOf(bigequal) != -1) {
	    		String[] expression = tet.split(">=");
	    		String variable = expression[0];
	    		String num = expression[1];
	    		IntExpr x = ctx.mkIntConst(variable);
	    		IntExpr shu = ctx.mkInt(num);
	    		result = ctx.mkAnd(result,ctx.mkGe(x, shu));
	    	}
	    	//小于等于关系
	    	else if(tet.indexOf(smallequal) != -1) {
	    		String[] expression = tet.split("<=");
	    		String variable = expression[0];
	    		String num = expression[1];
	    		IntExpr x = ctx.mkIntConst(variable);
	    		IntExpr shu = ctx.mkInt(num);
	    		result = ctx.mkAnd(result,ctx.mkLe(x, shu));
	    	}
    	}
    	return result;    	
    }
    /*
     * 字符串自增
     */
    public static String createNext(String string){
        char [] tempChar = string.toCharArray();
        for(int i =tempChar.length-1;i>=1;i--){
            if (tempChar[i]<'z' ){
                tempChar[i]=(char)(tempChar[i]+1);
                break;
            }else {
                tempChar[i]='a';
                tempChar[i-1]=(char)(tempChar[i-1]+1);
                if (tempChar[i-1]<='z'){
                    i=0;
                }
            }
        }
        return String.valueOf(tempChar);
    }
	
	public static void main(String[] args){  
		Z3ReverseSolution p = new Z3ReverseSolution();
        try{
            HashMap<String, String> cfg = new HashMap<String, String>();  
            cfg.put("model", "true");  
			Context ctx = new Context(cfg);
            Solver s = ctx.mkSolver();
    		//p.findModel(ctx);  		
            RealExpr a = ctx.mkRealConst("a");  
            RealExpr c = ctx.mkRealConst("c");  
            RealExpr d = ctx.mkRealConst("d");  
            RealExpr e = ctx.mkRealConst("e");  
            RealExpr x = ctx.mkRealConst("x");  
            RealExpr y = ctx.mkRealConst("y");  
            ArithExpr left = ctx.mkAdd(x, ctx.mkMul(y, c, e));  
            ArithExpr right = ctx.mkAdd(d, a);  
            BoolExpr equation = ctx.mkEq(left,  right);  
            s.add(equation);  
            s.add(ctx.mkGt(a, ctx.mkReal(0))); 
            Status result = s.check();  
            if(result == Status.SATISFIABLE){  
                //System.out.println("model for: x + y*c*e = d + a, a > 0");  
               // System.out.print(s.getModel());  
               // System.out.println(a.toString());  
                Expr a_value = s.getModel().evaluate(a, false);  
                System.out.println(a_value.toString());  
                BoolExpr x_eq = ctx.mkEq(x, a_value);
                BoolExpr c5 = ctx.mkNot(x_eq);
                s.add(ctx.mkAnd(c5,equation));
            }  
            else if(result == Status.UNSATISFIABLE)  
                    System.out.println("unsat");  
            else   
                System.out.println("unknow");  
              
        }catch(Exception e){  
            System.out.println("z3 exception");  
            e.printStackTrace();  
        }
    }
}
