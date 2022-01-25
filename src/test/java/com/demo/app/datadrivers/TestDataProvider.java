package com.demo.app.datadrivers;

import com.demo.app.enums.ConfigKeywords;
import com.demo.app.utils.CsvUtility;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "testData")
    public Object[][] getTestData() {
        CsvUtility csvData = new CsvUtility();
        return csvData.getData(ConfigKeywords.DATA_CSV.toString());
    }
}
