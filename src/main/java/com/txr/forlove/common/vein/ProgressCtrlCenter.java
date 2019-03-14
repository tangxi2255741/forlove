/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein;

import com.jd.ka.vein.domain.Lock;
import com.jd.ka.vein.domain.ProgressResult;

import java.util.Map;

/**
 * <p> 进度控制中心，主要需要实现分布式锁 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 12:46
 */
public interface ProgressCtrlCenter {
    /**
     * 尝试对模块添加任务锁，如果加锁成功，返回true；失败返回false;
     * @param module 导出模块编号，可以为空
     * @param user 当前用户，可以不空
     * @return
     */
    boolean tryLock(String module, String shardId);


    /**
     * 手动解锁导出任务锁，
     * 因为有可能，任务正在执行过程中，实例异常宕机，任务锁就不能自动释放；这时就需要手动来解锁；
     * @param module
     * @param shardId
     * @return
     */
    boolean unLock(String module, String shardId);

    /**
     * 停止当前正在执行的任务
     * @param module
     * @param shardId
     */
    boolean stop(String module, String shardId);

    /**
     * 攻取当前锁的信息
     * @param module
     * @param shardId
     * @return
     * @throws Exception
     */
    Lock getLock(String module, String shardId) throws Exception;

    /**
     * 创建一个进度信息
     */
    void createProgress(String module, String shardId, String tip, int count, Map<String, Object> context);

    /**
     * 更新进度
     * @param module
     * @param shardId
     * @param tip 提示信息
     * @param rowNum 当前已处理的总量
     */
    void updateProgress(String module, String shardId, String tip, int rowNum) throws Exception;

    /**
     * 获取导出进度
     * @param module
     * @param shardId
     * @return
     */
    ProgressResult getProgress(String module, String shardId) throws Exception;

    /**
     * 任务执行完成
     * @param module
     * @param shardId
     * @param succeed 是否处理成功
     * @param tip
     */
    void finish(String module, String shardId, boolean succeed, String tip, int rowNum);

    /**
     * 检查是否退出
     * @param module
     * @param shardId
     * @return true:表示当前任务已需要停止，false：正常运行中
     */
    boolean checkStop(String module, String shardId);
}
