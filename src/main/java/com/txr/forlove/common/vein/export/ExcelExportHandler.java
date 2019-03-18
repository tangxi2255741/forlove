/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;
import com.txr.forlove.common.vein.EventListener;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.txr.forlove.common.vein.ProgressCtrlCenter;
import com.txr.forlove.common.vein.domain.Context;
import com.txr.forlove.common.vein.domain.Event;
import com.txr.forlove.common.vein.domain.JobConfig;
import com.txr.forlove.common.vein.domain.ProgressResult;
import com.txr.forlove.common.vein.export.out.JFSOutputExcelHandler;
import com.txr.forlove.common.vein.export.out.OutputExcelHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;
import java.util.concurrent.*;


/**
 * <p> 数据导出 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-07 17:49
 */
public class ExcelExportHandler implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelExportHandler.class);

    private static final String KEY_FILE_NAME = "fileName";

    private ExecutorService exec;

    public void setExec(ExecutorService exec) {
        this.exec = exec;
    }

    private ProgressCtrlCenter progressCtrlCenter; //导出控制中心

    private int interval;

    /**
     * @deprecated 0.2.0版本这个参数，已无效，请使用ExcelExportHelper
     *
     * @param interval
     */
    public void setInterval(int interval) {
        LOGGER.warn("setInterval 0.2.0版本这个参数，已无效，请使用ExcelExportHelper");
        this.interval = interval;
    }

    public ProgressCtrlCenter getProgressCtrlCenter() {
        return progressCtrlCenter;
    }

    public void setProgressCtrlCenter(ProgressCtrlCenter progressCtrlCenter) {
        this.progressCtrlCenter = progressCtrlCenter;
    }

    private StoreCenter storeCenter;

    public StoreCenter getStoreCenter() {
        return storeCenter;
    }

    public void setStoreCenter(StoreCenter storeCenter) {
        this.storeCenter = storeCenter;
    }

    /*****UMP配置****/
    private boolean enableUmp;  //是否使用ump监控，如果为true,umpPrefix和appName不能为空
    private String umpPrefix;   //ump的前缀
    private String appName;     //ump需要到哪个应用

    public void setEnableUmp(boolean enableUmp) {
        this.enableUmp = enableUmp;
    }

    public void setUmpPrefix(String umpPrefix) {
        this.umpPrefix = umpPrefix;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /*****UMP配置[END]****/

    private Result downloadOrderCheck(int count,int downLoadLimt) {
        try {
            LOGGER.info("export downloadOrderCheck.[count={},downLoadLimt={}].", count,downLoadLimt);
            if(count < 0){
                return Result.create()
                        .succeed();
            }
            if(count == 0){
                return Result.failure("查询到0条记录！");
            }
            if (downLoadLimt > 0 && count > downLoadLimt) {
                return Result.failure("一共查询到"+ count +"条记录, 已超出导出上限"+ downLoadLimt);
            }
            return Result.create()
                    .succeed()
                    .count(count)
                    .tip("一共查询到" + count + "条记录");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            return Result.failure("查询总行数异常").tip(e.getMessage());
        }
    }


    public <T> Result export(final String module, final String user, final String fileName,
                             final DataFetchIterator<T> iterator,final WriteRowHandler rowHandler){
        return export(module,user,fileName,true,new JFSOutputExcelHandler(storeCenter),iterator,rowHandler);
    }
    /**
     *
     * @param module 模块，可以为空
     * @param user 当前用户名，可以不空，有来进行数据权限的
     * @param fileName 文件名称，可以为空，不能有特殊字符，如有，
     * @param iterator
     */
    public <T> Result export(final String module, final String user, final String fileName,
                             final boolean sync,
                             final OutputExcelHandler outHandler,
                             final DataFetchIterator<T> iterator,final WriteRowHandler rowHandler){
        return export(module,user,fileName,sync,outHandler,iterator,rowHandler,null,
                new JobConfig(this.interval,this.interval,this.interval,this.interval));
    }


    /**
     *
     * @param module 模块，可以为空
     * @param user 当前用户名，可以不空，有来进行数据权限的
     * @param fileName 文件名称，可以为空，不能有特殊字符，如有，
     * @param iterator
     */
    public <T> Result export(final String module, final String user, final String fileName,
                             final boolean sync,
                             final OutputExcelHandler outHandler,
                             final DataFetchIterator<T> iterator,final WriteRowHandler rowHandler,
                             final EventListener eventListener,
                             JobConfig cfg){
        //如果是异步的，则才加锁
        if(sync) {
            boolean lock = progressCtrlCenter.tryLock(module, user);
            LOGGER.info("export lock={}.[module={},user={},fileName={}].", lock, module, user, fileName);
            if (!lock) {
                return Result.failure("当前已有导出任务在运行，请稍后.");
            }
        }
        if(cfg == null){
            cfg = new JobConfig();
        }
        Result result = null;
        try {
            String[] heads = iterator.heads();
            LOGGER.info("export heads.[heads={}].", Arrays.toString(heads));

            if (heads == null || heads.length == 0) {
                String tip = "heads is empty";
                //异常的才写进度信息
                if(sync) {
                    progressCtrlCenter.finish(module, user, false, tip, -1);
                }
                return Result.failure(tip);
            }
            int count = iterator.count();
            int downLoadLimt = iterator.downLoadLimt();

            result = downloadOrderCheck(count, downLoadLimt);
            if (!result.isSuccess()) {
                //异常的才写进度信息
                if(sync) {
                    progressCtrlCenter.finish(module, user, false, result.getMessage(), -1);
                }
                return result;
            }

            final FileInfo fileInfo = ExcelUtils.bulidFileUrl(module, user, fileName);
            LOGGER.info("bulidFileUrl_module={}, user={}, fileName={}, fileInfo={}", module, user, fileName, fileInfo);
            outHandler.setFileInfo(fileInfo);
            result = Result.create().succeed().count(count).url(fileInfo.getUrl()).tip("开始导出..");

            final Context ctx = new Context(module,user);
            ctx.setCount(count);
            Map<String,Object> context = new HashMap<String, Object>(2);
            String soreFileName = fileInfo.getStoreFileName();
            context.put(KEY_FILE_NAME, soreFileName);
            ctx.putAddition(KEY_FILE_NAME, soreFileName);


            if(sync) {
                final JobConfig finalCfg = cfg;

                //异步的才写进度信息
                progressCtrlCenter.createProgress(module, user, "tip-createProgress", count, context);
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            execExport(sync,fileInfo,outHandler, iterator, rowHandler,eventListener, finalCfg,ctx);
                        } catch (Exception e) {
                            LOGGER.error("VIN:003-run,ex=" + e.getMessage(), e);
                        }
                    }
                });
            } else {
                execExport(sync,fileInfo,outHandler, iterator, rowHandler,eventListener,cfg,ctx);
            }
            result.setMessage("导出任务成功启动！");
            LOGGER.info("result={}", result);
        } catch (RejectedExecutionException re){
            String msg = "导出线程池已满，请稍后重试！";
            LOGGER.error("VIN:002-export;"+msg+",ex=" + re.getMessage(), re);
            if(sync) {
                progressCtrlCenter.finish(module, user, false, msg, -1);
            }
        } catch (Exception e) {
            String msg = "创建任务异常";
            LOGGER.error("VIN:001-export;"+msg+",ex=" + e.getMessage(), e);
            if(sync) {
                progressCtrlCenter.finish(module, user, false, msg, -1);
            }
        }
        return result;
    }


    /**
     *
     */
    private  <T> void execExport(boolean sync,FileInfo fileInfo,final OutputExcelHandler outHandler,DataFetchIterator<T> iterator,
                                 WriteRowHandler rowHandler,final EventListener eventListener,
                                 final JobConfig cfg,final Context ctx){
//        CallerInfo callerInfo = null;
        String module = fileInfo.getModule();
        if(enableUmp) {
//            callerInfo = Profiler.registerInfo(umpPrefix + "vein.ExcelExportHandler.export."+module,
//                    appName, false, true);
        }
        long st = System.currentTimeMillis();


        String user = fileInfo.getUser();
        String fileName = fileInfo.getStoreFileName();


        SXSSFWorkbook workbook = null;
        File file = null;
        OutputStream out = null;
        int rownum = 0;
        boolean succeed = true;
        String tip = "init";


        try {
            String tmpdir = System.getProperty("java.io.tmpdir");
            String tmp = UUID.randomUUID().toString()+".tmp";
            LOGGER.info("export 临时文件目录:" + tmpdir + "/" +tmp+",storeFileName="+fileName);

            file = outHandler.getFile();
            out = outHandler.getOutputStream();
            workbook = new SXSSFWorkbook();

            //回写事件信息
            if(eventListener != null){
                eventListener.handle(Event.start,ctx, workbook);
            }

            int count = ctx.getCount();

            CellStyle style = ExcelUtils.getCellStyle(workbook);

            Sheet sh = workbook.createSheet();

            //1.导出表头
            Row head = sh.createRow(rownum);
            int column = 0;

            String[] heads = iterator.heads();
            int len = heads.length;
            for (String str : iterator.heads()) {
                ExcelUtils.createCell(style, head, column, str);
                column++;
            }
            rownum++;
            Map<String,Object> context = ctx.getAddition();


            //异步的才写进度信息
            if(sync) {
                progressCtrlCenter.createProgress(module, user, "tip-createProgress", count, context);
            }


            int rownumIdx = rownum;
            int maxrow = SpreadsheetVersion.EXCEL2007.getLastRowIndex() - 100;
            int page = 1;
            T last = null;
            WriteRowContext writeRowContext = new WriteRowContext(workbook,style,maxrow,sh,len);
            List<T> list = iterator.fetch(page,last);
            while (!CollectionUtils.isEmpty(list)) {
                for (T dto : list) {
                    RowHandleResult rhr = rowHandler.handle(rownumIdx,dto,writeRowContext);
                    sh = rhr.getSheet();
                    writeRowContext.sheet = sh;
                    rownumIdx = rhr.getRowNumIdx();
                    last = dto;
                    rownumIdx++;
                    rownum++;


                    //回写事件信息
                    if(eventListener != null && rownum % cfg.getEventInterval() == 0){
                        ctx.setRowNum(rownum);
                        eventListener.handle(Event.data,ctx,dto);
                    }

                    //进度日志
                    if (rownum % cfg.getInfoRows() == 0) {
                        LOGGER.info(">> export (rownum/count)=({}/{})", rownum, count);
                    }

                    //更新进度
                    if(sync && rownum % cfg.getInterval() == 0) {
                        String format = iterator.format(dto);
                        LOGGER.info("format=={}",format);
                        progressCtrlCenter.updateProgress(module, user, format, (rownum - 1));
                    }

                    //检查是否外部执行手动 终止任务
                    if(sync && rownum % cfg.getChechStopInterval() == 0) {
                        //检查是否需要退出
                        if(progressCtrlCenter.checkStop(module,user)){
                            succeed = true;
                            tip = "任务终止退出!";
                            LOGGER.warn("{}.[module={},shardId={},rowNum={}]",tip,module,user,rownum);
                            progressCtrlCenter.updateProgress(module,user,tip,rownum);
                            //回写事件信息
                            if(eventListener != null){
                                ctx.setRowNum(rownum);
                                eventListener.handle(Event.interrupt,ctx,dto);
                            }
                            return ;
                        }
                    }

                }
                page++;
                list = iterator.fetch(page,last);
            }

            progressCtrlCenter.updateProgress(module, user, "明细数据写入完成", (rownum - 1));
            //回写事件信息
            if(eventListener != null){
                eventListener.handle(Event.after,ctx,workbook);
            }

            workbook.write(out);
            out.flush();
            LOGGER.info("execExport finish. rownum={},url={}", (rownum - 1), fileInfo.getUrl());
            outHandler.write();
            succeed = true;
            tip = "导出成功！总用时"+(System.currentTimeMillis() - st)+"(ms)，导出总数据："+(rownum - 1)+"条";

        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
            succeed = false;
            tip = "导出数据出现IO异常,ex="+e.getMessage();
            //回写事件信息
            if(eventListener != null){
                eventListener.handle(Event.exception,ctx,e);
            }
        }
//        catch (StorageClientException e) {
//            LOGGER.error(e.getMessage(),e);
//            succeed = false;
//            tip = "文件上传JFS异常,ex="+e.getMessage();
//            //回写事件信息
//            if(eventListener != null){
//                eventListener.handle(Event.exception,ctx,e);
//            }
//        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            succeed = false;
            tip = "导出数据未知异常，ex="+e.getMessage();
            //回写事件信息
            if(eventListener != null){
                eventListener.handle(Event.exception,ctx,e);
            }
        } finally {
            //回写事件信息
            if(eventListener != null){
                eventListener.handle(Event.finish,ctx,succeed);
            }
            //异步的才写进度信息
            if(sync) {
                progressCtrlCenter.finish(module, user, succeed, tip, (rownum - 1));
                progressCtrlCenter.unLock(module, user);
            }
            try {
                if(workbook != null){
                    workbook.dispose();
                }
            } catch (Exception ignore){
                LOGGER.error(ignore.getMessage(),ignore);
            }
            try {
                if(out != null) { out.close();}
            }catch (Exception ignore){
                LOGGER.error(ignore.getMessage(),ignore);
            }
            //删除tmp文件
            try {
                if(file != null) { file.delete(); }
            }catch (Exception ignore){
                LOGGER.error(ignore.getMessage(),ignore);
            }

            //ump 相关
//            if(callerInfo != null){
//                if(!succeed){
//                    Profiler.functionError(callerInfo);
//                }
//                Profiler.registerInfoEnd(callerInfo);
//            }
        }
    }


    /**
     * 获取导出进度
     * @param module
     * @param user
     * @return
     */
    public ProgressResult progress(String module, String user) {
        try {
            ProgressResult result = progressCtrlCenter.getProgress(module, user);
            if (result == null) {
                result = ProgressResult.failure("找不到进度信息");
            }
            return result;
        } catch (Exception e){
            LOGGER.error("VIN:002-getProgress.ex="+e.getMessage(),e);
            return ProgressResult.failure("获取进度信息异常");
        }
    }

    /**
     * 手动解锁导出任务锁，
     * 因为有可能，任务正在执行过程中，实例异常宕机，任务锁就不能自动释放；这时就需要手动来解锁；
     * @param module
     * @param user
     * @return
     */
    public  void unLock(String module,String user){
        progressCtrlCenter.unLock(module,user);
        progressCtrlCenter.finish(module,user,false,"执行手动任务解锁",-1);
    }

    public URI downFile(String url) {
        LOGGER.info("downFile url={}", url);
        return storeCenter.downFile(url);
    }

    public List<Map<String, Object>> fileList(String module,String user){
        LOGGER.info("fileList module={},user={}" , module,user);
        return storeCenter.fileList(module, user);
    }

    public void deleteStoreFile(String url){
        LOGGER.info("deleteStoreFile url={}", url);
        storeCenter.deleteStoreFile(url);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.exec == null){
            exec = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(10),
                    new ThreadFactoryBuilder().setDaemon(true).setNameFormat("vein异步导出-%d").build(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        ExcelExportHelper.excelExportHandler = this;
    }
}
