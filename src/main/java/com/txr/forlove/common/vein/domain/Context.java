/**
 * Copyright(C) 2004-2017 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2017-01-24 14:46
 */
public class Context {
    /**
     * 当前任务的模块标识
     */
    private String module;
    /***
     * 子任务、分片标识
     */
    private String shardId;

    /**
     * 任务开始时间
     */
    private long startDate;
    /***
     * 当前任务的状态
     */
    private State state;
    /**
     * 数据总条数,如果小于0（<0）则表示这个任务总量没有预估
     */
    private int count = -1;
    /**
     * 当前已处理的总量
     */
    private int rowNum = 0;
    /***
     * 其他的辅助信息
     * 文件导出时: fileName字段
     *
     */
    private Map<String,Object> addition;




    public Context(){}

    public Context(String module, String shardId) {
        this.module = module;
        this.shardId = shardId;
        this.startDate = System.currentTimeMillis();
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public Map<String, Object> getAddition() {
        return addition;
    }

    public void setAddition(Map<String, Object> addition) {
        this.addition = addition;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    /***
     * 添加一个附加我信息
     * @param key
     * @param val
     */
    public void putAddition(String key,Object val){
        if(this.addition == null){
            this.addition = new HashMap<String, Object>();
        }
        this.addition.put(key,val);
    }

    @Override
    public String toString() {
        return "Context{" +
                "module='" + module + '\'' +
                ", shardId='" + shardId + '\'' +
                ", startDate=" + startDate +
                ", state=" + state +
                ", count=" + count +
                ", rowNum=" + rowNum +
                ", addition=" + addition +
                '}';
    }
}