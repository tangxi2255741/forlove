package com.txr.forlove.common.constants;


/**
 * @Description：监控key
 * @Author：T.X
 * @CreateTime：2019/4/17-15:42
*/
public class UmpConstants {

	public static final String SYSTEM_NAME = "b2b-restrict-sale-cbi"; //Environments.getAppName();
	public static final String SYSTEM_NAME_DEPLOY = "b2b-restrict-sale-cbi"; //Environments.getAppName();
	public static final String OUTLINE_ROOT = SYSTEM_NAME + ".outline";
	/** 方法 */
	public static final String FUNCTION_ROOT = SYSTEM_NAME + ".function";
	public static final String JMQ_CONSUMER_ROOT = SYSTEM_NAME + ".jmq.consumer";
	public static final String JMQ_PRODUCER_ROOT = SYSTEM_NAME + ".jmq.producer";
	public static final String JSF_PROVIDER_ROOT = SYSTEM_NAME + ".jsf.provider";
	public static final String HTTP_PROVIDER_ROOT = SYSTEM_NAME + ".http.provider";
	public static final String DATABASE_ROOT = SYSTEM_NAME + ".database";
	public static final String REDIS_ROOT = SYSTEM_NAME + ".redis";
	public static final String ES_ROOT = SYSTEM_NAME + ".es";
    public static final String JSF_PROVIDER_BIZ_ROOT = JSF_PROVIDER_ROOT + ".biz";
    public static final String JSF_PROVIDER_STATISTICS_ROOT = JSF_PROVIDER_ROOT + ".statistics";
    public static final String STATISTICS_ROOT = SYSTEM_NAME + ".statistics";
	public static final String ALARM_ROOT = SYSTEM_NAME + ".alarm";
	public static final String JMQ_CONSUMER_MONITOR = JMQ_CONSUMER_ROOT + ".";
	/** 系统存活性监控key前缀  */
	public final static String SYSTEM_LIVE_PREFIX = SYSTEM_NAME + ".system.live.systemLive";
	/** jvm监控前缀  */
	public final static String JVM_MONITOR_PREFIX = SYSTEM_NAME + ".system.jvm.systemJvm";
	public static final String JMQ_CONSUMER_HANDER_ERROR = ALARM_ROOT + ".jmq.comsumer.handException.";
	public static final String JMQ_CONSUMER_PARSE_ERROR = ALARM_ROOT + ".jmq.comsumer.parse.";
	public static final String JSF_PROVIDER_ALARM_ROOT = ALARM_ROOT + ".jsf.provider.";
	public static final String ERRORCODE = STATISTICS_ROOT + ".errorCode.";
}
