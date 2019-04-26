package com.txr.forlove.common.exception;

/**
 * @Description：错误信息
 * @Author：T.X
 * @CreateTime：2019/4/25-10:50
 */
public enum ExtMessage {
    error_ware("错误商品列表"),
    task_save_result("任务保存结果!"),
    task_executed_context("任务执行结果类型!"),
    ;
    private final String desc;

    private ExtMessage(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
    
}
