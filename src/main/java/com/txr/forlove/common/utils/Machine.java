/**
 * Copyright(C) 2004-2015 JD.COM All Right Reserved
 */
package com.txr.forlove.common.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * <p>
 * </p>
 * 
 * @author zhoudedong(周德东) 成都研究院
 * @version 1.0
 * @date 2015-8-28 下午2:05:36
 */
public class Machine {
    public static String getServerIpPort() {
        StringBuffer ips = new StringBuffer("");
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            Enumeration<InetAddress> addresses;
            while (en.hasMoreElements()) {
                NetworkInterface networkinterface = en.nextElement();
                addresses = networkinterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress  ia = addresses.nextElement();
                    String ip = ia.getHostAddress();
                    if(!ia.isLoopbackAddress() && !ia.isLinkLocalAddress() && StringUtils.isNotEmpty(ip)){
                        ips.append(ip).append(",");
                    }
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        if(ips.length() > 0){
            ips.deleteCharAt(ips.length() - 1);
        }
        ips.append(":").append(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        return ips.toString();
    }
    public static String getServerIp(boolean showHost) {
        StringBuffer ips = new StringBuffer("");
        try {
            if(showHost){
                ips.append("Host:" + InetAddress.getLocalHost() + "<br>\n");
            }
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            Enumeration<InetAddress> addresses;
            while (en.hasMoreElements()) {
                NetworkInterface networkinterface = en.nextElement();
                String iname = networkinterface.getName();
                addresses = networkinterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress  ia = addresses.nextElement();
                    String ip = ia.getHostAddress();
                    if(!ia.isLoopbackAddress() && !ia.isLinkLocalAddress() && StringUtils.isNotEmpty(ip)){
                        ips.append(iname)
                        .append(":")
                        .append(ip).append("<br>\n");
                        
                    }
                        
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
           ips.append("\n\tERROR:").append(e.getMessage());
        }
        return ips.toString();
    }
    
    public static String get(String key){
        return System.getProperty(key);
    }
    public static String port(){
        return ManagementFactory.getRuntimeMXBean().getName();
    }
}
