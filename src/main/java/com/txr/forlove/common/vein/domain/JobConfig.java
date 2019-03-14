/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.domain;

import java.io.Serializable;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-14 18:12
 */
public class JobConfig implements Serializable {
    private static final long serialVersionUID = -6156581836704952675L;

    /***
     * 每处理多少行数据，打印一行日志
     */
    private int infoRows = 500;
    /***
     * 每处理多少行数据，检查一下任务是否需要终止
     */
    private int chechStopInterval = 100;
    /***
     * 每处理多少行数据，需要同步一下进度信息到控制中心
     */
    private int interval = 100;
    /***
     * 数据处理过程中通知的间隔
     */
    private int eventInterval = 100;

    public JobConfig(){}


    public JobConfig(int infoRows, int chechStopInterval, int interval) {
        this(infoRows,chechStopInterval,interval, 100);
    }

    public JobConfig(int infoRows, int chechStopInterval, int interval,int eventInterval) {
        if(infoRows > 0) {
            this.infoRows = infoRows;
        }
        if(chechStopInterval > 0) {
            this.chechStopInterval = chechStopInterval;
        }
        if(interval > 0) {
            this.interval = interval;
        }
        if(eventInterval > 0) {
            this.eventInterval = eventInterval;
        }
    }

    public int getInfoRows() {
        return infoRows;
    }

    public void setInfoRows(int infoRows) {
        this.infoRows = infoRows;
    }

    public int getChechStopInterval() {
        return chechStopInterval;
    }

    public void setChechStopInterval(int chechStopInterval) {
        this.chechStopInterval = chechStopInterval;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getEventInterval() {
        return eventInterval;
    }

    public void setEventInterval(int eventInterval) {
        this.eventInterval = eventInterval;
    }

    @Override
    public String toString() {
        return "JobConfig{" +
                "infoRows=" + infoRows +
                ", chechStopInterval=" + chechStopInterval +
                ", interval=" + interval +
                ", eventInterval=" + eventInterval +
                '}';
    }
}
