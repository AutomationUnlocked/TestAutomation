package fileReaders;


import logging.Log;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static Workbook workbook = null;
    private static Sheet sheet = null;
    private static Row row = null;
    private static Cell cell = null;
    static DataFormatter dataFormatter = new DataFormatter();


    public static void getRowData(String fileLocation, String sheetName, int rowNum) {
        sheet = getWorkSheet(fileLocation, sheetName);
    }


    public static void getColumnData(String fileLocation, String sheetName, int columnNum) {
        sheet = getWorkSheet(fileLocation, sheetName);

    }

    public static List<String> getColumnData(String fileLocation, String sheetName, String columnName) {
        List<String> columnValues = new ArrayList<>();
        sheet = getWorkSheet(fileLocation, sheetName);
        int colNum = getColumnNum(2, columnName);
        int lastRow = getLastNonEmptyRowNum(1);
        for (int i = 4; i <= lastRow; i++)
            columnValues.add(getCellData(i, colNum));
        return columnValues;
    }

    public static Map<String, String> getTextLabelMap(String fileLocation, String sheetName, String textIDColumn, String labelColumn) {
        Map<String, String> textIdLabel = new HashMap<>();
        sheet = getWorkSheet(fileLocation, sheetName);
        int colNum = getColumnNum(2, textIDColumn);
        int colNumValue = getColumnNum(2, labelColumn);
        int lastRow = getLastNonEmptyRowNum(1);
        for (int i = 4; i <= lastRow; i++) {

            if (getCellData(i, colNum).length() > 4) {
                textIdLabel.put(getCellData(i, colNum), getCellData(i, colNumValue));
            }

        }

        return textIdLabel;
    }

    private static int getLastNonEmptyRowNum(int colNum) {
        int rowIndex = 0;
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            if (getCellData(rowNum, colNum).length() >= 1)
                rowIndex = rowNum;
            else
                break;
        }
        return rowIndex;
    }

    private static String getCellData(int rowNum, int colNum) {
        cell = sheet.getRow(rowNum).getCell(colNum);
        return dataFormatter.formatCellValue(cell);
    }

    private static int getColumnNum(int rowNum, String columnName) {
        int colNum = -1;
        row = sheet.getRow(rowNum);
        for (Cell cell : row) {
            String richText = String.valueOf(cell.getRichStringCellValue());
            if (richText.equalsIgnoreCase(columnName))
                colNum = cell.getColumnIndex();
        }
        if (colNum == -1)
            Log.warn("The column : " + columnName + " could not be found in excel sheet : " + sheet.getSheetName());

        return colNum;


    }

    private static Sheet getWorkSheet(String fileLocation, String sheetName) {
        FileInputStream fis;
        File file = new File(fileLocation);
        try {
            fis = new FileInputStream(file);
            workbook = WorkbookFactory.create(fis);
            sheet = workbook.getSheet(sheetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }
}
