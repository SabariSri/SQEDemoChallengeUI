package com.demo.app.utils;

import com.demo.app.base.TestBase;
import com.demo.app.constants.FrameworkConstants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.SkipException;

import java.io.File;

public class ExtentReportUtility implements FrameworkConstants {

    private ExtentReports extentReports;
    private ExtentTest extentTest;

    public void startReport() {
        String reportOutput = System.getProperty("user.dir") + TestBase.getConfigProperty(ConfigKeyWords.REPORT_OUTPUT
                .toString()).replaceAll("//", File.separator) + "report_" + CommonUtility.getDateTime() + ".html";
        String reportConfig = System.getProperty("user.dir") + TestBase
                .getConfigProperty(ConfigKeyWords.REPORT_CONFIG.toString()).replaceAll("//", File.separator);
        extentReports = new ExtentReports(reportOutput, true);
        extentReports.addSystemInfo("Host Name", "localhost").addSystemInfo("Environment", "QA")
                .addSystemInfo("User Name", "Sri_Sabarinath");
        extentReports.loadConfig(new File(reportConfig));
    }

    public void startTest(String testcaseName) {
        extentTest = extentReports.startTest(testcaseName);
    }

    public void stepPass(String passText) {
        extentTest.log(LogStatus.PASS, passText);
        attachScreenshotDynamically(LogStatus.PASS, ReportStepStatus.STEP_PASS.toString(), false);
        TestBase.getLogger().info(passText);
    }

    public void stepFailErrorStack(String errorText, Throwable error) {
        extentTest.log(LogStatus.FAIL, errorText, error.fillInStackTrace());
        attachScreenshotDynamically(LogStatus.FAIL, ReportStepStatus.STEP_FAIL_ERROR.toString(), true);
        TestBase.getLogger().error(errorText, error);
    }

    public void stepFail(String failText) {
        extentTest.log(LogStatus.FAIL, failText);
        attachScreenshotDynamically(LogStatus.FAIL, ReportStepStatus.STEP_FAIL.toString(), true);
        TestBase.getLogger().error(failText);
    }

    public void stepInfo(String infoText) {
        extentTest.log(LogStatus.INFO, infoText);
        TestBase.getLogger().info(infoText);
    }

    public void stepWarning(String warningText) {
        extentTest.log(LogStatus.WARNING, warningText);
        attachScreenshotDynamically(LogStatus.WARNING, ReportStepStatus.STEP_WARNING.toString(), false);
        TestBase.getLogger().warn(warningText);
    }

    public void stepRecap(String passText, String gifPath) {
        String gif = extentTest.addScreenCapture(gifPath);
        extentTest.log(LogStatus.INFO, "Test Recap " + gif);
        TestBase.getLogger().info(passText);
    }

    public void skipTest() {
        throw new SkipException("Skipping - This is not ready for testing ");
    }

    public void endTest() {
        extentReports.endTest(extentTest);
    }

    public void endReport() {
        extentReports.flush();
        extentReports.close();
    }

    private void attachScreenshotDynamically(LogStatus status, String stepStatus, boolean isStepFail) {
        boolean captureAllSteps = Boolean.parseBoolean(TestBase.getConfigProperty(
                ConfigKeyWords.SCREENSHOT_CAPTURE_ALL_STEPS.toString()));
        String screenshotPath = ImageUtility.takeScreenshotDynamically(TestBase.getTestCaseName(), stepStatus, isStepFail);
        if (screenshotPath != null && captureAllSteps || isStepFail) {
            extentTest.log(status, extentTest.addScreenCapture(screenshotPath));
        }
    }
}
