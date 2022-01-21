package com.demo.app.base;

import com.demo.app.constants.FrameworkConstants;
import com.demo.app.pages.PizzaOrderPage;
import com.demo.app.utils.CommonUtility;
import com.demo.app.utils.ExtentReportUtility;
import com.demo.app.utils.ImageUtility;
import com.demo.app.utils.PropertyInitiator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class TestBase implements FrameworkConstants {

    private static Properties configProp;
    private static Properties appMsgProp;
    private static Logger log;
    private static ExtentReportUtility report;
    private static WebDriver driver;
    private static String testCaseName;
    private static Set<String> screenshotsSet;
    protected PizzaOrderPage pizzaOrderPage;

    @BeforeSuite(alwaysRun = true)
    public void init() {
        try {
            setConfigProp();
            setAppMsgProp();
            setLogger();
            setReport();
            CommonUtility.deleteSnapsGifsReportFiles();
            CommonUtility.deleteLogFiles();
            getReport().startReport();
        } catch (Exception e) {
            getLogger().error("Unable to complete base Setup", e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTestCase(Method method) {
        try {
            setScreenshotsSet();
            setTestCaseName(method.getName());
            getReport().startTest(getTestCaseName());
            String url = getConfigProperty(ConfigKeyWords.URL.toString());
            driver = DriverManager.initializeDriver(getConfigProperty(ConfigKeyWords.BROWSER.toString()));
            getDriver().manage().window().maximize();
            getDriver().get(url);
            setPages();
            loadPages();
        } catch (Exception e) {
            getLogger().error("Unable to complete testcase setup", e);

        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterTestCase(ITestResult result) {
        getDriver().quit();
        try {
            ImageUtility.createAnimatedGif(testCaseName,
                    Integer.parseInt(configProp.getProperty(ConfigKeyWords.GIF_SNAPS_INTERVAL_SECS.toString())),
                    Integer.parseInt(configProp.getProperty(ConfigKeyWords.GIF_LOOP.toString())));
        } catch (Exception e) {
            getLogger().error("Unable to complete testcase cleanup", e);
        } finally {
            getReport().endTest();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        try {
            getReport().endReport();
        } catch (Exception e) {
            getLogger().error("Unable to complete final clean up", e);
        }
    }

    private void loadPages() {
        PageFactory.initElements(driver, pizzaOrderPage);
    }

    private void setPages() {
        pizzaOrderPage = new PizzaOrderPage();
    }

    private void setTestCaseName(String testCase) {
        testCaseName = testCase;
        getLogger().info("***************************************************************************************");
        getLogger().info("Staring Test Execution - " + testCase);
        getLogger().info("***************************************************************************************");
    }

    private void setLogger() {
        log = LoggerConfig.loadLogProperties();
    }

    private void setReport() {
        report = new ExtentReportUtility();
    }

    private static void setConfigProp() {
        configProp = PropertyInitiator
                .readProperties(System.getProperty("user.dir") + ConfigKeyWords.CONFIG_PROP_PATH);
    }

    private static void setAppMsgProp() {
        appMsgProp = PropertyInitiator.readProperties(System
                .getProperty("user.dir") + configProp.getProperty(ConfigKeyWords.APP_MSG_PROP.toString()));
    }

    private static void setScreenshotsSet() {
        screenshotsSet = new LinkedHashSet<>();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getTestCaseName() {
        return testCaseName;
    }

    public static Logger getLogger() {
        return log;
    }

    public static ExtentReportUtility getReport() {
        return report;
    }

    public static String getConfigProperty(String key) {
        return configProp.getProperty(key);
    }

    public static String getAppMsgProperty(String key) {
        return appMsgProp.getProperty(key);
    }

    public static Set<String> getScreenshotsSet() {
        return screenshotsSet;
    }
}
