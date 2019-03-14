/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.task;

import com.jd.ka.vein.domain.Context;

import java.util.List;

/**
 * <p> job的接口，对外提供执行的入口，和查询进度的功能 </p>
 *
 * 支持分片的Job
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 20:37
 */
public interface Job<T> {
    /**
     * 需要处理的数据总量；只是用总进度预估使用的；
     *  <0(小于0) :表示不需要预发知道有多少总量；
     *  =0(等于0) :表示当前任务没有数据需要执行；
     * @return
     */
    int count(Context ctx, String shardId);
    /***
     * 查询数据
     * @param ctx
     * @param page 从1开始
     * @param last 最后处理到一行数据,当page为1时，last为空
     * @return 如果返回List为空（list== null || list.size == 0）时，则表示任务已经处理完成；
     */
    List<T> fetch(Context ctx, int page, T last);

    /**
     * 进度提示信息，可以为空；如果返回空，则自动输出rowNum+vo.toString()
     * @param ctx
     * @param vo
     * @return
     */
    String format(Context ctx, T vo);
}
