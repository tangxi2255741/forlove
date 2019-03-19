package com.txr.forlove.common.exception.handler;

import com.txr.forlove.common.advice.log.B2bLogger;
import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.exception.EnumCode;
import com.txr.forlove.common.exception.ErrorCode;
import com.txr.forlove.common.exception.ExtMessage;
import com.txr.forlove.common.handler.HandlerChain;
import com.txr.forlove.domain.rpc.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年12月28日
 * @since 2018年12月28日
 * 
 */
public abstract class AbstractExceptionHandler implements ExceptionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public boolean supports(Throwable e) {
		return true;
	}

	@Override
	public final int getOrder() {
		return getStep().ordinal();
	}

	@Override
	public final void handle(Throwable t, ExceptionContext context, HandlerChain<Throwable> chain) throws AppException {

		logger.error("{}异常: {}", context.getModuleDesc(), t.getMessage(), t);
		if (t instanceof AppException) {
			EnumCode<String> code = ((AppException) t).getResponseCode();
			context.errorCode(code == null ? ErrorCode.SYSTEM_ERROR.getCode() : code.getCode());
		}

		RPCResult<?> rs = handException(t, context);

		if (rs != null) {
			rs.reqId(B2bLogger.getLogId());
			if (t instanceof AppException) {
				attachExtMessage((AppException) t, rs);
				logger.error("{}异常:code={}, message={}", context.getModuleDesc(), ((AppException) t).getResponseCode(), t.getMessage());
			}
		}
		context.setResult(rs);

		// 不再执行后续节点!
	}

	protected abstract <E extends Serializable> RPCResult<E> handException(Throwable t, ExceptionContext context);

	protected <E extends Serializable> void attachExtMessage(AppException e, RPCResult<E> rs) {
		if (!CollectionUtils.isEmpty(e.getExtMessage())) {
			Map<String, String> extMessage = new HashMap<String, String>();
			for (Map.Entry<ExtMessage, String> entry : e.getExtMessage().entrySet()) {
				extMessage.put(entry.getKey().name(), entry.getValue());
			}
			// rs.setExtMessage(extMessage);
		}
	}
}
