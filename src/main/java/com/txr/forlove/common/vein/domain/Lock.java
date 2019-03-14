/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.domain;

import java.io.Serializable;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-14 13:28
 */
public class Lock implements Serializable{
    private static final long serialVersionUID = 3676515438522392040L;

    private State state;
    private String ip;
    private Integer deployInstanceId;
    private long lockDate;



    public Lock() {

    }


    public Lock(State state, String ip,Integer deployInstanceId,long lockDate) {
        this.state = state;
        this.ip = ip;
        this.deployInstanceId = deployInstanceId;
        this.lockDate = lockDate;
    }


    public long getLockDate() {
        return lockDate;
    }

    public void setLockDate(long lockDate) {
        this.lockDate = lockDate;
    }

    public Integer getDeployInstanceId() {
        return deployInstanceId;
    }

    public void setDeployInstanceId(Integer deployInstanceId) {
        this.deployInstanceId = deployInstanceId;
    }
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "state=" + state +
                ", ip='" + ip + '\'' +
                ", deployInstanceId=" + deployInstanceId +
                '}';
    }
}
