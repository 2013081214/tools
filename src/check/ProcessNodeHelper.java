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
 * TODO description process�ڵ���������
 *
 * @author vivy
 */
public class ProcessNodeHelper {

	private static final String CircleErrNodeFeatureName = "error-circle-feature";
	private static final String CircleErrNodeProcessNamePrefix = "error-circle-node";

	// ������ݵ�����̨
	

	/**
	 * ���feature���кϲ�
	 *
	 * @param featureNames
	 * @param retFeatureName ���պϲ������featurename
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
		featureLinkMap.get(retFeatureName).addAll(ret);// �������õ�ǰfeature����·
	}

	/**
	 * ��ȡfeature�����������ϵ��·����
	 *
	 * @param projectBaseDir
	 * @param projectName
	 * @param selectedFeatureMap
	 * @return
	 */
	public static Map<String, List<Map<String, ProcessNode>>> GetFeatureLinkMapList(String projectBaseDir, String projectName,
			Map<String, Integer> selectedFeatureMap) {
		final Map<String, List<String>> featureProcessMap = GetFeatureProcess(projectBaseDir, projectName, selectedFeatureMap);
		// ��ȡ����feature�ڲ�����process�Ľڵ����������ϵ
		final Map<String, Map<String, List<ProcessNode>>> featureProcessNodeMap = new HashMap<String, Map<String, List<ProcessNode>>>();
		for (final String featureName : featureProcessMap.keySet()) {
			featureProcessNodeMap.put(featureName, ProcessCombineNodeLink(featureName, featureProcessMap.get(featureName)));
		}
		final Map<String, List<Map<String, ProcessNode>>> featureLinkMap = new HashMap<String, List<Map<String, ProcessNode>>>();
		// ��ȡ����feature����·��ϵ
		for (final String featureName : featureProcessNodeMap.keySet()) {
			featureLinkMap.put(featureName, GetSingleFeatureAllLinkList(featureProcessNodeMap.get(featureName), false));
		}
		return featureLinkMap;
	}

