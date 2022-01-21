package com.demo.app.datadrivers;

import com.demo.app.constants.FrameworkConstants;
import com.demo.app.utils.CsvUtility;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class TestDataProvider {

    @DataProvider(name = "testData")
    public Object[][] getTestData(Method method) {
        CsvUtility csvData = new CsvUtility();
        return csvData.getData(FrameworkConstants.ConfigKeyWords.DATA_CSV.toString(), method.getName());
    }
}
