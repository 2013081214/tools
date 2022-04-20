/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2020  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 *
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package check;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO description 输入输出链表节点
 *
 * @author vivy
 *
 */
public class ProcessNode {

	// 前节点
	Set<ProcessNode> preNode = new HashSet<ProcessNode>();
	// 输入索引
	int inputIndex;
	// 输入，之所以是集合，是因为有多个输入可以表示为同一个输入
	List<String> input;
	// 当前feature的名字
	String featureName;
	// 当前process的名字
	String processName;
	// 输出索引
	int outputIndex;
	// 输出，同理
	List<String> output;
	// 当前节点
	Set<ProcessNode> nextNode = new HashSet<ProcessNode>();

	public void setFeatureName(String name) {
		featureName = name;
	}

	public void setProcessName(String name) {
		processName = name;
	}
}
