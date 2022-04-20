package testcc;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class KMP {

    /**
     * 求出一个字符数组的next数组
     * @param t 字符数组
     * @return next数组
     */
    public static int[] getNextArray(char[] t) {
        int[] next = new int[t.length];
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
    
    public static void checkFunctionScenarioPath(){
		try{
			SAXReader saxReader=new SAXReader(); 
			Document doc=saxReader.read("D:\\model.formal");
			Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
			for(Iterator i = rootElm.elementIterator();i.hasNext();) {
				Element childEle = (Element) i.next();
				String featureName = childEle.attributeValue("name");
				List nodes = childEle.elements("process");
				for (Iterator it = nodes.iterator(); it.hasNext();) {      
				    Element elm = (Element) it.next();
				    Element input = elm.element("inputs");
				    String inputs = input.getText();
	            	String[] inputsStrArray = inputs.split(";");
	            	Element output = elm.addElement("outputs");
	            	String ouputs = output.getText();
	            	String[] outputsStrArray = inputs.split(";");
	            	Element post = elm.addElement("post");
	            	String posts = post.getText();
	            	String[] postStrArray = posts.split("if|elseif|else");
	            	for(int k = 0; k <= postStrArray.length;k++) {
	            	System.out.println(postStrArray[k]);}
	            	//判断post判定条件中是否出现input的变量
	            	/*for(int k = 0; k <= postStrArray.length;k++) {
	            		for(int p = 0; p <= inputsStrArray.length; p++) {
	            			boolean haveInput = kmpMatch(inputsStrArray[p],postStrArray[k]);
	            			if(haveInput == true) {
	            				map.put(num,inputsStrArray[p]);
	            				map.put(num,postStrArray[k]);
	            			}
	            			for(int w = 0; w <= outputsStrArray.length; w++) {
	            				boolean haveOutput = kmpMatch(outputsStrArray[w],postStrArray[k]);
	            				if(haveOutput == true) {
	            					map.put(num,outputsStrArray[w]);	            				
	            				}
	            			}
	            			
	            		}
	            		num++;
	            	}*/
				}
				for(Iterator j = childEle.elementIterator();j.hasNext();) {
					
				}
				//System.out.println(map);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

    public static void main(String[] args) {
    	checkFunctionScenarioPath();
        System.out.println(kmpMatch("abcabaabaabcacb", "abaabcac"));
    }

}