package com.txr.forlove.common.utils;


import java.math.BigDecimal;

/**
 * 金额工具
 *
 */
public class PriceUtils {
	 /**金额为分的格式 */    
    private  static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
	/**
	 * 分转元  保留两位小数点
	 */
	public static BigDecimal changeF2Y(Long amount) throws Exception{
	    return null;
    }   
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额  
	 * @param amount
	 * @return
	 */
    public static Long changeY2F(BigDecimal amount){
        return null;
    } 

	public static void main(String[] args) throws Exception {
	}

}
