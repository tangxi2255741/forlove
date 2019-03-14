/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import java.util.List;

/**
 * <p> 需要导出的数据是如何获取的 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-07 17:54
 */
public interface DataFetchIterator<T> {



    /**
     * 返回当次查询，会返回从少数据，如果为-1；先不查询总行数，通过fetch
     * @return
     */
    int count();

    /**
     * 当次最大的下载数量限制，如果为小于等于0，则没有下载数量限制
     * @return
     */
    int downLoadLimt();

    /**
     * 返回表格excel的头
     * @return
     */
    String[] heads();

    /**
     * 分页返回数据
     * @param page 当前页数据，从1开始；
     * @param last 最后处理的一行数据信息，当page=1时，入参为空
     * @return
     *
     */
    List<T> fetch(int page, T last);

    /**
     * 用于进度条信息
     * @param dto
     * @return
     */
    String format(T dto);
}
