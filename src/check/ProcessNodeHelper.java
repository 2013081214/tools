package check;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;




/**
 * TODO description process节点链表工具类
 *
 * @author vivy
 */
public class ProcessNodeHelper {

	private static final String CircleErrNodeFeatureName = "error-circle-feature";
	private static final String CircleErrNodeProcessNamePrefix = "error-circle-node";

	// 输出内容到控制台
	

	/**
	 * 多个feature进行合并
	 *
	 * @param featureNames
	 * @param retFeatureName 接收合并结果的featurename
	 * @param featureLinkMap
	 * @return
	 */
	public static void LeafCombine(List<String> featureNames, String retFeatureName, Map<String, List<Map<String, ProcessNode>>> featureLinkMap) {
		final List<Map<String, ProcessNode>> ret = new ArrayList<Map<String, ProcessNode>>();
		if ((featureNames == null) || (featureNames.size() <= 0)) {
			return;
		}

		if (featureNames.size() == 1) {
			return;
		}
		ret.addAll(featureLinkMap.get(featureNames.get(0)));
		for (int i = 1; i < featureNames.size(); i++) {
			final List<Map<String, ProcessNode>> tempRet = FeatureCombine(ret, featureLinkMap.get(featureNames.get(i)), true);
			ret.clear();
			ret.addAll(tempRet);
		}
		if (!featureLinkMap.containsKey(retFeatureName)) {
			featureLinkMap.put(retFeatureName, new ArrayList<Map<String, ProcessNode>>());
		}
		featureLinkMap.get(retFeatureName).clear();
		featureLinkMap.get(retFeatureName).addAll(ret);// 重新设置当前feature的链路
	}

	/**
	 * 获取feature的输入输出关系链路集合
	 *
	 * @param projectBaseDir
	 * @param projectName
	 * @param selectedFeatureMap
	 * @return
	 */
	public static Map<String, List<Map<String, ProcessNode>>> GetFeatureLinkMapList(String projectBaseDir, String projectName,
			Map<String, Integer> selectedFeatureMap) {
		final Map<String, List<String>> featureProcessMap = GetFeatureProcess(projectBaseDir, projectName, selectedFeatureMap);
		// 获取单个feature内部所有process的节点输入输出关系
		final Map<String, Map<String, List<ProcessNode>>> featureProcessNodeMap = new HashMap<String, Map<String, List<ProcessNode>>>();
		for (final String featureName : featureProcessMap.keySet()) {
			featureProcessNodeMap.put(featureName, ProcessCombineNodeLink(featureName, featureProcessMap.get(featureName)));
		}
		final Map<String, List<Map<String, ProcessNode>>> featureLinkMap = new HashMap<String, List<Map<String, ProcessNode>>>();
		// 获取单个feature的链路关系
		for (final String featureName : featureProcessNodeMap.keySet()) {
			featureLinkMap.put(featureName, GetSingleFeatureAllLinkList(featureProcessNodeMap.get(featureName), false));
		}
		return featureLinkMap;
	}

