/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein;

import com.jd.ka.vein.domain.Context;
import com.jd.ka.vein.task.SimpleJob;

import java.util.List;

/**
 * <p> job的接口，对外提供执行的入口，和查询进度的功能 </p>
 *
 * 支持分片的Job
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 20:37
 */
public abstract class ShardingJob<T> implements SimpleJob<T> {
    /**
     * 需要处理的数据总量；只是用总进度预估使用的；
     *  <0(小于0) :表示不需要预发知道有多少总量；
     *  =0(等于0) :表示当前任务没有数据需要执行；
     * @return
     */
    public abstract int count(String shardId);
    /***
     * 查询数据
     * @param shardId
     * @param page 从1开始
     * @param last 最后处理到一行数据,当page为1时，last为空
     * @return 如果返回List为空（list== null || list.size == 0）时，则表示任务已经处理完成；
     */
    public abstract  List<T> fetch(String shardId,int page,T last);

    /**
     * 处理一条数据
     */
    public abstract void handle(T vo);

    /**
     * 进度提示信息，可以为空；如果返回空，则自动输出rowNum+vo.toString()
     * @param count
     * @param rowNum
     * @param vo
     * @return
     */
    public abstract String format(int count, int rowNum, T vo);



    @Override
    public void handle(Context ctx, T vo) {
        this.handle(vo);
    }

    @Override
    public int count(Context ctx, String shardId) {
        return this.count(shardId);
    }

    @Override
    public List<T> fetch(Context ctx, int page, T last) {
        return this.fetch(ctx.getShardId(),page,last);
    }

    @Override
    public String format(Context ctx, T vo) {
        return this.format(ctx.getCount(),ctx.getRowNum(),vo);
    }
}
