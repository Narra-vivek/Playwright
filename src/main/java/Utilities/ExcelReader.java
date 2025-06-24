package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    static String fileName;
	static String Sheetname;
    private static String filePath = System.getProperty("user.dir") + "/src/test/resources/testData/";

    // Load Excel Sheet
    public static void loadExcel(String fileName, String sheetName) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath + fileName));
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Excel file: " + fileName + ", sheet: " + sheetName, e);
        }
    }

    // Get cell value by DataSet name and column name
    public static String getValue(String dataSet, String key) {
        try {
            int totalRows = sheet.getPhysicalNumberOfRows();
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i < totalRows; i++) {
                Row currentRow = sheet.getRow(i);
                Cell dataSetCell = currentRow.getCell(0);

                if (dataSetCell != null && dataSetCell.getStringCellValue().trim().equalsIgnoreCase(dataSet)) {
                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        if (headerRow.getCell(j).getStringCellValue().trim().equalsIgnoreCase(key)) {
                            return getCellValue(currentRow.getCell(j));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get value for DataSet: " + dataSet + ", Key: " + key, e);
        }
        return null;
    }

    // Convert Cell to String
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return cell.toString().trim();
        }
    }
    public static Map<String, Map<String, String>> getExcelValue() {
        Map<String, Map<String, String>> excelData = new HashMap<>();
        try {
            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath + fileName)));
            sheet = workbook.getSheet(Sheetname);
            Row header = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null) continue;
                String dataSet = row.getCell(0).getStringCellValue().trim();
                if (dataSet.isEmpty()) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 1; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String key = header.getCell(j).getStringCellValue().trim();
                    String value = getCellValue(cell);
                    rowData.put(key, value);
                }
                excelData.put(dataSet, rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading Excel sheet data");
        }
        return excelData;
    }

    public ExcelReader(String fileName,String  sheetname) {
		// TODO Auto-generated constructor stub
		this.fileName=fileName;
		
			this.Sheetname=sheetname;
		
	}
    // Get entire row as key-value map
    public static Map<String, String> getRowData(String dataSet) {
        Map<String, String> rowData = new HashMap<>();
        try {
            int totalRows = sheet.getPhysicalNumberOfRows();
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i < totalRows; i++) {
                Row currentRow = sheet.getRow(i);
                Cell dataSetCell = currentRow.getCell(0);

                if (dataSetCell != null && dataSetCell.getStringCellValue().trim().equalsIgnoreCase(dataSet)) {
                    for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                        String key = headerRow.getCell(j).getStringCellValue().trim();
                        String value = getCellValue(currentRow.getCell(j));
                        rowData.put(key, value);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get row data for DataSet: " + dataSet, e);
        }
        return rowData;
    }
}
