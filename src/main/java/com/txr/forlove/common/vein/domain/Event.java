/**
 * Copyright(C) 2004-2017 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.domain;

/**
 * <p> 事件类型 </p>
 *  所有的任务都会有 start 和 finish两个整件；
 *  正常的处理流程    ： start --> data --> finish
 *  如果内部发生异常了： start --> data --> exception --> finish
 *  手动中断任务     ： start --> data --> interrupt --> finish
 * @author zhoudedong(周德东) 成都研究院
 * @created 2017-01-24 14:37
 */
public enum Event {
    /***
     * 导出任务开始
     */
    start,
    /***
     * 处理数据中
     */
    data,
    /***
     * 任务被外部中断
     */
    interrupt,
    /***
     * 内部发生异常
     */
    exception,
    /***
     * 全部数据写完之后
     */
    after,
    /***
     * 任务完成
     */
    finish;
}
