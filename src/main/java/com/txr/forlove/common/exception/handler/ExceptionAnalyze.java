/**
 * 
 */
package com.txr.forlove.common.exception.handler;


import com.txr.forlove.domain.rpc.RPCResult;

import java.io.Serializable;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public interface ExceptionAnalyze {
	<T extends Serializable> RPCResult<T> analyze(Throwable e, ExceptionContext context);
}
