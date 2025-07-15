package com.tripgain.common;
	import org.apache.poi.ss.usermodel.*;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	import java.io.File;
	import java.io.FileInputStream;
	import java.util.*;

	public class getDataFromExcelTestData {

	  /*  public static List<Map<String, String>> getExcelDataFromSheet(String sheetName) {
	        List<Map<String, String>> dataList = new ArrayList<>();
	        File classpathRoot = new File(System.getProperty("user.dir"));
	        File app = new File(classpathRoot, "src\\test\\resources\\testdata\\testdatamain1.xlsx");
	        String fileName = app.toString();

	        try (FileInputStream fis = new FileInputStream(fileName);
	             Workbook workbook = new XSSFWorkbook(fis)) {

	            Sheet sheet = workbook.getSheet(sheetName);
	            if (sheet == null) {
	                throw new RuntimeException("Sheet not found: " + sheetName);
	            }

	            Row headerRow = sheet.getRow(0);
	            if (headerRow == null) {
	                throw new RuntimeException("Header row is missing in sheet: " + sheetName);
	            }

	            int totalCols = headerRow.getLastCellNum();

	            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	                Row row = sheet.getRow(i);
	                if (row == null) continue;

	                Map<String, String> rowMap = new HashMap<>();
	                boolean hasData = false;

	                for (int j = 0; j < totalCols; j++) {
	                    Cell headerCell = headerRow.getCell(j);
	                    if (headerCell == null) continue;

	                    String key = new DataFormatter().formatCellValue(headerCell);
	                    Cell cell = row.getCell(j);
	                    String value = new DataFormatter().formatCellValue(cell);

	                    if (value != null && !value.trim().isEmpty()) {
	                        hasData = true;
	                    }

	                    rowMap.put(key, value);
	                }

	                // Only add rowMap if the row contains actual data
	                if (hasData) {
	                    dataList.add(rowMap);
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return dataList;
	    }*/
		 public static List<Map<String, String>> getExcelDataFromSheet(String sheetName) {
             List<Map<String, String>> dataList = new ArrayList<>();
             File classpathRoot = new File(System.getProperty("user.dir"));
            
//             File app = new File(classpathRoot, "src\\test\\resources\\testdata\\AutomationTestData.xlsx");
             File app = new File(classpathRoot, "src\\test\\resources\\testdata\\testdatamain1.xlsx");
             String fileName = app.toString();

             try (FileInputStream fis = new FileInputStream(fileName);
                  Workbook workbook = new XSSFWorkbook(fis)) {

                 Sheet sheet = workbook.getSheet(sheetName);
                 if (sheet == null) {
                     throw new RuntimeException("Sheet not found: " + sheetName);
                 }

                 Row headerRow = sheet.getRow(0);
                 if (headerRow == null) {
                     throw new RuntimeException("Header row is missing in sheet: " + sheetName);
                 }

                 int totalCols = headerRow.getLastCellNum();//ex:orgin,destination,etc...last cell value 9 outof 9

                 //Scence header row start from 0 .so we need values from and to location so we are fetching row from 1
                 for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                     Row row = sheet.getRow(i);
                     if (row == null) continue;

                     Map<String, String> rowMap = new HashMap<>();
                     boolean hasData = false;
                     boolean skipRow = false;

                     for (int j = 0; j < totalCols; j++) {
                         /*
                          Gets the column name from the header row (key).
                          Gets the value from the current row and column (value).
                          */
                         Cell headerCell = headerRow.getCell(j);
                         if (headerCell == null) continue;
//DataFormatter makes sure values are read as strings (no need to worry about numbers or dates).
                         String key = new DataFormatter().formatCellValue(headerCell);
                         Cell cell = row.getCell(j);
                         String value = new DataFormatter().formatCellValue(cell);

                         // Check if any cell in the row contains "TestSkip"
                         if ("TestSkip".equalsIgnoreCase(value.trim())) {
                             skipRow = true;
                             break; // Skip rest of the cells in this row
                         }

                         if (value != null && !value.trim().isEmpty()) {
                             hasData = true;
                         }

                         rowMap.put(key, value);
                     }

                     // Add row data if it's not marked as "TestSkip" and has actual data
                     if (!skipRow && hasData) {
                         dataList.add(rowMap);
                     }
                 }

             } catch (Exception e) {
                 e.printStackTrace();
             }

             return dataList;
         }
	}


