package fileReaders;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import logging.Log;
import org.testng.Assert;

import java.io.FileReader;
import java.util.*;

public class CSVUtils {

    public static void printMap(HashMap<String, Integer> map) {
        Iterator itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry mapElement = (Map.Entry) itr.next();
            System.out.println(mapElement.getKey() + ":" + mapElement.getValue());
        }
    }

    public static void printHashMap(Map<String, String> map) {
        Iterator itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry mapElement = (Map.Entry) itr.next();
            System.out.println(mapElement.getKey() + ":" + mapElement.getValue());
        }
    }


    private static HashMap<String, Integer> getProfiles(String file) {
        HashMap<String, Integer> profiles = new HashMap<String, Integer>();
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            List<String[]> allData = csvReader.readAll();
            int rowNum = 2;
            int colNum = 0;
            for (String[] row : allData) {
                profiles.put(row[colNum], rowNum);
                rowNum++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profiles;
    }

    private static List<Integer> getProfileRowList(String profile, String filePath) {
        List<Integer> result = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            List<String[]> allData = csvReader.readAll();
            int rowNum = 2;

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (profile.equals(row[0])) {
                    result.add(rowNum);
                }
                rowNum++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static HashMap<String, String> mapCSVTestData(String profile, String filePath) {

        HashMap<String, Integer> csvDetails = new HashMap<String, Integer>();
        csvDetails = getProfiles(filePath);
        int selectedProfileRow = csvDetails.get(profile);
        return mapCSVTestData(selectedProfileRow, filePath);

    }

    public static List<HashMap<String, String>> mapCSVTestDataList(String profile, String filePath) {
        List<HashMap<String, String>> result = new ArrayList<>();
        List<Integer> selectedProfileRowList = getProfileRowList(profile, filePath);

        for (int selectedProfileRow : selectedProfileRowList) {
            result.add(mapCSVTestData(selectedProfileRow, filePath));
        }
        return result;
    }

    private static HashMap<String, String> mapCSVTestData(int selectedProfileRow, String filePath) {
        int headerRow = 0;
        HashMap<String, String> testDataMap = new HashMap<String, String>();
        try {
            FileReader filereader = new FileReader(filePath);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            List<String[]> allData = csvReader.readAll();
            String[] attributes = allData.get(headerRow);
            String[] testData = allData.get(selectedProfileRow - 1);
            for (int i = 0; i < attributes.length; i++) {
                testDataMap.put(attributes[i], testData[i]);
            }
        } catch (Exception e) {
            Assert.fail("Exception happened reading the csv file");
        }
        return testDataMap;

    }

    public static HashMap<String, String> mapCSVTestData(String file, int rowNum) {
        HashMap<String, String> testDataMap = new HashMap<String, String>();

        int headerRow = 0;
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            List<String[]> allData = csvReader.readAll();
            allData.size();
            String[] attributes = allData.get(headerRow);
            String[] testData = allData.get(rowNum);
            for (int i = 0; i < attributes.length; i++) {
                testDataMap.put(attributes[i], testData[i]);
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception happened reading the csv file");
        }
        return testDataMap;

    }

    // map a 2 dimenssional array, where the first column will be a key and the second column will be value
    public static HashMap<String, String> mapCSVTestData(String file, String columnName1, String columnName2) {
        HashMap<String, String> testDataMap = new HashMap<String, String>();
        int columnNum1 = 0;
        int columnNum2 = 0;
        boolean column1Found = false, column2Found = false;
        boolean columnsFound = false;
        int headerRow = 0;
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            List<String[]> allData = csvReader.readAll();
            String[] attributes = allData.get(headerRow);
            int noOfColumns = attributes.length;
            for (int i = 0; i < noOfColumns; i++) {
                if (attributes[i].contentEquals(columnName1)) {
                    columnNum1 = i;
                    column1Found = true;
                }
                if (attributes[i].contentEquals(columnName2)) {
                    columnNum2 = i;
                    column2Found = true;
                }
                if (column1Found && column2Found) {
                    columnsFound = true;
                    break;
                }

            }
            Assert.assertTrue(columnsFound, "The column names are not found in the file " + file);
            int noOfRows = allData.size();
            for (int i = 1; i < noOfRows; i++) {
                String[] rowData = allData.get(i);
                testDataMap.put(rowData[columnNum1], rowData[columnNum2]);
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception happened reading the csv file");
        }
        return testDataMap;

    }


    public static int getRowCount(String file) {
        int size = 0;
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            List<String[]> allData = csvReader.readAll();
            size = allData.size();
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception happened reading the csv file");
        }
        Assert.assertTrue(!(size == 0), "There are no lines in the csv file located at " + file);
        return size;

    }

    public static HashMap<String, String> mapCSVTestData(String file, int initColNum, int columnNum) {
        HashMap<String, String> testData = new HashMap<String, String>();
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            int size = allData.size();
            for (int i = 0; i < size; i++) {
                String[] row = allData.get(i);
                testData.put(row[initColNum], row[columnNum]);
            }
        } catch (Exception e) {
            Log.info(" No csv files specified, null will be returned");
        }
        return testData;
    }


    public static List<String> getRowData(String file, String valueTobeCheckedIntheFirstColumn) {
        List<String> listOfRows = new ArrayList<>();

        int headerRow = 0;
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            List<String[]> allData = csvReader.readAll();
            String[] attributes = allData.get(headerRow);
            int noOfColumns = attributes.length;
            int noOfRows = allData.size();
            for (int i = 1; i < noOfRows; i++) {
                String[] rowData = allData.get(i);
                if (rowData[0].contentEquals(valueTobeCheckedIntheFirstColumn)) {
                    String addColumnsData = "";
                    for (int j = 0; j < noOfColumns; j++) {
                        addColumnsData = addColumnsData + rowData[j].replace(";", ",");
                    }
                    listOfRows.add(addColumnsData);
                }
            }

        } catch (Exception e) {
            Assert.assertTrue(false, "Exception happened reading the csv file");
        }
        return listOfRows;

    }


    public static List<String> getColumnData(String file, String ColumnName) {
        List<String> columnData = new ArrayList<>();
        int columnNum = 0;
        boolean columnFound = false;
        int headerRow = 0;
        try {
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            List<String[]> allData = csvReader.readAll();
            String[] attributes = allData.get(headerRow);
            int noOfColumns = attributes.length;
            for (int i = 0; i < noOfColumns; i++) {
                if (attributes[i].contentEquals(ColumnName)) {
                    columnNum = i;
                    columnFound = true;
                    break;
                }
            }
            Assert.assertTrue(columnFound, "The column name " + ColumnName + " is not found in the file " + file);

            int noOfRows = allData.size();
            for (int i = 1; i < noOfRows; i++) {
                String[] rowData = allData.get(i);
                columnData.add(rowData[columnNum]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false, "Exception happened reading the csv file");
        }
        return columnData;

    }


}
