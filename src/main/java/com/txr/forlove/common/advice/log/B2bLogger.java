package com.txr.forlove.common.advice.log;

import com.txr.forlove.common.utils.UUIDUtils;
import org.slf4j.MDC;


/**
 * 日志记录辅助.
 * 
 * @since 2015年10月28日
 * @author yanglei
 *
 */
public class B2bLogger {

	private final static String LOG_APP_KEY = "LogId";
	
	
	public static String getLogId(){
		return MDC.get(LOG_APP_KEY);
	}
	
	public static void removeLogId() {
		MDC.remove(LOG_APP_KEY);
	}
	

	public static void newLogId(){
		newLogId(getNewLogId());
	}
	
	public static void newLogId(String logId){
		MDC.put(LOG_APP_KEY,logId);
	}
	
	private static String getNewLogId(){
		
		return UUIDUtils.newUuid();
	}


}
