package com.qautomation.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.qautomation.base.Base;

public class WriteExcel extends Base{

private static WriteExcel writeexcel;
	

	public static WriteExcel getInstance() {
		writeexcel = new WriteExcel();
		return writeexcel;
	}
	
	public void updateExeclTestData(String TestCaseIdName, String testerName, String browser, String status, String message) throws Exception
	{
		action.loadConfig();
		FileInputStream fi = new FileInputStream(path);
		XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fi);
		XSSFSheet sheet = workbook.getSheet("TestSummary");
		int rows = sheet.getLastRowNum();
		for (int i =0; i< rows;i++)
		{
			Row row = sheet.getRow(i + 1);
			Cell cell = row.getCell(0);
			if (cell.getStringCellValue().toLowerCase().contains(TestCaseIdName))
			{
				int cols = row.getLastCellNum();
				for (int j =0;j<cols;j++)
				{
					row = sheet.getRow(0);
					cell = row.getCell(j);
					if (cell.getStringCellValue().contains("Browser"))
					{
						row = sheet.getRow(i + 1);
						cell = row.createCell(j);
						cell.setCellValue(browser);
						CellStyle style = workbook.createCellStyle();
						style.setBorderTop(BorderStyle.THIN);
						style.setBorderBottom(BorderStyle.THIN);
						style.setBorderLeft(BorderStyle.THIN);
						style.setBorderRight(BorderStyle.THIN);
						cell.setCellStyle(style);
					}
					else if (cell.getStringCellValue().contains("Status"))
					{
						row = sheet.getRow(i + 1);
						cell = row.createCell(j);
						CellStyle style = workbook.createCellStyle();
						if (status.contains("Passed")) {
							style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
							style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							cell.setCellStyle(style);}
						if (status.contains("Skipped")) {
							style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
							style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							cell.setCellStyle(style);}
						if (status.contains("Failed")) {
							style.setFillForegroundColor(IndexedColors.RED.getIndex());
							style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							cell.setCellStyle(style);}
						cell.setCellValue(status);
						style.setBorderTop(BorderStyle.THIN);
						style.setBorderBottom(BorderStyle.THIN);
						style.setBorderLeft(BorderStyle.THIN);
						style.setBorderRight(BorderStyle.THIN);
						cell.setCellStyle(style);

					}
					else if (cell.getStringCellValue().contains("Test_Executed_By"))
					{
						row = sheet.getRow(i + 1);
						cell = row.createCell(j);
						cell.setCellValue(testerName);
						CellStyle style = workbook.createCellStyle();
						style.setBorderTop(BorderStyle.THIN);
						style.setBorderBottom(BorderStyle.THIN);
						style.setBorderLeft(BorderStyle.THIN);
						style.setBorderRight(BorderStyle.THIN);
						cell.setCellStyle(style);

					}
					else if (cell.getStringCellValue().contains("Test_Executed_Report"))
					{
						row = sheet.getRow(i + 1);
						cell = row.createCell(j);
						cell.setCellValue(System.getProperty("user.dir") + "\\ExtentReports\\" + fileName);
						CellStyle style = workbook.createCellStyle();
						style.setBorderTop(BorderStyle.THIN);
						style.setBorderBottom(BorderStyle.THIN);
						style.setBorderLeft(BorderStyle.THIN);
						style.setBorderRight(BorderStyle.THIN);
						cell.setCellStyle(style);

					}
					else if (cell.getStringCellValue().contains("Message"))
					{
						row = sheet.getRow(i + 1);
						cell = row.createCell(j);
						cell.setCellValue(message);
						CellStyle style = workbook.createCellStyle();
						style.setBorderTop(BorderStyle.THIN);
						style.setBorderBottom(BorderStyle.THIN);
						style.setBorderLeft(BorderStyle.THIN);
						style.setBorderRight(BorderStyle.THIN);
						cell.setCellStyle(style);

					}
				}
				
			}
			
		}
		FileOutputStream fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();

	}
	
	
}
