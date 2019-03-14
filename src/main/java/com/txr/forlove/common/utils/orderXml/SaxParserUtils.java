package com.txr.forlove.common.utils.orderXml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by cdtangxi on 2015/8/10.
 */
public class SaxParserUtils {
    private static final String DEFAULT_ENCODE = "utf-8";
    private static final String FEATURE_CONTINUE = "http://apache.org/xml/features/continue-after-fatal-error";
    private static final Logger LOGGER = LoggerFactory.getLogger(SaxParserUtils.class);
    private static final Map<String, WatchedNode> ORDER_TRACK_WATCHED_NODE_MAP;

    static {
        ORDER_TRACK_WATCHED_NODE_MAP = new WatchedNodeMapBuilder()
                .append("OrderId", "jdOrderId", FormatterFactory.LONG_FORMATTER)
                .append("Pin", "pin")
                .append("SendPay", "sendPay")
                .append("PaymentType", "paymentType",FormatterFactory.INTEGER_FORMATTER)
                .append("CreateDate", "orderTime",FormatterFactory.DATE_FORMATTER)
                .append("OperateTime", "finishTime",FormatterFactory.DATE_FORMATTER)
                .append("OrderType", "orderType",  FormatterFactory.INTEGER_FORMATTER)
                .append("IdCompanyBranch", "invoiceOrgId",  FormatterFactory.INTEGER_FORMATTER)
                .append("ParentId","parentId", FormatterFactory.LONG_FORMATTER).build();
    }

    /**
     * 订单MQ消息解析。
     *
     * @param xml xml文档内容，不允许为null，
     * @return 解析结果.
     */
    public static Map<String, Object> parseOrderTrackXml(String xml){
        WatchedNodeHandler handler = new WatchedNodeHandler(ORDER_TRACK_WATCHED_NODE_MAP);
        parseXml(xml,  handler);
        return handler.getResult();

    }
    private static void parseXml(String xml, DefaultHandler handler){
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parserFactory.setFeature(FEATURE_CONTINUE, true);
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(new ByteArrayInputStream(xml.getBytes(getEncoding(xml, DEFAULT_ENCODE))), handler);
        } catch (Throwable e) {
            LOGGER.error("解析xml异常,xml="+xml+",ex="+e.getMessage(),e);
        }
    }

    public static String getEncoding(String text, String defaultEncode) {
        return getHeader(text, "encoding", defaultEncode);
    }

    private static String getHeader(String text, String name, String defaultVal) {
        String xml = text.trim();
        String result = defaultVal;
        if (xml.startsWith("<?xml")) {
            int end = xml.indexOf("?>");
            String sub = xml.substring(0, end);
            StringTokenizer tokens = new StringTokenizer(sub, " =\"\'");

            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();

                if (name.equals(token)) {
                    if (tokens.hasMoreTokens()) {
                        result = tokens.nextToken();
                    }

                    break;
                }
            }
        }

        return result;
    }
}
