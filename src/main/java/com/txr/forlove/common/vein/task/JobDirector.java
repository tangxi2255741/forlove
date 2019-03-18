/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.task;

import com.txr.forlove.common.vein.EventListener;
import com.txr.forlove.common.vein.ProgressCtrlCenter;
import com.txr.forlove.common.vein.ShardingJob;
import com.txr.forlove.common.vein.domain.JobConfig;
import com.txr.forlove.common.vein.domain.Lock;
import com.txr.forlove.common.vein.domain.ProgressResult;
import com.txr.forlove.common.vein.exception.JobException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p> 任务的,控制器
 * </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 20:40
 */
public class JobDirector implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDirector.class);
    private static final int DEFAULT_THREADS = 5;
    /**
     * 任务配置
     */
    private Map<String,Job> jobMap;

    private Map<String,JobConfig> jobConfigMaps;

    private Map<String,EventListener> jobEventListener;
    /**
     *锁的控制器
     */
    private ProgressCtrlCenter progressCtrlCenter;
    /**
     * 最大的并发线程数,默认为：5；
     */
    private int nThreads = DEFAULT_THREADS;
    /***
     * 线程池
     */
    private ExecutorService pool;


    public Map<String, Job> getJobMap() {
        return jobMap;
    }

    public void setJobMap(Map<String, Job> jobMap) {
        this.jobMap = jobMap;
    }

    public Map<String, JobConfig> getJobConfigMaps() {
        return jobConfigMaps;
    }

    public void setJobConfigMaps(Map<String, JobConfig> jobConfigMaps) {
        this.jobConfigMaps = jobConfigMaps;
    }

    public void setJobEventListener(Map<String, EventListener> jobEventListener) {
        this.jobEventListener = jobEventListener;
    }

    public ProgressCtrlCenter getProgressCtrlCenter() {
        return progressCtrlCenter;
    }

    public void setProgressCtrlCenter(ProgressCtrlCenter progressCtrlCenter) {
        this.progressCtrlCenter = progressCtrlCenter;
    }

    public int getnThreads() {
        return nThreads;
    }

    public void setnThreads(int nThreads) {
        this.nThreads = nThreads;
    }

    public ExecutorService getPool() {
        return pool;
    }

    public void setPool(ExecutorService pool) {
        this.pool = pool;
    }

    public void execute(String module,String... shardIds){
        this.execute(null,module,shardIds);
    }

    public void execute(Map<String,Object> params,String module,String... shardIds){
        LOGGER.info("params={},module={},shardIds={}", params, module, Arrays.toString(shardIds));
        if(StringUtils.isBlank(module)){
            throw new JobException("module不能为空！");
        }
        if(shardIds == null || shardIds.length == 0){
            LOGGER.error("shardIds is emtry.");
            return;
        }

        Job job = jobMap.get(module);
        if(job == null){
            throw new JobException("找不到对应的ShardingJob.[module="+module+"]");
        }
        if(!(job instanceof BatchJob || job instanceof ShardingJob)){
            throw new JobException("job类型无法处理。"+job.getClass());
        }
        JobConfig cfg = getJobConfig(module);
        if(cfg == null){
            cfg = new JobConfig();
        }

        EventListener eventListener = getEventListener(module);
        for(final String shardId : shardIds){
            LOGGER.info("execute shardId={}，module={}", shardId,module);
            pool.execute(JobActuator.fork(cfg,eventListener,progressCtrlCenter,job,module,shardId,params));
        }
    }

    private EventListener getEventListener(String module) {
        if(jobEventListener != null){
            return jobEventListener.get(module);
        }
        return null;
    }

    private JobConfig getJobConfig(String module) {
        if(jobConfigMaps != null){
            return jobConfigMaps.get(module);
        }
        return null;
    }

    /**
     * 查询进度
     * @param shardId
     * @return
     */
    public ProgressResult getProgress(String module, String shardId) throws Exception {
        return progressCtrlCenter.getProgress(module,shardId);
    }

    /**
     * 手动解锁
     * @param shardId
     */
    public void unLock(String module,String shardId){
        progressCtrlCenter.unLock(module,shardId);
    }


    /**
     * 查询锁信息
     * @param shardId
     */
    public Lock getLock(String module, String shardId) throws Exception {
        return progressCtrlCenter.getLock(module, shardId);
    }

    /**
     * 终止任务
     * @param shardId
     */
    public void stop(String module,String shardId){
        progressCtrlCenter.stop(module,shardId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(pool == null) {
            LOGGER.info("init pool.config_nThreads={}",nThreads);
            if(nThreads <= 0){
                LOGGER.warn("VIN:005-Argument_Warm.nThreads={},小于0，使用默认值{}.",nThreads,DEFAULT_THREADS);
                nThreads = DEFAULT_THREADS;
            }
            pool = Executors.newFixedThreadPool(nThreads);
        }
    }
}
