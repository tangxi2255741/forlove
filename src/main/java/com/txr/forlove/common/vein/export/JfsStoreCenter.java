/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import com.jcloud.jss.JingdongStorageService;
import com.jcloud.jss.domain.ObjectListing;
import com.jcloud.jss.domain.ObjectSummary;
import com.jcloud.jss.service.BucketService;
import com.jcloud.jss.service.ObjectService;
import com.jd.ka.vein.exception.ExportException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.*;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 14:27
 */
public class JfsStoreCenter implements StoreCenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JfsStoreCenter.class);

    private JingdongStorageService jss;
    private String bucket;
    private int presignedTime = -1;

    public void setJss(JingdongStorageService jss) {
        this.jss = jss;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setPresignedTime(int presignedTime) {
        this.presignedTime = presignedTime;
    }



    @Override
    public void storeFile(String url, File file) {
        String ps = jss.bucket(bucket).object(url).entity(file).put();
        LOGGER.info("put request:"+ps);
    }

    @Override
    public URI downFile(String url) {
        LOGGER.info("downFile>>url={}",url);
        ObjectService jf = jss.bucket(bucket).object(url);
        if(jf.exist()){
            if(presignedTime > 0) {
                return jf.generatePresignedUrl(presignedTime);
            } else {
                return jf.generatePresignedUrl();
            }
        }else{
            throw new ExportException("文件还未生成，请等待！");
        }
    }

    @Override
    public List<Map<String, Object>> fileList(String module, String user) {

        String path = "";
        if(StringUtils.isNotBlank(user)){
            path = user+"/";
        }
        if(StringUtils.isNotBlank(module)){
            path = path + module+"/";
        }

        BucketService erpBs  =  jss.bucket(bucket).path(path);
        ObjectListing objectListTmp = erpBs.listObject();
        List<ObjectSummary> listTmp = objectListTmp.getObjectSummaries();
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(ObjectSummary os : listTmp){
            String key = os.getKey();
            String[] keys = key.split("/");
            String m = "";
            String u = "";
            String k = key;
            if(keys != null){
                if(keys.length == 3){
                    m = keys[0];
                    u = keys[1];
                    k = keys[2];
                } else if(keys.length == 2){
                    m = keys[0];
                    k = keys[1];
                } else {
                    k = key;
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("module", m);
            map.put("user", u);
            map.put("key", k);
            map.put("size", fmSize(os.getSize()));
            map.put("lastModified", fmDate(os.getLastModified()));
            list.add(map);
        }
        return list;
    }


    /**
     * 将Wed, 23 Sep 2015 02:00:48 GMT的时间格式字符串，格式化为 2015-09-23 10:00:48
     * @param lastModified
     * @return
     */
    private static String  fmDate(String lastModified) {
        try {
            Date dt = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).parse(lastModified);
            return FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(dt);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage()+",lastModified="+lastModified);
        }
        return lastModified;
    }

    private static String fmSize(long size) {
        if(size < 1024){
            return size + "B";
        }
        if(size < 1024 * 1024){
            return String.format("%.2fKB",size/1024.0);
        }
        return String.format("%.2fMB",size/1024.0/1024.0);
    }

    @Override
    public void deleteStoreFile(String url) {
        LOGGER.info("deleteStoreFile>>url={}",url);
        ObjectService jf = jss.bucket(bucket).object(url);
        if(jf.exist()){
            jf.delete();
        }
    }
}
