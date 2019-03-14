/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export.out;

import com.jd.ka.vein.export.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-23 14:25
 */
public interface OutputExcelHandler {
    OutputStream getOutputStream() throws IOException;
    void setFileInfo(FileInfo fileInfo);
    File getFile();
    void write();
}