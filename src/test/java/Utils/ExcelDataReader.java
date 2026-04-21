package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelDataReader {

    public static Map<String, String> getTestDataMap(String sheetName, String keyColumn, String valueColumn) {
        Map<String, String> testDataMap = new HashMap<>();
        try {
            // Specify the path to your Excel file
            String filePath = "DataFiles/TDM.xlsx";

            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            // Get the sheet by name
            Sheet sheet = workbook.getSheet(sheetName);

            // Find the columns indices for key and value
            int keyColumnIndex = -1;
            int valueColumnIndex = -1;

            // Iterate through the header row to find the column indices
            Row headerRow = sheet.getRow(0);
            Iterator<Cell> cellIterator = headerRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String columnHeader = cell.getStringCellValue();
                if (keyColumn.equals(columnHeader)) {
                    keyColumnIndex = cell.getColumnIndex();
                } else if (valueColumn.equals(columnHeader)) {
                    valueColumnIndex = cell.getColumnIndex();
                }
            }

            if (keyColumnIndex != -1 && valueColumnIndex != -1) {
                // Iterate through rows and populate the map
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Cell keyCell = row.getCell(keyColumnIndex);
                    Cell valueCell = row.getCell(valueColumnIndex);

                    if (keyCell != null && valueCell != null && row.getRowNum() != 0) {
                        String key = keyCell.getStringCellValue();
                        String value;
                        if (valueCell.getCellType() == CellType.NUMERIC) {
                            // If the cell contains a numeric value, convert it to a string
                            value = String.valueOf((int) valueCell.getNumericCellValue());
                        } else {
                            // If it's not numeric, treat it as a string
                            value = valueCell.getStringCellValue();
                        }
                        testDataMap.put(key, value);
                    }
                }
            } else {
                System.out.println("Key or Value column not found in the header.");
            }

            // Close the workbook and file input stream
            workbook.close();
            fileInputStream.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }

        return testDataMap;
    }


    public static String inputData(String sheetName,String keyColumn, String valueColumn,String data){

        Map<String, String> testDataMap = getTestDataMap(sheetName, keyColumn, valueColumn);
        String input = testDataMap.get(data);
        return input;
    }



}
