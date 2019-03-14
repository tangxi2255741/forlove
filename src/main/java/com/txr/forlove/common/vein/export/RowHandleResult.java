/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-20 13:16
 */
public class RowHandleResult {
    private Sheet sheet; //处理的sheet
    private int rowNumIdx; //在这个sheet中的多少行

    public RowHandleResult(){

    }
    public RowHandleResult(Sheet sheet, int rowNumIdx) {
        this.sheet = sheet;
        this.rowNumIdx = rowNumIdx;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public int getRowNumIdx() {
        return rowNumIdx;
    }
}
