package com.txr.forlove.common.concurrent;

import java.util.concurrent.Future;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月23日
 * @since 2018年8月23日
 * 
 */
public interface ExecutorSerivce {
	void execute(Runnable command);

	Future<?> submit(Runnable task);

	<T> Future<T> submit(Runnable task, T result);
}
