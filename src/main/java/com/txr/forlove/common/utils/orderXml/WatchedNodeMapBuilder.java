package com.txr.forlove.common.utils.orderXml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * WatchedNode Map构造器.
 * 
 * @since 2015年6月5日
 * @author yanglei
 *
 */
public class WatchedNodeMapBuilder {
	private Map<String, WatchedNode> nodeMap = new HashMap<String, WatchedNode>();

	/**
	 * 添加节点，默认字符类型.
	 * 
	 * @param elementName 文档节点名称
	 * @param keyName map keyName
	 * @return
	 */
	public WatchedNodeMapBuilder append(String elementName, String keyName) {
		nodeMap.put(elementName, new WatchedNode(keyName));
		return this;
	}

	/**
	 * 添加节点.
	 * 
	 * @param elementName 文档节点名称
	 * @param keyName map keyName
	 * @param formatter 节点数据格式化器.
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public WatchedNodeMapBuilder append(String elementName, String keyName, Formatter formatter) {
		nodeMap.put(elementName, new WatchedNode(keyName, formatter));
		return this;
	}

	public Map<String, WatchedNode> build() {
		return Collections.unmodifiableMap(nodeMap);
	}
	
}
