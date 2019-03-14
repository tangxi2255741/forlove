/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export.out;

import com.jd.ka.vein.export.FileInfo;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-23 14:57
 */
public class SimpleOutputExcelHandler implements OutputExcelHandler {

    private OutputStream outputStream;
    public SimpleOutputExcelHandler(OutputStream outputStream){
        this.outputStream = outputStream;
    }
    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    public void setFileInfo(FileInfo fileInfo) {

    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void write() {
        IOUtils.closeQuietly(outputStream);
    }
}