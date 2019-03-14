/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.task;

import com.jd.ka.vein.ProgressCtrlCenter;
import com.jd.ka.vein.core.EventListener;
import com.jd.ka.vein.domain.Context;
import com.jd.ka.vein.domain.Event;
import com.jd.ka.vein.domain.JobConfig;
import com.jd.ka.vein.domain.Lock;
import com.jd.ka.vein.exception.JobException;
import com.jd.ka.vein.exception.LockException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * <p> job的执行器</p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-14 16:29
 */
public class JobActuator<T> implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(JobActuator.class);


    private JobConfig cfg;
    private String module;
    private Job<T> job;
    private String shardId;
    private ProgressCtrlCenter progressCtrlCenter;

    private Map<String,Object> params;

    private EventListener eventListener;

    public JobActuator(){}

    public JobActuator(String module,String shardId, Job<T> job, ProgressCtrlCenter progressCtrlCenter) {
        this.module = module;
        this.shardId = shardId;
        this.job = job;
        this.progressCtrlCenter = progressCtrlCenter;
    }

    @Override
    public void run() {
        Context ctx = new Context(module,shardId);
        if(eventListener != null){
            eventListener.handle(Event.start,ctx,ctx);
        }
        LOGGER.info("tryLock.[module={},shardId={}]", module, shardId);
        boolean lock = progressCtrlCenter.tryLock(module,shardId);
        LOGGER.info("tryLock==>lock={}.[module={},shardId={}]",lock,module,shardId);
        if(!lock){
            Lock lc = null;
            try {
                lc = progressCtrlCenter.getLock(module, shardId);
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
                if(eventListener != null){
                    eventListener.handle(Event.exception,ctx,e);
                }
            }
            LOGGER.warn("lock-false-exit.[module={},shardId={},lock={}]", module, shardId, lc);
            if(eventListener != null){
                LockException ex = new LockException("获取任务失败，当前已有任务正在执行中!",lc);
                eventListener.handle(Event.exception,ctx,ex);
            }
            return;
        }
        LOGGER.info("Jobconfig.[module={},shardId={},cfg={}]",  module,shardId,cfg);
        long st = System.currentTimeMillis();
        boolean succeed = false;
        String tip = "init";
        int rowNum = 0;

        ctx.putAddition("params",params);
        try{
            int count = job.count(ctx,shardId);
            ctx.setCount(count);
            tip = "需要执行的任务总量有："+count;
            LOGGER.info(tip);
            progressCtrlCenter.createProgress(module,shardId,tip,count,null);
            int page = 1;
            List<T> list = job.fetch(ctx,page,null);
            LOGGER.info("fetch_shardId:{}=>page:{},size={}",shardId,page,(list != null ? list.size() : 0));
            T last = null;
            while (list != null && list.size() > 0){
                //批量处理的job
                if(job instanceof BatchJob){
                    last = ((BatchJob<T>) job).handle(ctx,list);
                    rowNum = rowNum + list.size();
                    ctx.setRowNum(rowNum);

                    if(eventListener != null){
                        eventListener.handle(Event.data,ctx,list);
                    }

                    //更新进度条信息
                    tip = getFormat(ctx, last);
                    progressCtrlCenter.updateProgress(module,shardId,tip,rowNum);
                    LOGGER.info("ctx={},rowNum={},vo={}", ctx, rowNum, last);
                    //检查是否需要退出
                    if(progressCtrlCenter.checkStop(module,shardId)){
                        succeed = true;
                        tip = "批量-任务终止退出!";
                        LOGGER.warn("{}.[module={},shardId={},rowNum={}]", tip, module, shardId, rowNum);
                        progressCtrlCenter.updateProgress(module,shardId,tip,rowNum);
                        return;
                    }
                } else if(job instanceof SimpleJob){ //单条处理的job
                    for(T vo : list){

                        ((SimpleJob)job).handle(ctx,vo);
                        last = vo;
                        rowNum++;
                        ctx.setRowNum(rowNum);

                        if(eventListener != null && rowNum > 0 && rowNum % cfg.getEventInterval() == 0){
                            eventListener.handle(Event.data,ctx,vo);
                        }

                        //更新进度信息
                        if(rowNum > 0 && rowNum % cfg.getInterval() == 0){
                            tip = getFormat(ctx, vo);
                            progressCtrlCenter.updateProgress(module,shardId,tip,rowNum);
                        }
                        //检查是否需要退出
                        if(rowNum > 0 && rowNum % cfg.getChechStopInterval() == 0 && progressCtrlCenter.checkStop
                                (module,shardId)){
                            succeed = true;
                            tip = "单个-任务终止退出!";
                            LOGGER.warn("{}.[module={},shardId={},rowNum={}]", tip, module, shardId, rowNum);
                            progressCtrlCenter.updateProgress(module,shardId,tip,rowNum);

                            if(eventListener != null){
                                eventListener.handle(Event.interrupt,ctx,vo);
                            }

                            return;
                        }
                        //定时打一个进度日志
                        if(rowNum % cfg.getInfoRows() == 0){
                            LOGGER.info("rowNum={},vo={}", rowNum, vo);
                        }
                    }
                } else {
                    throw new JobException("无法识别的job类型."+job.getClass());
                }
                page++;
                list = job.fetch(ctx,  page, last);
                LOGGER.info("fetch_shardId:{}=>page:{},size={}",shardId,page,(list != null ? list.size() : 0));
            }
            succeed = true;
            tip = "任务执行完成！总共处理:"+rowNum+"行数据，总用时:"+(System.currentTimeMillis() - st)+"(ms)";
            progressCtrlCenter.updateProgress(module,shardId,tip,rowNum);
        }  catch (Exception e){
            LOGGER.error("module="+module+",shardId="+shardId+",ex="+e.getMessage(),e);
            tip = "发生异常，任务退出！ex="+e.getMessage();
            succeed = false;
            if(eventListener != null){
                eventListener.handle(Event.exception,ctx,e);
            }
        } finally {
            LOGGER.info("finish==>[module={},shardId={},succeed={},tip={}]",module,shardId,succeed,tip,rowNum);
            progressCtrlCenter.finish(module, shardId, succeed,tip,rowNum);
            progressCtrlCenter.unLock(module, shardId);
            if(eventListener != null){
                eventListener.handle(Event.finish,ctx,succeed);
            }
        }
    }

    private String getFormat(Context ctx, T vo) {
        int rowNum = ctx.getRowNum();
        int count = ctx.getCount();
        try {
            String format = job.format(ctx, vo);
            if(StringUtils.isBlank(format)){

                format = "("+count+"/"+rowNum+") [format is null] vo="+vo;
            }
            return format;
        }catch (Exception e){
            return "("+count+"/"+rowNum+") ，[format出现异常，已忽略] ex="+e.getMessage();
        }
    }


    /**
     * 创建一个任务执行器
     * @param cfg 本任务的相关配置
     * @param progressCtrlCenter 进度控制中心
     * @param job 实际的job
     * @param module 模块，主要用来进行任务锁的控制
     * @param shardId 对应的子任务
     * @return
     */
    public static JobActuator fork(JobConfig cfg,EventListener eventListener,ProgressCtrlCenter progressCtrlCenter, Job job,
                                   String module, String shardId,Map<String,Object> params) {
        JobActuator task = new JobActuator(module,shardId,job,progressCtrlCenter);
        if(cfg == null){
            cfg = new JobConfig();
        }
        task.cfg = cfg;
        task.params = params;
        task.eventListener = eventListener;
        return task;
    }
}
