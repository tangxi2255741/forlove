/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.task;


import com.txr.forlove.common.vein.domain.Context;

/**
 * <p> job的接口，对外提供执行的入口，和查询进度的功能 </p>
 *
 * 支持分片的Job
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 20:37
 */
public interface SimpleJob<T> extends Job<T> {
    /**
     * 处理一条数据
     */
    void handle(Context ctx, T vo);
}