	/**
	 * ��ȡfeature�Ͷ�Ӧ��process
	 *
	 * @param projectBaseDir
	 * @param projectName
	 * @param selectedFeatureMap
	 * @return
	 */
	public static Map<String, List<String>> GetFeatureProcess(String projectBaseDir, String projectName, Map<String, Integer> selectedFeatureMap) {
		// ��ȡspecification�е�����
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

		// ����ѡ�еĽڵ㣬�Ӷ�ȡ�������л�ȡָ��feature
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
				featureList.add(featureString);// ֻ���ѡ���˵�feature
			}
		}
		final Map<String, List<String>> featureProcessMap = new HashMap<String, List<String>>();
		// ����product.formal�ļ�����
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
	 * ��ȡһ��feature������process�����Ӧ�����������ϵ
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

		// process���������ȫ����Ҫ�ж�һ�飬������Ƴ���״��·
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
		// ��ӡ����proces�����������ϵ
		PrintSingleProcessInputOutputRela(processMap);
		//printToConsole("", true); //
		// ��ȡ����feature����Ч��·����
		GetSingleFeatureAllLinkList(processMap, true);

		return processMap;

	}

	/**
	 * ����feature֮��ϲ�ʵ�ʾ���������·���ϵĺϲ�
	 *
	 * @param feature1
	 * @param feature2
	 * @param isFirstCheck �Ƿ����״μ�飬��Ϊ�״μ��ʱ�������һ�ַ����жϳ�û�й�ϵ���ǻ���ִ�еڶ��μ�顣
	 * @return
	 */
	public static List<Map<String, ProcessNode>> FeatureCombine(List<Map<String, ProcessNode>> feature1, List<Map<String, ProcessNode>> feature2,
			boolean isFirstCheck) {
		final Map<Integer, List<String>> outputVarMap = GetSingleFeatureInputOrOuputVars(feature1, false);
		final Map<Integer, List<String>> inputVarMap = GetSingleFeatureInputOrOuputVars(feature2, true);

		final List<Map<String, ProcessNode>> retLinkMapList = new ArrayList<Map<String, ProcessNode>>();
		final List<Map<String, ProcessNode>> mergedLinkMapList = new ArrayList<Map<String, ProcessNode>>();// �Ѿ��ϲ�������·
		if ((feature1 == null) || (feature1.size() <= 0)) {
			retLinkMapList.addAll(feature2);
			return retLinkMapList;
		}
		if ((feature2 == null) || (feature2.size() <= 0)) {
			retLinkMapList.addAll(feature1);
			return retLinkMapList;
		}

		// ���������������Ƿ��й�ϵ
		boolean hasRela = false;
		for (final int outputLinkIndex : outputVarMap.keySet()) {
			for (final int inputLinkIndex : inputVarMap.keySet()) {
				// ֮ǰû�������������ӹ� ����û�кϲ���
				if (!retLinkMapList.contains(feature1.get(outputLinkIndex)) && !mergedLinkMapList.contains(feature1.get(outputLinkIndex))) {
					retLinkMapList.add(feature1.get(outputLinkIndex));
				}
				if (!retLinkMapList.contains(feature2.get(inputLinkIndex)) && !mergedLinkMapList.contains(feature2.get(inputLinkIndex))) {
					retLinkMapList.add(feature2.get(inputLinkIndex));
				}

				// �ж���·������������Ƿ��й�ϵ
				if (CheckVarsHasRela(outputVarMap.get(outputLinkIndex), inputVarMap.get(inputLinkIndex))) {
					hasRela = true;// �Ⱥ�˳��ȷ����������feature�ĺϲ��Ͳ��ڿ�������һ��˳����
					final Map<String, ProcessNode> tempLinkMap = CopyNodeLinkMap(feature1.get(outputLinkIndex));
					tempLinkMap.putAll(feature2.get(inputLinkIndex));// ��feature2�ĵ�inputLinkIndex��·��ӵ�feature1�ĵ�outputLinkIndex��·����
					retLinkMapList.add(tempLinkMap);
					retLinkMapList.remove(feature1.get(outputLinkIndex));// �ѽ�Ҫ���صĽ����¼ ɾ�������Ѻϲ�����·
					retLinkMapList.remove(feature2.get(inputLinkIndex));// �ѽ�Ҫ���صĽ����¼ ɾ�������Ѻϲ�����·
					mergedLinkMapList.add(feature1.get(outputLinkIndex));// �Ѵ���·��ӵ��Ѻϲ��ļ�¼��
					mergedLinkMapList.add(feature2.get(inputLinkIndex));// �Ѵ���·��ӵ��Ѻϲ��ļ�¼��
				}
			}
		}

		// hasRelaΪtrue��˵���й�ϵ��˵����ǰ���������������ϵ������û�й�ϵ���������Ѿ��ǵڶ����ң�����Ҳ��ֱ�ӷ�����
		if (hasRela || !isFirstCheck) {
			return retLinkMapList;// ��ϵ
		}
		// �ڶ��μ��ʱ��feature2��feature1�Ĳ���λ�õ���һ�£����൱�ڻ�һ�ַ�������
		return FeatureCombine(feature2, feature1, false);

	}

	/**
	 * ��ȡ����feature������������������
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
	 * ��ȡ����feature���������������ϵ/·��
	 *
	 * @param processMap
	 * @param isPrint �Ƿ��ӡ��·������̨
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
		// �Ƴ���״��·
		final List<Map<String, ProcessNode>> retList = new ArrayList<Map<String, ProcessNode>>();
		for (final Map<String, ProcessNode> linkMap : tempRetList) {
			if (!linkMap.keySet().stream().anyMatch(a -> a.startsWith(CircleErrNodeProcessNamePrefix))) {
				retList.add(linkMap);// ����·����ѭ����·������ʹ��
			}
		}
		return retList;
	}

	/**
	 * ��ӡ����process�������ϵ
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
			// ��ӡ��·
			//printToConsole(linkStr, true);
		}
	}

	/**
	 * ��ȡ���ڵ���·����
	 *
	 * @param node
	 * @param nodeLinkMap
	 * @return
	 */
	public static List<Map<String, ProcessNode>> GetSingleProcessNodeLinkMapList(ProcessNode node, Map<String, ProcessNode> nodeLinkMap) {
		final List<Map<String, ProcessNode>> ret = new ArrayList<Map<String, ProcessNode>>();
		ret.add(nodeLinkMap);
		if (nodeLinkMap.containsKey(node.processName)) {
			// ����ѭ���ڵ㣬
			final ProcessNode circleErrNode = new ProcessNode();
			circleErrNode.setFeatureName(CircleErrNodeFeatureName);
			circleErrNode.setProcessName(CircleErrNodeProcessNamePrefix + node.processName);
			nodeLinkMap.put(CircleErrNodeProcessNamePrefix + node.processName, circleErrNode);
			return ret;
		}

		nodeLinkMap.put(node.processName, node);
		if (node.nextNode.size() <= 0) {
			return ret;// û�к�̽ڵ���
		}
		for (final ProcessNode nextNode : node.nextNode) {
			final LinkedHashMap<String, ProcessNode> nextNodeLinkMap = CopyNodeLinkMap(nodeLinkMap);
			final List<Map<String, ProcessNode>> nextNodeRet = GetSingleProcessNodeLinkMapList(nextNode, nextNodeLinkMap);
			ret.addAll(nextNodeRet);
		}
		ret.remove(nodeLinkMap);// �Ƴ����ظ�������·
		return ret;
	}

	/**
	 * ����map
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
	 * ��ȡͷ���ڵ�
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
	 * ������process֮�����ù�ϵ
	 *
	 * @param process1
	 * @param process2
	 */
	public static void SetUpFeatureProcessRela(List<ProcessNode> process1, List<ProcessNode> process2) {
		final boolean hasCombined = false;
		if ((process1.size() <= 0) || (process2.size() <= 0)) {
			return;
		}

		int preOrNext = 0;// process1��ǰ���ڵ㻹�Ǻ�̽ڵ㣬0�ǳ�ʼֵ����ʾ��δȷ����1��ǰ����2����̡���Ϊһ��˳��ȷ��֮�󣬺�������෴�����������ϵҲ���Ե�����������
		for (final ProcessNode node1 : process1) {
			for (final ProcessNode node2 : process2) {
				// �������node�Ƿ�������������ϵ
				// ���node1�������node2�������Ƿ��й�ϵ
				if (CheckVarsHasRela(node1.output, node2.input) && ((preOrNext == 0) || (preOrNext == 1))) {
					// �ڵ�ϲ���node1��node2��ǰ���ڵ�
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
	 * ��������������Ƿ���ڹ�ϵ
	 *
	 * @param var1
	 * @param var2
	 * @return �Ƿ��й�ϵ
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
	 * ��������process�����������ϵ
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
			return nodes;// ���������������ڣ����������ϵ������
		}
		int inputIndex = 0;
		int outputIndex = 0;
		for (final List<String> input : inputs) {
			outputIndex = 0;
			inputIndex++;
			for (final List<String> output : outputs) {
				outputIndex++;
				boolean isMatch = false;
				// �ж���������Ƿ�ƥ�䣬i��oֻҪ����һ��ƥ�䣬��ôinput��output����ƥ��ģ���Ϊinput/output��ֵ�Ƕ��ŷָ���ֵ
				for (final String i : input) {
					if (isMatch) {
						break;// ����������Ѿ�ƥ�����ˣ���ֹ����ѭ��
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
							// �������ܹ�ƥ����
							isMatch = true;
							break;// ѭ��
						}
					}
				}

			}
		}

		return nodes;
	}

	/**
	 * ��ȡfeature�е�ÿ��process
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
			feature = feature.substring(processEndIndex);// �Ƴ����Ѿ�ȡ����process
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
	 * ��ȡprocess������
	 *
	 * @param process
	 * @return
	 */

	public static String getProcessName(String process) {
		return getNodeValue(process, "<process name=\"", "\">");
	}

	/**
	 * ��ȡfeature��nameֵ
	 *
	 * @param feature
	 * @return
	 */
	public static String getName(String feature) {
		return getNodeValue(feature, "<feature name=\"", "\">");
	}

	/**
	 * ��ȡ�ڵ��ֵ
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
	 * ��ȡfeature���
	 *
	 * @param feature
	 * @return
	 */
	public static List<List<String>> getOutpus(String feature) {
		return getInputsOrOutputs(feature, "<outputs>", "</outputs>", false);
	}

	/**
	 * ��ȡfeature����
	 *
	 * @param feature
	 * @return
	 */
	public static List<List<String>> getInputs(String feature) {
		return getInputsOrOutputs(feature, "<inputs>", "</inputs>", true);
	}

	/**
	 * ��ȡ��������
	 *
	 * @param feature
	 * @param startFlag
	 * @param endFlag
	 * @param isInput �Ƿ������� ��������룬��Ӧ��ȡprocess�еĵ�һ�����룬����������Ӧ��ȡprocess�е����һ�����
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
