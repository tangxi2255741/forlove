/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export.out;

import com.txr.forlove.common.vein.export.FileInfo;
import com.txr.forlove.common.vein.export.StoreCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-23 14:30
 */
public class JFSOutputExcelHandler implements OutputExcelHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JFSOutputExcelHandler.class);
    private StoreCenter storeCenter;
    private FileInfo fileInfo;
    private File file;

    public JFSOutputExcelHandler(StoreCenter storeCenter){
        this.storeCenter = storeCenter;
    }
    @Override
    public OutputStream getOutputStream() throws IOException {
        if(file == null){
            createFile();
        }
        return new FileOutputStream(file);
    }

    @Override
    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public File getFile() {
        if(file == null){
            createFile();
        }
        return file;
    }

    @Override
    public void write() {
        storeCenter.storeFile(fileInfo.getUrl(),file);
    }

    private synchronized void createFile(){
        String tmpdir = System.getProperty("java.io.tmpdir");
        String tmp = UUID.randomUUID().toString()+".tmp";
        LOGGER.info("export 临时文件目录:" + tmpdir + "/" +tmp);
        file = new File(tmpdir + "/" + tmp);
    }



}