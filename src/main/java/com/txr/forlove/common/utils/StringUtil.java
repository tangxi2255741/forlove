package com.txr.forlove.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class StringUtil extends StringUtils {
    public static char STRING_SIGN_EQUAL_MARK = '=';

    public static char STRING_SIGN_COLON = ':';

    public static String getParamValue(String param) {
        return param.substring(param.indexOf("=") + 1);
    }

    public static String getParamValue(String param, char sign) {
        return param.substring(param.indexOf(sign) + 1);
    }

    public static boolean isBlank(String src) {
        return StringUtils.isBlank(src);
    }

    public static boolean isNotBlank(String src) {
        return StringUtils.isNotBlank(src);
    }

    public static String splitStr(String str,int leng){
        if(StringUtils.isBlank(str)){
            return "";
        }
        if(str.length() > leng){
            str = str.substring(0, leng)+"...";
        }
        return str;
    }

    public static String getFilterMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return "";
    }

    public static String getFilterTel(String tel) {
        if (StringUtils.isNotBlank(tel)) {
            return tel.replaceAll("(\\d*\\d)\\d{4}", "$1****");
        }
        return "";
    }


    public static String deleteEmptyChar(String str) {
        if (str==null||str.length()==0) {
            return str;
        }
        return str.replaceAll("\\s","");
    }


    public static String Html2Text(String inputString) {
        if (inputString == null) {
            return "";
        }
        if (StringUtils.isBlank(inputString)){
            return "";
        }
        String htmlStr = inputString;
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;

        Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签   

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签   

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签   

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); //过滤html标签
            htmlStr = htmlStr.replaceAll(" ", "");


            textStr = htmlStr;

        } catch (Exception e) {
            // System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;//返回文本字符串

    }

    public static String toHtml(String str) {
        if (str == null) {
            return "";
        }
        String targetStr = str;
        /**
         * 正常显示的字符串
         */
        String[] normalStrs = {"&", "<", ">", " ", "á", "à", "¤", "°",
                "÷", "é", "ê", "è", "í", "ì", "·", "ó", "ò", "±", "§", "×",
                "ú", "ù", "¨", "ü", "–", "—", "‘", "’", "“", "”", "‰", "…",
                "←", "↑", "→", "↓", "∈", "∏", "∑", "√", "∝", "∞", "∠", "∧",
                "∨", "∩", "∪", "∫", "∴", "≈", "≠", "≡", "≤", "≥"};

        /**
         * html转义后的字符串
         */
        String[] htmlStrs = {"&amp;", "&lt;", "&gt;", "&nbsp;",
                "&aacute;", "&agrave;", "&curren;", "&deg;", "&divide;",
                "&eacute;", "&ecirc;", "&egrave;", "&iacute;", "&igrave;",
                "&middot;", "&oacute;", "&ograve;", "&plusmn;", "&sect;",
                "&times;", "&uacute;", "&ugrave;", "&uml;", "&uuml;",
                "&ndash;", "&mdash;", "&lsquo;", "&rsquo;", "&ldquo;",
                "&rdquo;", "&permil;", "&hellip;", "&larr;", "&uarr;",
                "&rarr;", "&darr;", "&isin;", "&prod;", "&sum;", "&radic;",
                "&prop;", "&infin;", "&ang;", "&and;", "&or;", "&cap;",
                "&cup;", "&int;", "&there4;", "&asymp;", "&ne;", "&equiv;",
                "&le;", "&ge;"};

        for (int i = 0; i < normalStrs.length; i++) {
            targetStr = targetStr.replaceAll(normalStrs[i], htmlStrs[i]);
        }
        return targetStr;
    }

    public static String textToTitle(String str) {
        String s = "";
        if (isNotBlank(str)) {
            s = str.replaceAll("\"", "'");
            s = s.replaceAll(",", ", ");
        }

        return s;
    }


}
