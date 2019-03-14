/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.exception;

/**
 * <p> 数据导出异常信息 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 11:37
 */
public class ExportException extends RuntimeException {

    private static final long serialVersionUID = 7151772239704931683L;

    public ExportException(String message) {
        super(message);
    }
}
