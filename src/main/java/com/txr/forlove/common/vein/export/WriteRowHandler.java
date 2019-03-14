/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

/**
 * <p> 一条业务，如果写入到excel表格中去 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-20 13:15
 */
public interface WriteRowHandler<T> {
    /***
     * 一条业务数据，写入表格的业务逻辑
     * @param rowNumIdx 新的行号
     * @param dto 业务数据
     * @param context 导出操作的上下文信息，包括的信息有：当前的workbook,当前的sheet,当前一个sheet最大允许的最大行数，表格头的总长度
     * @return
     */
    RowHandleResult handle(int rowNumIdx, T dto, WriteRowContext context);
}
