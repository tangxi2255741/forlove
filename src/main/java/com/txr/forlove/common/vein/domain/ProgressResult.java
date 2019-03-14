/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * <p> 进度信息 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-08 12:58
 */
public class ProgressResult implements Serializable{
    private static final long serialVersionUID = 1562604814708312641L;
    /**
     * 是否有异常，没有满足期望的操作时为false;
     */
    private boolean success;
    /**
     * 提示消息，如果{@link #success} 为false,则#message为失败的详细说明
     */
    private String message;

    /**
     * 导出模块编号,不能使用特殊字符
     */
    private String module;
    /**
     * 导出操作者,用来做数据权限隔离,不能使用特殊字符
     */
    private String shardId;

    /**
     * 其他的上下文环境信息
     */
    private Map<String,Object> context;

    /***
     * 任务状态
     */
    private State state;

    /**
     * 任务开始时间
     */
    private long startDate;
    /**
     * 当前处理到的数据信息提示
     */
    private String tip;
    /**
     * 数据总条数,如果小于0（<0）则表示这个任务总量没有预估
     */
    private int count;

    /**
     * 估计剩余用时,单位毫秒（ms），只有当count > 0时会有值
     */
    private long estResidualTimes;
    /**
     * 进度信息最后更新时间
     */
    private long lastUpdateTime;
    /**
     * 当前已处理的总量
     */
    private int rowNum;


    //// getter\setter ////

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getShardId() {
        return shardId;
    }

    public void setShardId(String shardId) {
        this.shardId = shardId;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getEstResidualTimes() {
        return estResidualTimes;
    }

    public void setEstResidualTimes(long estResidualTimes) {
        this.estResidualTimes = estResidualTimes;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    //// 其他操作方法 ////


    public boolean isSuccess() {
        return success;
    }

    public static ProgressResult failure(String message) {
        ProgressResult result = new ProgressResult();
        result.success = false;
        result.message = message;
        return result;
    }



    public static ProgressResult create() {
        ProgressResult result = new ProgressResult();
        return result;
    }

    public ProgressResult succeed() {
        this.success = true;
        return this;
    }

    public ProgressResult count(int count) {
        this.count = count;
        return this;
    }

    public ProgressResult tip(String tip) {
        this.tip = tip;
        return this;
    }


    public ProgressResult jobStartDate(long startDate) {
        this.startDate = startDate;
        return this;
    }

    public static ProgressResult create(String module, String shardId, Map<String,Object> context) {
        ProgressResult result = create();
        result.module = module;
        result.shardId = shardId;
        result.context = context;
        result.state = State.start;
        result.message = "任务创建成功";
        return result;
    }

    public ProgressResult lastUpdateTime(long time) {
        this.lastUpdateTime = time;
        return this;
    }

    public ProgressResult rowNum(int rowNum) {
        this.rowNum = rowNum;
        return this;
    }

    @Override
    public String toString() {
        return "ProgressResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", module='" + module + '\'' +
                ", shardId='" + shardId + '\'' +
                ", context=" + context +
                ", state=" + state +
                ", startDate=" + startDate +
                ", tip='" + tip + '\'' +
                ", count=" + count +
                ", estResidualTimes=" + estResidualTimes +
                ", lastUpdateTime=" + lastUpdateTime +
                ", rowNum=" + rowNum +
                '}';
    }
}
