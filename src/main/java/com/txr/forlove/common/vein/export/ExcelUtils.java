/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-19 14:41
 */
public class ExcelUtils {

    /**
     * 创建一个单元格
     * @param row
     * @param column
     * @param content 内容
     */
    public static void createCell(CellStyle style, Row row, int column, Object content){
        Cell cell = row.createCell(column);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        String val = "";
        if(content != null){
            if(content instanceof java.sql.Date){
                val = FastDateFormat.getInstance("yyyy-MM-dd").format(content);
            } else if(content instanceof Date || content instanceof Calendar){
                val = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(content);
            } else {
                val = content.toString();
            }
        }
        cell.setCellValue(new XSSFRichTextString(val));
    }

    public static CellStyle getCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat df = workbook.createDataFormat();
        style.setDataFormat(df.getFormat("TEXT"));
        return style;
    }

    public static FileInfo bulidFileUrl(String module, String user, String fileName) {
        String url = "";
        if(StringUtils.isNotBlank(user)){
            url = user+"/";
        }
        if(StringUtils.isNotBlank(module)){
            url = url + module+"/";
        }
        String storeFile = null;
        if(StringUtils.isBlank(fileName)){
            storeFile = UUID.randomUUID().toString() +"_"+ RandomStringUtils.random(8, true, true)+".xlsx" ;
        } else {
            storeFile = stringFilter(FilenameUtils.getBaseName(fileName)) +"_"+ RandomStringUtils.random(8,true,true)+".xlsx" ;
        }
        url = url + storeFile;
        FileInfo f = new FileInfo(module,user,storeFile,url);
        return f;
    }

    private static Pattern P = Pattern.compile("[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
    private static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        Matcher m = P.matcher(str);
        return m.replaceAll("_").trim();
    }
}
