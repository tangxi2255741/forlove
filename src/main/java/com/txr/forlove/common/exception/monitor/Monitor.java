package com.txr.forlove.common.exception.monitor;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public interface Monitor {

	/**
	 * 监控结束
	 */
	void end();

	/**
	 * 错误码统计.
	 * 
	 * @param errorCode
	 */
	void errorCode(String errorCode);

	/**
	 * 流程处理失败, 不降低可用率
	 */
	void failure();

	/**
	 * 整个流程处理失败, 降低系统可用率!
	 */
	void functionError();

}