	/**
	 * 获取feature和对应的process
	 *
	 * @param projectBaseDir
	 * @param projectName
	 * @param selectedFeatureMap
	 * @return
	 */
	public static Map<String, List<String>> GetFeatureProcess(String projectBaseDir, String projectName, Map<String, Integer> selectedFeatureMap) {
		// 读取specification中的内容
		final String specFilePath = Paths.get(projectBaseDir, "specification", projectName + ".formal").toString();
		final File specFile = new File(specFilePath);
		final Long fileLength = specFile.length();
		final byte[] filecontent = new byte[fileLength.intValue()];
		try {
			final FileInputStream in = new FileInputStream(specFile);
			in.read(filecontent);
			in.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final String specStringContent = new String(filecontent);

		// 根据选中的节点，从读取的内容中获取指定feature
		int startIndex = 0;
		final List<String> featureList = new ArrayList<>();
		while (true) {
			final int featureStartIndex = specStringContent.indexOf("<feature name=", startIndex);
			final int featureEndIndex = specStringContent.indexOf("</feature>", startIndex) + "</feature>".length();
			if (featureStartIndex < 0) {
				break;
			}
			final String featureString = specStringContent.substring(featureStartIndex, featureEndIndex);
			startIndex = featureEndIndex;
			final String featureName = getName(featureString);
			if (selectedFeatureMap.containsKey(featureName)) {
				featureList.add(featureString);// 只添加选中了的feature
			}
		}
		final Map<String, List<String>> featureProcessMap = new HashMap<String, List<String>>();
		// 构造product.formal文件内容
		final List<String> featureNameList = new ArrayList<>();
		final List<String> featureProcessList = new ArrayList<>();
		if (featureList.size() > 0) {
			for (final String featureStr : featureList) {
				final String featureName = getName(featureStr);
				final List<String> featureProcess = getProcess(featureStr);
				featureProcessMap.put(featureName, featureProcess);
			}
		}
		return featureProcessMap;
	}

	/**
	 * 获取一个feature中所有process和其对应的输入输出关系
	 *
	 * @param featureName
	 * @param processList
	 * @return
	 */
	public static Map<String, List<ProcessNode>> ProcessCombineNodeLink(String featureName, List<String> processList) {
		final Map<String, List<ProcessNode>> processMap = new HashMap<String, List<ProcessNode>>();
		if ((processList == null) || (processList.size() <= 0)) {
			return processMap;
		}

		for (final String process : processList) {
			final List<ProcessNode> nodes = CreateProcessNode(featureName, process);
			if (nodes.size() <= 0) {
				continue;
			}
			final String processName = getProcessName(process);
			processMap.put(processName, nodes);
		}

		if (processMap.size() <= 0) {
			return processMap;
		}

		// process的两两组合全部都要判断一遍，最后再移除环状链路
		final String[] keys = processMap.keySet().stream().toArray(String[]::new);
		int jizhunIndex = 0;
		int i = 1;
		for (i = jizhunIndex + 1; i < keys.length; i++) {
			final String jizhunProcessName = keys[jizhunIndex];
			final String processName = keys[i];
			final List<ProcessNode> combineRet = new ArrayList<ProcessNode>();
			SetUpFeatureProcessRela(processMap.get(jizhunProcessName), processMap.get(processName));
			if (i == (keys.length - 1)) {
				jizhunIndex += 1;
				i = jizhunIndex;
			}
		}

		//printToConsole(featureName, true);
		// 打印单个proces的输入输出关系
		PrintSingleProcessInputOutputRela(processMap);
		//printToConsole("", true); //
		// 获取单个feature的有效链路集合
		GetSingleFeatureAllLinkList(processMap, true);

		return processMap;

	}

	/**
	 * 两个feature之间合并实际就是两个链路集合的合并
	 *
	 * @param feature1
	 * @param feature2
	 * @param isFirstCheck 是否是首次检查，因为首次检查时，如果第一轮方向判断出没有关系，那还会执行第二次检查。
	 * @return
	 */
	public static List<Map<String, ProcessNode>> FeatureCombine(List<Map<String, ProcessNode>> feature1, List<Map<String, ProcessNode>> feature2,
			boolean isFirstCheck) {
		final Map<Integer, List<String>> outputVarMap = GetSingleFeatureInputOrOuputVars(feature1, false);
		final Map<Integer, List<String>> inputVarMap = GetSingleFeatureInputOrOuputVars(feature2, true);

		final List<Map<String, ProcessNode>> retLinkMapList = new ArrayList<Map<String, ProcessNode>>();
		final List<Map<String, ProcessNode>> mergedLinkMapList = new ArrayList<Map<String, ProcessNode>>();// 已经合并过的链路
		if ((feature1 == null) || (feature1.size() <= 0)) {
			retLinkMapList.addAll(feature2);
			return retLinkMapList;
		}
		if ((feature2 == null) || (feature2.size() <= 0)) {
			retLinkMapList.addAll(feature1);
			return retLinkMapList;
		}

		// 检查输出或者输入是否有关系
		boolean hasRela = false;
		for (final int outputLinkIndex : outputVarMap.keySet()) {
			for (final int inputLinkIndex : inputVarMap.keySet()) {
				// 之前没有往结果集中添加过 并且没有合并过
				if (!retLinkMapList.contains(feature1.get(outputLinkIndex)) && !mergedLinkMapList.contains(feature1.get(outputLinkIndex))) {
					retLinkMapList.add(feature1.get(outputLinkIndex));
				}
				if (!retLinkMapList.contains(feature2.get(inputLinkIndex)) && !mergedLinkMapList.contains(feature2.get(inputLinkIndex))) {
					retLinkMapList.add(feature2.get(inputLinkIndex));
				}

				// 判断链路的输出和输入是否有关系
				if (CheckVarsHasRela(outputVarMap.get(outputLinkIndex), inputVarMap.get(inputLinkIndex))) {
					hasRela = true;// 先后顺序确定，这两个feature的合并就不在考虑另外一种顺序了
					final Map<String, ProcessNode> tempLinkMap = CopyNodeLinkMap(feature1.get(outputLinkIndex));
					tempLinkMap.putAll(feature2.get(inputLinkIndex));// 将feature2的第inputLinkIndex链路添加到feature1的第outputLinkIndex链路后面
					retLinkMapList.add(tempLinkMap);
					retLinkMapList.remove(feature1.get(outputLinkIndex));// 把将要返回的结果集录 删除此条已合并的链路
					retLinkMapList.remove(feature2.get(inputLinkIndex));// 把将要返回的结果集录 删除此条已合并的链路
					mergedLinkMapList.add(feature1.get(outputLinkIndex));// 把此链路添加到已合并的记录中
					mergedLinkMapList.add(feature2.get(inputLinkIndex));// 把此链路添加到已合并的记录中
				}
			}
		}

		// hasRela为true，说明有关系，说明当前方向存在输出输入关系；或者没有关系，但是这已经是第二查找，所以也就直接返回了
		if (hasRela || !isFirstCheck) {
			return retLinkMapList;// 关系
		}
		// 第二次检查时，feature2与feature1的参数位置调换一下，就相当于换一种方向检查了
		return FeatureCombine(feature2, feature1, false);

	}

	/**
	 * 获取单个feature的输入或者输出参数名
	 *
	 * @param featureLinkMapList
	 * @param isGetInput
	 * @return
	 */
	public static Map<Integer, List<String>> GetSingleFeatureInputOrOuputVars(List<Map<String, ProcessNode>> featureLinkMapList, boolean isGetInput) {
		final Map<Integer, List<String>> ret = new HashMap<Integer, List<String>>();
		if ((featureLinkMapList == null) || (featureLinkMapList.size() <= 0)) {
			return ret;
		}
		int index = 0;
		for (final Map<String, ProcessNode> linkMap : featureLinkMapList) {
			final String[] keyArrays = linkMap.keySet().stream().toArray(String[]::new);
			final String firstKey = keyArrays[0];
			final String lastKey = keyArrays[keyArrays.length - 1];
			if (isGetInput) {
				final List<String> inputVars = linkMap.get(firstKey).input;
				if (inputVars.size() > 0) {
					ret.put(index++, inputVars);
				}
			} else {
				final List<String> outputVars = linkMap.get(lastKey).output;
				if (outputVars.size() > 0) {
					ret.put(index++, outputVars);
				}
			}
		}
		return ret;
	}

	/**
	 * 获取单个feature的所有输入输出关系/路径
	 *
	 * @param processMap
	 * @param isPrint 是否打印链路到控制台
	 */
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

	/**
	 * 打印单个process的输出关系
	 */
	public static void PrintSingleProcessInputOutputRela(Map<String, List<ProcessNode>> processMap) {
		for (final String processName : processMap.keySet()) {
			for (final ProcessNode node : processMap.get(processName)) {
				final String nodeStr = "[" + String.join(",", node.input) + "]" + node.processName + node.inputIndex + node.outputIndex + "["
					+ String.join(",", node.output) + "]";
				//printToConsole(nodeStr, true);
			}
		}
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

	/**
	 * 获取单节点链路集合
	 *
	 * @param node
	 * @param nodeLinkMap
	 * @return
	 */
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

	/**
	 * 拷贝map
	 *
	 * @param nodeLinkMap
	 * @return
	 */
	public static LinkedHashMap<String, ProcessNode> CopyNodeLinkMap(Map<String, ProcessNode> nodeLinkMap) {
		final LinkedHashMap<String, ProcessNode> newNodeLinkMap = new LinkedHashMap<String, ProcessNode>();
		for (final String key : nodeLinkMap.keySet()) {
			newNodeLinkMap.put(key, nodeLinkMap.get(key));
		}
		return newNodeLinkMap;
	}

	/**
	 * 获取头部节点
	 *
	 * @param processMap
	 * @return
	 */
	public static List<ProcessNode> GetFirstProcessNode(Map<String, List<ProcessNode>> processMap) {
		final List<ProcessNode> firstProcessNode =
			processMap.values().stream().flatMap(a -> a.stream()).filter(a -> a.preNode.size() <= 0).collect(Collectors.toList());
		return firstProcessNode;
	}

	/**
	 * 给两个process之间设置关系
	 *
	 * @param process1
	 * @param process2
	 */
	public static void SetUpFeatureProcessRela(List<ProcessNode> process1, List<ProcessNode> process2) {
		final boolean hasCombined = false;
		if ((process1.size() <= 0) || (process2.size() <= 0)) {
			return;
		}

		int preOrNext = 0;// process1是前驱节点还是后继节点，0是初始值，表示还未确定，1：前驱，2：后继。因为一旦顺序确定之后，后面存在相反的输入输出关系也忽略掉，避免引起环
		for (final ProcessNode node1 : process1) {
			for (final ProcessNode node2 : process2) {
				// 检查两个node是否存在输入输出关系
				// 检查node1的输出与node2的输入是否有关系
				if (CheckVarsHasRela(node1.output, node2.input) && ((preOrNext == 0) || (preOrNext == 1))) {
					// 节点合并，node1是node2的前驱节点
					preOrNext = 1;
					node1.nextNode.add(node2);
					node2.preNode.add(node1);
					continue;

				} else if (CheckVarsHasRela(node2.output, node1.input) && ((preOrNext == 0) || (preOrNext == 2))) {
					preOrNext = 2;
					node2.nextNode.add(node1);
					node1.preNode.add(node2);
					continue;
				}
			}
		}
	}

	/**
	 * 检查两个变量集是否存在关系
	 *
	 * @param var1
	 * @param var2
	 * @return 是否有关系
	 */
	public static boolean CheckVarsHasRela(List<String> var1, List<String> var2) {
		for (final String v1 : var1) {
			for (final String v2 : var2) {
				if (v1.equals(v2)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 解析单个process的输入输出关系
	 *
	 * @param featureName
	 * @param process
	 * @return
	 */
	public static List<ProcessNode> CreateProcessNode(String featureName, String process) {
		final List<ProcessNode> nodes = new ArrayList<ProcessNode>();
		if (featureName.isEmpty() || process.isEmpty()) {
			return nodes;
		}
		final String processName = getProcessName(process);
		final String processPost = getProcessPost(process);
		final List<List<String>> inputs = getInputs(process);
		final List<List<String>> outputs = getOutpus(process);
		if ((inputs.size() <= 0) || (outputs.size() <= 0) || processPost.isEmpty()) {
			return nodes;// 输入或者输出不存在，或者输出关系不存在
		}
		int inputIndex = 0;
		int outputIndex = 0;
		for (final List<String> input : inputs) {
			outputIndex = 0;
			inputIndex++;
			for (final List<String> output : outputs) {
				outputIndex++;
				boolean isMatch = false;
				// 判断输入输出是否匹配，i和o只要存在一个匹配，那么input和output就是匹配的，因为input/output的值是逗号分隔的值
				for (final String i : input) {
					if (isMatch) {
						break;// 此输入输出已经匹配上了，终止此轮循环
					}
					for (final String o : output) {
						final String pattern1 = "[\\s|\\S]* .*" + i + ".*=.*" + o + ".*;[\\s|\\S]*";
						final String pattern2 = "[\\s|\\S]* .*" + o + ".*=.*" + i + ".*;[\\s|\\S]*";
						if (Pattern.matches(pattern1, processPost) || Pattern.matches(pattern2, processPost)) {
							final ProcessNode node = new ProcessNode();
							node.featureName = featureName;
							node.processName = processName;
							node.input = input;
							node.output = output;
							node.inputIndex = inputIndex;
							node.outputIndex = outputIndex;
							nodes.add(node);
							// 存在已能够匹配上
							isMatch = true;
							break;// 循环
						}
					}
				}

			}
		}

		return nodes;
	}

	/**
	 * 提取feature中的每个process
	 *
	 * @param feature
	 * @return
	 */
	public static List<String> getProcess(String feature) {
		final List<String> processList = new ArrayList<String>();
		final int startIndex = feature.indexOf("<process name=");
		final int endIndex = feature.lastIndexOf("</process>") + "</process>".length();
		if ((startIndex < 0) || (endIndex < 0)) {
			return processList;
		}
		final String allProcessStr = feature.substring(startIndex, endIndex);
		if (allProcessStr.isEmpty()) {
			return processList;
		}
		while (true) {
			final int processStartIndex = feature.indexOf("<process name=");
			final int processEndIndex = feature.indexOf("</process>") + "</process>".length();
			if ((processStartIndex < 0) || (processEndIndex < 0)) {
				break;
			}
			final String processStr = feature.substring(processStartIndex, processEndIndex);
			feature = feature.substring(processEndIndex);// 移除掉已经取到的process
			if (processStr.isEmpty()) {
				break;
			}

			processList.add(processStr);
		}
		return processList;
	}

	public static String getProcessPost(String process) {
		return getNodeValue(process, "<post>", "</post>");
	}

	/**
	 * 获取process的名字
	 *
	 * @param process
	 * @return
	 */

	public static String getProcessName(String process) {
		return getNodeValue(process, "<process name=\"", "\">");
	}

	/**
	 * 提取feature的name值
	 *
	 * @param feature
	 * @return
	 */
	public static String getName(String feature) {
		return getNodeValue(feature, "<feature name=\"", "\">");
	}

	/**
	 * 提取节点的值
	 *
	 * @param feature
	 * @param startFlag
	 * @param endFlag
	 * @return
	 */
	public static String getNodeValue(String feature, String startFlag, String endFlag) {
		final int startIndex = feature.indexOf(startFlag) + startFlag.length();
		final int endIndex = feature.indexOf(endFlag);
		if ((startIndex < 0) || (endIndex < 0)) {
			return "";
		}
		return feature.substring(startIndex, endIndex);
	}

	/**
	 * 获取feature输出
	 *
	 * @param feature
	 * @return
	 */
	public static List<List<String>> getOutpus(String feature) {
		return getInputsOrOutputs(feature, "<outputs>", "</outputs>", false);
	}

	/**
	 * 获取feature输入
	 *
	 * @param feature
	 * @return
	 */
	public static List<List<String>> getInputs(String feature) {
		return getInputsOrOutputs(feature, "<inputs>", "</inputs>", true);
	}

	/**
	 * 获取输入或输出
	 *
	 * @param feature
	 * @param startFlag
	 * @param endFlag
	 * @param isInput 是否是输入 如果是输入，就应该取process中的第一个输入，如果是输出就应该取process中的最后一个输出
	 * @return
	 */
	public static List<List<String>> getInputsOrOutputs(String feature, String startFlag, String endFlag, boolean isInput) {
		final List<List<String>> result = new ArrayList<List<String>>();
		int startIndex = -1;
		int endIndex = -1;
		if (isInput) {
			startIndex = feature.indexOf(startFlag);
			if (startIndex < 0) {
				return result;
			}
			startIndex = startIndex + startFlag.length();
			endIndex = feature.indexOf(endFlag);
		} else {
			startIndex = feature.lastIndexOf(startFlag);
			if (startIndex < 0) {
				return result;
			}
			startIndex = startIndex + startFlag.length();
			endIndex = feature.lastIndexOf(endFlag);
		}

		final String inputsStr = feature.substring(startIndex, endIndex);
		if (inputsStr.isEmpty()) {
			return result;
		}
		final String[] inputsArray = inputsStr.split("\\|");
		for (final String item : inputsArray) {
			final List<String> varNames = new ArrayList<String>();
			final String[] varArrays = item.split(",");
			for (String varName : varArrays) {
				varName = varName.split(":")[0];
				varNames.add(varName.trim());
			}
			result.add(varNames);
		}
		return result;
	}

}
