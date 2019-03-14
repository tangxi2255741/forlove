/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.task;

import com.jd.ka.vein.domain.Context;

import java.util.List;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-11-16 12:45
 */
public interface BatchJob<T> extends Job<T> {
    /**
     * 处理一条数据
     * 返回最后处理的一条数据
     */
    T handle(Context ctx, List<T> list);
}