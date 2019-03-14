/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * <p> 文件存储中心 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 13:28
 */
public interface StoreCenter {
    /***
     * 保存文件
     * @param module
     * @param user
     * @param fileName
     * @param file
     */
    void storeFile(String url, File file) ;

    /**
     * 下载文件
     * @param url
     */
    URI downFile(String url);

    /**
     * 文件列表
     * @param module
     * @param user
     * @return
     */
    List<Map<String, Object>> fileList(String module, String user);

    /**
     * 删除文件
     * @param url
     */
    void deleteStoreFile(String url);
}
