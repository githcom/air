package Helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <h1>The ParseXls class contains methods to work with MS Excel files.</h1>
 */
public class ParseXls {

    public static final String FILE_PATH_COMMENTS = "src//test//resources//data//comments.xlsx";
    public static XSSFSheet SHEET;

    public static XSSFSheet getSheet(String filePath, String sheetName) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "//" + filePath));
        } catch (IOException e) {
            Report.exitTest("getSheet: " + e.getMessage());
        }
        XSSFSheet sheet = workbook.getSheet(sheetName);
        return sheet;
    }

    public static void setSheet(String sheetName, String filePath) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "//" + filePath));
        } catch (IOException e) {
            Report.exitTest("getSheet: " + e.getMessage());
        }
        XSSFSheet sheet = workbook.getSheet(sheetName);
        ParseXls.SHEET = sheet;
    }

    //column headers should be in the first row
    public static List<String> getColumnByValue(String columnTitle) {
        return getColumnByValue(columnTitle, SHEET);
    }

    //column headers should be in the first row
    public static List<String> getColumnByValue(String columnTitle, XSSFSheet sheet) {
        Row row = sheet.getRow(0);
        List<String> columnList = new ArrayList<>();
        int columnNum = 0;
        for (Cell cell : row) {
            String val = cell.getStringCellValue().trim();
            if (val.equals(columnTitle)) {
                columnNum = cell.getColumnIndex();
                break;
            }
        }
        for (Row currentRow : sheet) {
            Cell cell = currentRow.getCell(columnNum);
            if (cell != null) {
                cell.setCellType(CellType.STRING);
                String val = cell.getStringCellValue().trim();
                columnList.add(val);
            }
        }
        columnList.remove(0);
        return columnList;
    }

    public static Map<String, String> getKeyValueMap(String key, String value) {
        return getKeyValueMap(key, value, SHEET);
    }

    public static Map<String, String> getKeyValueMap(String key, String value, XSSFSheet sheet) {
        List<String> keys = getColumnByValue(key, sheet);
        List<String> values = getColumnByValue(value, sheet);
        if (keys.size() != values.size()) {
            Report.exitTest("getKeyValueMap: wrong lists size ");
        }
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }

    public static Map getKeysAndMultipleValuesMapByRows(XSSFSheet sheet, String key, String... values) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> keys = getColumnByValue(key, sheet);
        List<List<String>> rowsList = getRowsByColumnTitles(sheet, values);
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), rowsList.get(i));
        }
        return map;
    }

    public static Map getKeysAndMultipleValuesMapByRows(String key, String... values) {
        return getKeysAndMultipleValuesMapByRows(SHEET, key, values);
    }

    //Takes all column titles if corresponding variable is empty
    public static Map getColumnsAsKeysAndMultipleValuesMap(XSSFSheet sheetName, String... columnTitles) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> titlesList = new ArrayList<>();
        if (columnTitles.length == 0) {
            for (int index = 0; index < sheetName.getRow(0).getLastCellNum(); index++) {
                XSSFCell currentCell = sheetName.getRow(0).getCell(index);
                currentCell.setCellType(CellType.STRING);
                String title = currentCell.getStringCellValue().trim();
                titlesList.add(title);
            }
        } else {
            for (String title : columnTitles) {
                titlesList.add(title);
            }
        }
        for (String title : titlesList) {
            List<String> ColumnList = getColumnByValue(title, sheetName);
            map.put(title, ColumnList);
        }
        return map;
    }

    public static Map getKeysAndMultipleValuesMapByColumns(String key, String... values) {
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> keys = getColumnByValue(key);
        List<List<String>> valuesColumnList = new ArrayList<List<String>>();
        for (int i = 0; i < values.length; i++) {
            List<String> column = new ArrayList<>();
            column = getColumnByValue(values[i]);
            valuesColumnList.add(column);
        }
        for (int i = 0; i < keys.size(); i++) {
            List<String> valuesList = new ArrayList<String>();
            for (int j = 0; j < values.length; j++) {
                valuesList.add(valuesColumnList.get(j).get(i));
            }
            map.put(keys.get(i), valuesList);
        }
        return map;
    }

    public static List<Boolean> getColumnBooleanListByValue(String columnTitle) {
        List<Boolean> columnList = new ArrayList<>();
        int columnNum = getColumnNumberByTitle(columnTitle);
        for (Row currentRow : SHEET) {
            Cell cell = currentRow.getCell(columnNum);
            if (cell != null) {
                cell.setCellType(CellType.STRING);
                String res = cell.getStringCellValue();
                if (res.toUpperCase().equals("TRUE") || res.equals("FALSE")) {
                    cell.setCellType(CellType.BOOLEAN);
                    Boolean val = cell.getBooleanCellValue();
                    columnList.add(val);
                }
            }
        }
        return columnList;
    }

    private static int getColumnNumberByTitle(String columnTitle, XSSFSheet sheet) {
        Row row = sheet.getRow(0);
        int columnNum = 0;
        for (Cell cell : row) {
            String val = cell.getStringCellValue();
            if (val.equals(columnTitle)) {
                columnNum = cell.getColumnIndex();
                break;
            }
        }
        return columnNum;
    }

    private static int getColumnNumberByTitle(String columnTitle) {
        return getColumnNumberByTitle(columnTitle, SHEET);
    }

    public static List<List<String>> getRowsByColumnTitles(String... columnTitles) {
        return getRowsByColumnTitles(SHEET, columnTitles);
    }

    public static List<List<String>> getRowsByColumnTitles(XSSFSheet sheetName, String... columnTitles) {
        int startIndex = getColumnNumberByTitle(columnTitles[0], sheetName);
        int endIndex = getColumnNumberByTitle(columnTitles[columnTitles.length - 1], sheetName);
        List<List<String>> rowsList = new ArrayList<List<String>>();
        for (int rowIndex = 1; rowIndex < sheetName.getPhysicalNumberOfRows(); rowIndex++) {
            Row currentRow = sheetName.getRow(rowIndex);
            List<String> row = new ArrayList<>();
            for (int i = startIndex; i <= endIndex; i++) {
                Cell cell = currentRow.getCell(i);
                if (cell == null) {
                    break;
                }
                cell.setCellType(CellType.STRING);
                row.add(cell.getStringCellValue());
            }
            if (row.size() == 0) {
                break;
            }
            rowsList.add(row);
        }
        return rowsList;
    }

    public static Map setRealValues(List<String> listForReplacement, Map<String, String> mapWithValues) {
        List<String> cloneList = listForReplacement;
        for (Map.Entry<String, String> keyValue : mapWithValues.entrySet()) {
            Collections.replaceAll(cloneList, keyValue.getKey(), keyValue.getValue());
        }
        Map<String, String> mapWithResult = new HashMap<>();
        for (int i = 0; i < listForReplacement.size(); i++) {
            mapWithResult.put(listForReplacement.get(i), cloneList.get(i));
        }
        return mapWithResult;
    }

    public static Map setRealValues(Map<String, List<String>> mapWithKeys, Map<String, String> mapWithValues) {
        for (Map.Entry<String, List<String>> entry : mapWithKeys.entrySet()) {
            List<String> values = entry.getValue();
            for (Map.Entry<String, String> keyValue : mapWithValues.entrySet()) {
                Collections.replaceAll(values, keyValue.getKey(), keyValue.getValue());
            }
        }
        Map<String, List<String>> mapWithResult = mapWithKeys;
        return mapWithResult;
    }

}
