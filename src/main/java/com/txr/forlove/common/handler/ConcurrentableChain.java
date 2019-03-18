package com.txr.forlove.common.handler;


import com.txr.forlove.common.concurrent.ExecutorSerivce;

import java.util.Set;
import java.util.concurrent.Future;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public interface ConcurrentableChain {
	Set<Future<Object>> getFutureSet();

	ExecutorSerivce getExecutorService();
}
