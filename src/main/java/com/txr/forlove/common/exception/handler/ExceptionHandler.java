package com.txr.forlove.common.exception.handler;


import com.txr.forlove.common.handler.Handler;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 * @param <T>
 */
public interface ExceptionHandler extends Handler<Throwable, ExceptionContext> {

	boolean supports(Throwable e);

	/**
	 * 步骤，决定执行数量, 是否可跳过, 且不能重复
	 * 
	 * @return
	 */
	ExceptionStep getStep();
	@Override
	int getOrder();

}
