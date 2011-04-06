package com.tesense.dpsintegration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIWrietExcelFile {

	public static void main(String[] args) {

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet firstSheet = workbook.createSheet();

		HSSFRow headerRow = firstSheet.createRow(0);
		(headerRow.createCell(0)).setCellValue(new HSSFRichTextString("Type"));
		(headerRow.createCell(1))
				.setCellValue(new HSSFRichTextString("Action"));
		(headerRow.createCell(2)).setCellValue(new HSSFRichTextString(
				"Refrence"));
		(headerRow.createCell(3)).setCellValue(new HSSFRichTextString(
				"Customer Name"));
		(headerRow.createCell(4)).setCellValue(new HSSFRichTextString(
				"Address 1"));
		(headerRow.createCell(5)).setCellValue(new HSSFRichTextString(
				"Customer Info 1"));
		(headerRow.createCell(6)).setCellValue(new HSSFRichTextString(
				"Container"));

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("CreateExcelDemo.xls"));
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}