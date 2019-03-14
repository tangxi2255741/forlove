/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-12 13:58
 */
public class FileInfo {

    private String url;
    private String storeFileName;

    private String module;
    private String user;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public FileInfo(){

    }

    public FileInfo(String module, String user, String storeFileName, String url) {
        this.module = module;
        this.user = user;
        this.storeFileName = storeFileName;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "url='" + url + '\'' +
                ", storeFileName='" + storeFileName + '\'' +
                ", module='" + module + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
