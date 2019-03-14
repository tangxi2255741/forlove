/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * <p> 处理当行记录时的上下文 信息 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-20 13:53
 */
public class WriteRowContext {

    protected Workbook workbook;
    protected CellStyle style;
    /**
     * 当前一个sheet最大允许的最大行数，
     */
    protected int maxRows;
    protected Sheet sheet;
    protected int headLen;


    public WriteRowContext() {
    }

    public WriteRowContext(Workbook workbook, CellStyle style, int maxRows, Sheet sheet, int headLen) {
        this.workbook = workbook;
        this.style = style;
        this.maxRows = maxRows;
        this.sheet = sheet;
        this.headLen = headLen;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public CellStyle getStyle() {
        return style;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public int getHeadLen() {
        return headLen;
    }
}
