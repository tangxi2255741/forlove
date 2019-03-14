/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

/**
 * <p>  </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-08 12:58
 */
public class Result {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 提示消息，如果{@link #success} 为false,则#message为失败的详细说明
     */
    private String message;
    /**
    /**
     * 更加详细的信息
     */
    private String tip;
    /**
     * 数据总条数
     */
    private int count;

    private String url;


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /////////////////////


    public boolean isSuccess() {
        return success;
    }

    public static Result failure(String message) {
        Result result = new Result();
        result.success = false;
        result.message = message;
        return result;
    }

    public static Result create() {
        Result result = new Result();
        return result;
    }

    public Result succeed() {
        this.success = true;
        return this;
    }

    public Result count(int count) {
        this.count = count;
        return this;
    }

    public Result tip(String tip) {
        this.tip = tip;
        return this;
    }


    public Result url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", tip='" + tip + '\'' +
                ", count=" + count +
                ", url='" + url + '\'' +
                '}';
    }
}
