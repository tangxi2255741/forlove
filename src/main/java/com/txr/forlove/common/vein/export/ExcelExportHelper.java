/**
 * Copyright(C) 2004-2017 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import com.jd.ka.vein.core.EventListener;
import com.jd.ka.vein.domain.JobConfig;
import com.jd.ka.vein.export.out.JFSOutputExcelHandler;
import com.jd.ka.vein.export.out.OutputExcelHandler;
import com.jd.ka.vein.export.out.SimpleOutputExcelHandler;

import java.io.OutputStream;

/**
 * <p> 文件导出工具助手工具 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2017-01-24 15:02
 */
public class ExcelExportHelper<T> {
    protected static ExcelExportHandler excelExportHandler;

    private String module = "vein_export_module";
    private String user = "boo";
    private String fileName = "导出文件";
    private boolean sync = true;
    private OutputExcelHandler outHandler;
    private DataFetchIterator<T> iterator;
    private WriteRowHandler rowHandler;
    private EventListener eventListener;

    private JobConfig jobConfig = new JobConfig();



    public static <T> ExcelExportHelper<T> create(){
        return new ExcelExportHelper<T>();
    }


    /***
     * module 默认： vein_export_module
     * @return
     */
    public ExcelExportHelper<T> module(String module){
        this.module = module;
        return this;
    }

    /***
     * user,默认：boo
     * @return
     */
    public ExcelExportHelper<T> user(String user){
        this.user = user;
        return this;
    }

    /***
     * fileName,默认：导出文件
     * @return
     */
    public ExcelExportHelper<T> fileName(String fileName){
        this.fileName = fileName;
        return this;
    }





    /***
     * 文件异步放到jfs上，同时任务会标记为异步处理，sync = true;
     * @return
     */
    public ExcelExportHelper<T> jfs(){
        this.outHandler = new JFSOutputExcelHandler(excelExportHandler.getStoreCenter());
        this.sync = true;
        return this;
    }

    /***
     * 文件直接输出到servlet中
     * @param outputStream
     * @return
     */
    public ExcelExportHelper<T> servlet(OutputStream outputStream){
        this.outHandler = new SimpleOutputExcelHandler(outputStream);
        this.sync = false;
        return this;
    }

    /***
     * 数据如何翻页查询出来
     * @return
     */
    public ExcelExportHelper<T> dataFetchIterator(DataFetchIterator<T> iterator){
        this.iterator = iterator;
        return this;
    }

    /***
     * 每行数据，如何写入excel的行
     * @return
     */
    public ExcelExportHelper<T> writeRowHandler(WriteRowHandler rowHandler){
        this.rowHandler = rowHandler;
        return this;
    }

    /***
     * 数据处理入进度中的事件监听
     * @return
     */
    public ExcelExportHelper<T> eventListener(EventListener eventListener){
        this.eventListener = eventListener;
        return this;
    }


    /***
     * 当 sync 为true是有效；
     * 在导出过程中，任务检查是否手动终断的频率，默认：100
     * @return
     */
    public ExcelExportHelper<T> chechStopInterval(int chechStopInterval){
        if(this.jobConfig == null){
            this.jobConfig = new JobConfig();
        }
        this.jobConfig.setChechStopInterval(chechStopInterval);
        return this;
    }

    /***
     * 当 sync 为true是有效；
     * 在导出过程中，任务进度信息更新的频率，默认：100
     * @return
     */
    public ExcelExportHelper<T> interval(int interval){
        if(this.jobConfig == null){
            this.jobConfig = new JobConfig();
        }
        this.jobConfig.setInterval(interval);
        return this;
    }


    /***
     * 当 sync 为true是有效；
     * 在导出过程中，任务处理的进度以日志方式输出的频率，默认：500
     * @return
     */
    public ExcelExportHelper<T> infoRows(int infoRows){
        if(this.jobConfig == null){
            this.jobConfig = new JobConfig();
        }
        this.jobConfig.setInfoRows(infoRows);
        return this;
    }
    /***
     * 当 sync 为true、同时配置了eventListener时 有效；
     * 在导出过程中，数据处理的事件通知的频率，默认：100
     * @return
     */
    public ExcelExportHelper<T> eventInterval(int eventInterval){
        if(this.jobConfig == null){
            this.jobConfig = new JobConfig();
        }
        this.jobConfig.setEventInterval(eventInterval);
        return this;
    }


    /***
     * 执行导出任务
     * @param <T>
     * @return
     */
    public <T> Result export(){
        if(excelExportHandler == null){
            throw new RuntimeException("excelExportHandler 没有实例化成功！");
        }
        if(this.outHandler == null){
            jfs();
        }
        return excelExportHandler.export(module,user,fileName,sync,outHandler,
                iterator,rowHandler,eventListener,jobConfig);
    }
}