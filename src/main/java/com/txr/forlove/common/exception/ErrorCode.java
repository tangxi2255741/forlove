/**
 *
 */
package com.txr.forlove.common.exception;

/**
 * @Description：错误码定义
 * @Author：T.X
 * @CreateTime：2019/4/25-10:50
 */
public enum ErrorCode implements EnumCode<String>{
	/** 成功 */
	SUCCESS("0000", "成功"),
	/**
	 * 参数错误：1***
	 */
	PARAM_NULL("1001", "参数为空"),
	PARAM_FORMAT_ERROR("1002", "参数格式不正确"),
	PARAM_VALUE_ERROR("1003", "参数值不正确"),
	/**
	 * 权限错误：2***
	 */

	/**
	 * 业务异常：6***
	 */
	RULE_INVAILD_ERROR("6001", "规则已失效异常"),
	BIZ_ERROR("6002","业务异常"),

	/**
	 * 系统异常、限流等：5***
	 */
	SYSTEM_ERROR("5001", "系统异常!"),
	DB_ACCESS_ERROR("5002", "数据库操作异常!"),
	JIMDB_ACCESS_ERROR("5003", "JimDb操作异常!"),
	ES_ACCESS_ERROR("5004", "ES访问异常!"),
	ES_RESULT_ERROR("5005", "ES操作结果失败!"),
	JMQ_ACCESS_ERROR("5006", "JMQ操作异常!"),
	JMQ_COMSUMER_ERROR("5007", "JMQ操作异常!"),
	JMQ_PARSE_ERROR("5008", "JMQ操作异常!"),
	RPC_ERROR("5009", "Rpc调用异常!"),
	RPC_RESULT_ERROR("5010", "Rpc调用结果检查异常!"),
	LOCK_ERROR("5011", "加锁失败异常!"),
	/**
	 * 预留：3***
	 */

	/**
	 * 预留：4***
	 */

	;
	private final String code;
	private final String desc;
	ErrorCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
