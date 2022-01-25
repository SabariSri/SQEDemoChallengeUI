package com.demo.app.utils;

import com.demo.app.base.TestBase;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvUtility {

    public Object[][] getData(String csvFile) {
        String csvFilePath = System.getProperty("user.dir") + TestBase.getConfigProperty(csvFile)
                .replaceAll("//", File.separator);
        try {
            FileReader filereader = new FileReader(csvFilePath);
            CSVReader csvReader = new CSVReader(filereader);
            List<String[]> csvData = csvReader.readAll();
            String[] headerRow = csvData.get(0);
            int totalColumns = headerRow.length;
            int totalRows = csvData.size();
            Object[][] dataObject = new Object[totalRows - 1][1];
            for (int rowIndex = 1; rowIndex < totalRows; rowIndex++) {
                Map<String, String> testData = new LinkedHashMap<>();
                for (int columnIndex = 0; columnIndex < totalColumns; columnIndex++) {
                    testData.put(headerRow[columnIndex], csvData.get(rowIndex)[columnIndex]
                            .replace("'", "").trim());
                }
                dataObject[rowIndex - 1][0] = testData;
            }
            return dataObject;
        } catch (Exception e) {
            TestBase.getLogger().error("Unable to parse csv file", e);
            return new Object[0][0];
        }
    }
}
