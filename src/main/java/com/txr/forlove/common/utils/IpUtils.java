package com.txr.forlove.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 
 * @author yanglei
 * @version 1.0.1, 2018年7月30日
 * @since 2018年7月30日
 * 
 */
public class IpUtils {
	private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
	private static Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
	public static final String HOST_IP = getHostIP();

	public static String getIp() {
		return HOST_IP;
	}

	private static String getHostIP() {
		String ip = null;
		try {
			InetAddress address = getLocalAddress();
			if (address != null) {
				ip = address.getHostAddress();
			}
		} catch (Throwable ex) {
			logger.error("IpUtils 获取客户端IP地址失败!");
		}

		return ip == null ? getDefaultIp() : ip;
	}

	private static String getDefaultIp() {
		final String key = "deploy_host_ip";
		try {
			return System.getProperty(key, "127.0.0.1");
		} catch (Throwable e) {
			logger.error("Was not allowed to read system property \"" + key + "\".", e);
			return "127.0.0.1";
		}
	}

	/* private static String getLocalIP() { InetAddress address = getLocalAddress(); return ((address == null) ? null :
	 * address.getHostAddress()); } */

	@SuppressWarnings("rawtypes")
	private static InetAddress getLocalAddress() {
		final Map<String, InetAddress> aviableInetAddress = new HashMap<String, InetAddress>();

		InetAddress localAddress = null;
		try {
			localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				aviableInetAddress.put("_LocalHost", localAddress);
			}
		} catch (Throwable e) {
			logger.warn("IpUtils.getLocalHost 获取客户端IP地址失败!", e);
		}

		try {
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
			while ((interfaces != null) && (interfaces.hasMoreElements())){
				try {
					NetworkInterface network = (NetworkInterface) interfaces.nextElement();
					Enumeration addresses = network.getInetAddresses();
					while ((addresses != null) && (addresses.hasMoreElements())){
						try {
							InetAddress address = (InetAddress) addresses.nextElement();
							if (isValidAddress(address)) {
								aviableInetAddress.put(StringUtils.trimToEmpty(network.getName()), address);
							}
						} catch (Throwable e) {
							logger.warn("IpUtils.nextElement 获取客户端IP地址失败!", e);
						}
					}
				} catch (Throwable e) {
					logger.warn("IpUtils.getInetAddresses 获取客户端IP地址失败!", e);
				}
			}
		} catch (Throwable e) {
			logger.warn("IpUtils.getNetworkInterfaces 获取客户端IP地址失败!", e);
		}
		logger.info("找到的可用IP地址: " + aviableInetAddress);

		localAddress = selectInetAddress(aviableInetAddress);

		logger.info("选择的IP地址  = " + localAddress + ", ip = " + localAddress.getHostAddress());
		return localAddress;
	}

	private static InetAddress selectInetAddress(Map<String, InetAddress> aviableInetAddress) {
		if (aviableInetAddress.isEmpty()) {
			return null;
		}

		InetAddress selected = null;
		String[] interfaceName = { "eth0", "eth1", "eth2", "eth3", "eth4", "eth5", "eth6", "eth7", "eth8", };
		for (String name : interfaceName) {
			selected = aviableInetAddress.get(name);
			if (selected != null) {
				return selected;
			}
		}

		selected = aviableInetAddress.get("_LocalHost");
		return selected != null ? selected : (aviableInetAddress.entrySet().iterator().next().getValue());
	}

	private static boolean isValidAddress(InetAddress address) {
		if ((address == null) || (address.isLoopbackAddress()) || !(address instanceof Inet4Address)) {
			return false;
		}
		String ip = address.getHostAddress();
		return ((ip != null) && (!("0.0.0.0".equals(ip))) && (!("127.0.0.1".equals(ip))) && (IP_PATTERN.matcher(ip).matches()));
	}

	/**
	 * 获得服务器名称
	 */

	public static String getServerName() {
		String serverName = "";
		try {
			serverName = InetAddress.getLocalHost().getHostName();
			LOG.info("获取服务器名称:" + serverName);
		} catch (UnknownHostException e) {
			LOG.error("获取服务器名称异常", e);
		}
		return serverName;
	}

	/**
	 * 获得客户端的ip
	 */
	public static String getServerIP() {
		String serverIP = "";
		try {
			serverIP = InetAddress.getLocalHost().getHostAddress();
			LOG.info("获取服务器IP:" + serverIP);
		} catch (UnknownHostException e) {
			LOG.error("获取服务器IP异常", e);
		}
		return serverIP;
	}

	/**
	 * 获取本地所有IP地址
	 *
	 * @return
	 */
	public static List<String> getLocalIP() {
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
						ipList.add(inetAddress.getHostAddress());
					}
				}
			}

			return ipList;
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * 获取浏览器客户端IP地址
	 *
	 * @return
	 * @author cdduqiang
	 * @date 2015年4月17日
	 */
	public static String getClientIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Http_Client_Ip");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		if (ip.indexOf(",") != -1) {
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (!"unknown".equalsIgnoreCase(ips[i])) {
					String splitIp = StringUtils.trimToEmpty(ips[i]);
					// 确保获得ip的最大长度为15
					if (splitIp.length() > 15) {
						splitIp = splitIp.substring(0, 15);
					}
					return splitIp;
				}
			}
		}
		// 确保获得ip的最大长度为15
		if (ip.length() > 15) {
			ip = ip.substring(0, 15);
		}
		return ip;
	}

}
