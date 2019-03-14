/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.domain;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-14 11:31
 */
public enum State {
    /***
     * 初始化状态
     */
    start,
    /**
     * 运行中
     */
    run,
    /***
     * 通知退出
     */
    interrupt,
    /***
     * 任务正常退出中
     */
    close,
    /***
     * 内部发生异常
     */
    exception,
    /***
     * 结束
     */
    end;
}
