package com.txr.forlove.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * POI工具类 功能点：
 * 1、实现excel的sheet复制，复制的内容包括单元的内容、样式、注释
 * 2、setMForeColor修改XSSFColor.YELLOW的色值，setMBorderColor修改PINK的色值
 *
 * @author Administrator
 */
public final class PoiUtil {

	/**
	 * 功能：拷贝sheet
	 * 实际调用 	copySheet(targetSheet, sourceSheet, true)
	 * @param targetSheet
	 * @param sourceSheet
	 * @param copyStyle			boolean 是否拷贝样式
	 */
	public static void copySheet(Sheet targetSheet, Sheet sourceSheet,boolean copyStyle) throws Exception {
		if(targetSheet == null || sourceSheet == null){
			throw new IllegalArgumentException("调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet都不能为空，故抛出该异常！");
		}
		//复制源表中的行
		int maxColumnNum = 0;
		int targetListNum = targetSheet.getLastRowNum();
		if(targetListNum>0){
			for (int i=targetSheet.getFirstRowNum();i<targetListNum;i++){
				targetSheet.removeRowBreak(i);
			}
		}
		int sourceListNum = sourceSheet.getLastRowNum()+1;
		if(sourceListNum>0){
			for (int i=0;i<sourceListNum;i++){
				Row sourceRow = sourceSheet.getRow(i);
				if(sourceRow==null){
					targetSheet.createRow(i);
					continue;
				}
				Row targetRow = targetSheet.createRow(i);
				copyRow(targetRow,sourceRow,copyStyle);
				if (sourceRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = sourceRow.getLastCellNum();
				}
			}
		}
		//复制源表中的合并单元格
		mergerRegion(targetSheet, sourceSheet);
		//设置目标sheet的列宽
		for (int i = 0; i <= maxColumnNum; i++) {
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
	}

	public static void copyRow(Row targetRow, Row sourceRow,boolean copyStyle) throws Exception {
		if(targetRow == null || sourceRow == null){
			throw new IllegalArgumentException("调用PoiUtil.copyRow()方法时，targetRow、sourceRow都不能为空，故抛出该异常！");
		}
		//设置行高
		targetRow.setHeight(sourceRow.getHeight());
		/*if(copyStyle){
			targetRow.setRowStyle(sourceRow.getRowStyle());
		}*/
		for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
			Cell sourceCell = sourceRow.getCell(i);
			Cell targetCell = targetRow.getCell(i);
			if (sourceCell != null) {
				if (targetCell == null) {
					targetCell = targetRow.createCell(i);
				}
				//拷贝单元格，包括内容和样式
				copyCell(targetCell, sourceCell, copyStyle);
			}
		}
	}


	/**
	 * 功能：拷贝cell，依据styleMap是否为空判断是否拷贝单元格样式
	 * @param targetCell			不能为空
	 * @param sourceCell			不能为空
	 * @param copyStyle				可以为空
	 */
	public static void copyCell(Cell targetCell, Cell sourceCell,boolean copyStyle) {
		if(targetCell == null || sourceCell == null ){
			throw new IllegalArgumentException("调用PoiUtil.copyCell()方法时，targetCell、sourceCell 都不能为空，故抛出该异常！");
		}

		//处理单元格样式
		if(copyStyle){
			targetCell.setCellStyle(sourceCell.getCellStyle());
			targetCell.setCellType(sourceCell.getCellType());
		}
		//处理单元格内容
		switch (sourceCell.getCellType()) {
//			case Cell.CELL_TYPE_STRING:
//				targetCell.setCellValue(sourceCell.getRichStringCellValue());
//				break;
//			case Cell.CELL_TYPE_NUMERIC:
//				targetCell.setCellValue(sourceCell.getNumericCellValue());
//				break;
//			case Cell.CELL_TYPE_BLANK:
//				targetCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
//				break;
//			case Cell.CELL_TYPE_BOOLEAN:
//				targetCell.setCellValue(sourceCell.getBooleanCellValue());
//				break;
//			case Cell.CELL_TYPE_ERROR:
//				targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
//				break;
//			case Cell.CELL_TYPE_FORMULA:
//				targetCell.setCellFormula(sourceCell.getCellFormula());
//				break;
			default:
				break;
		}
	}

	/**
	 * 功能：复制原有sheet的合并单元格到新创建的sheet
	 *
	 * @param targetSheet
	 * @param sourceSheet
	 */
	public static void mergerRegion(Sheet targetSheet, Sheet sourceSheet)throws Exception {
		if(targetSheet == null || sourceSheet == null){
			throw new IllegalArgumentException("调用PoiUtil.mergerRegion()方法时，targetSheet或者sourceSheet不能为空，故抛出该异常！");
		}
		for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
			CellRangeAddress newRange = new CellRangeAddress(
					oldRange.getFirstRow(), oldRange.getLastRow(),
					oldRange.getFirstColumn(), oldRange.getLastColumn());
			targetSheet.addMergedRegion(newRange);
		}
	}
}