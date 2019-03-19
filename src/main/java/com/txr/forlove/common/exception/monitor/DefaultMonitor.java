package com.txr.forlove.common.exception.monitor;

import org.springframework.util.StringUtils;
import com.txr.forlove.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import com.txr.forlove.common.exception.AccessException;
import com.txr.forlove.common.exception.AppException;
import com.txr.forlove.common.exception.ErrorCode;
import com.txr.forlove.common.exception.handler.AbstractExceptionHandler;
import com.txr.forlove.common.exception.handler.ExceptionContext;
import com.txr.forlove.common.exception.handler.ExceptionStep;
import com.txr.forlove.domain.rpc.RPCResult;
import java.io.Serializable;


/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public class DefaultMonitor implements Monitor {
//	private final CallerInfo mainCaller;
//	private final CallerInfo statisticCaller;
//	private CallerInfo statisticCallerByPlatform;

	protected final String module;
	protected String platform;

	public DefaultMonitor(String module, String platform) {
		this.module = module;

//		mainCaller = registerInfo(UmpConstants.JSF_PROVIDER_BIZ_ROOT + "." + this.module, false, true);
//
//		String statisticKey = JSF_PROVIDER_STATISTICS_ROOT + "." + this.module;
//		statisticCaller = registerInfo(statisticKey, false, true);
//
//		if (StringUtils.hasText(platform)) {
//			this.platform = platform;
//			statisticCallerByPlatform = registerInfo(statisticKey + "." + platform, false, true);
//		}
	}

	@Override
	public void end() {
//		registerInfoEnd(mainCaller);
//		registerInfoEnd(statisticCaller);
//
//		if (statisticCallerByPlatform != null) {
//			registerInfoEnd(statisticCallerByPlatform);
//		}
	}


	@Override
	public void errorCode(String errorCode) {
//		registerInfoEnd(registerInfo(ERRORCODE + errorCode, false, true));
//
//		if (StringUtils.hasText(platform)) {
//			registerInfoEnd(registerInfo(ERRORCODE + errorCode + "." + platform, false, true));
//		}
	}

	@Override
	public void failure() {
//		UmpProfiler.functionError(statisticCaller);
//		if (statisticCallerByPlatform != null) {
//			UmpProfiler.functionError(statisticCallerByPlatform);
//		}
	}

	@Override
	public void functionError() {
//		UmpProfiler.functionError(mainCaller);
	}

	public String getModule() {
		return module;
	}

	public String getPlatform() {
		return platform;
	}
	
	
}
