/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-09-20 13:27
 */
public abstract class AbstractWriteRowHandler<T> implements WriteRowHandler<T>  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWriteRowHandler.class);
    /**
     * 处理的方式；可以格处理：cell;可以行处理：row;
     * @return
     *  如果返回{@link Type#cell},则需要重写{@link #cell(Object, int)} 方法
     */
    protected Type getType(){
        return Type.cell;
    }

    /**
     * 一行中的第column列是写什么值；
     * @param dto 当前的业务数据对象
     * @param column 从0开始
     * @return
     */
    protected Object cell(T dto, int column){
        throw new RuntimeException("没有实现！");
    }

    /**
     * 当前这条数据，如何写入到excel表格中一行中；
     * @param dto 当前的业务数据对象
     * @param row 当前的新行
     * @param style 格式
     * @param headLen 表格头的总长度，大于0的
     */
    protected void writeRow(T dto, Row row, CellStyle style, int headLen){
        throw new RuntimeException("没有实现！");
    }

    @Override
    public RowHandleResult handle(int rowNumIdx, T dto, WriteRowContext context) {
        Sheet sh = context.sheet;
        int rowIdx = rowNumIdx;
        if (rowNumIdx >= context.maxRows) {
            sh = context.workbook.createSheet();
            rowIdx = 0;
            LOGGER.info(">> export createSheet .....SheetName="+sh.getSheetName());
        }
        Row row = sh.createRow(rowIdx);
        if(getType() == null || getType() == Type.cell) {
            writeRowData(dto, row, context.style, context.headLen);
        } else {
            writeRow(dto, row,context.style, context.headLen);
        }
        return new RowHandleResult(sh,rowIdx);
    }

    private void writeRowData(T dto, Row row, CellStyle style, int len) {
        int column = 0;
        while (len > column) {
            ExcelUtils.createCell(style, row, column, cell(dto, column));
            column++;
        }
    }

}
