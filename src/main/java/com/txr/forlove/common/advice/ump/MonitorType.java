package com.txr.forlove.common.advice.ump;

import com.txr.forlove.common.constants.UmpConstants;
import com.txr.forlove.common.enums.Enums;
import com.txr.forlove.common.enums.StringType;

import java.util.Map;

/**
 * 
 * 
 * @since 2016年3月21日
 * @author yanglei
 *
 */
public enum MonitorType implements StringType {
	OUTLINE(UmpConstants.OUTLINE_ROOT, "外部接口监控"),
	JSF_PROVIDER(UmpConstants.JSF_PROVIDER_ROOT, "系统提供的jsf服务监控"),
	DATABASE(UmpConstants.DATABASE_ROOT, "数据库监控"),
	JMQ_PRODUCE(UmpConstants.JMQ_PRODUCER_ROOT, "jmq生产监控"),
	
	REDIS(UmpConstants.REDIS_ROOT, "redis监控"), // 硬编码添加

	ES(UmpConstants.ES_ROOT, "es监控"), // 硬编码添加
	JMQ_CONSUMER(UmpConstants.JMQ_CONSUMER_ROOT, "jmq消费监控"), // 硬编码

	FUNCTION(UmpConstants.FUNCTION_ROOT, "业务方法监控"), // 硬编码添加
	UNDEFINED("undefined", "未知");

	private final String name;
	private final String desc;

	private MonitorType(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	private static final Map<String, MonitorType> HOLDER = Enums.toMap(MonitorType.class.getEnumConstants());

	public static MonitorType value(String state) {
		MonitorType value = HOLDER.get(state);
		return value == null ? UNDEFINED : value;
	}
	@Override
	public String getType() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return new StringBuilder(50).append(getDesc()).append("(").append(getType()).append(", ").append(name()).append(")").toString();
	}
}
