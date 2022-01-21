package com.demo.app.utils;

import com.demo.app.base.TestBase;
import com.demo.app.constants.FrameworkConstants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtility implements FrameworkConstants {

    private static final String SCREENSHOTS_DIRECTORY = System.getProperty("user.dir")
            + TestBase.getConfigProperty(ConfigKeyWords.SCREENSHOT_OUTPUT.toString()).replaceAll("//", File.separator);
    private static final String REPORTS_DIRECTORY = System.getProperty("user.dir")
            + TestBase.getConfigProperty(ConfigKeyWords.REPORT_OUTPUT.toString()).replaceAll("//", File.separator);
    private static final String LOGS_DIRECTORY = System.getProperty("user.dir")
            + TestBase.getConfigProperty(ConfigKeyWords.LOG_OUTPUT.toString()).replaceAll("//", File.separator);
    static boolean deleteSnapsGifsReports = Boolean.parseBoolean(TestBase
            .getConfigProperty(ConfigKeyWords.SNAPS_REPORTS_REMOVAL_BEFORE_RUN.toString()));
    static boolean deleteLogs = Boolean.parseBoolean(TestBase
            .getConfigProperty(ConfigKeyWords.LOGS_REMOVAL_BEFORE_RUN.toString()));

    private CommonUtility() {
        throw new IllegalStateException("Common utility class");
    }

    public static String getDateTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss.ms");
        LocalDateTime current = LocalDateTime.now();
        return current.format(format);
    }

    public static void waitFor(Object object, int timeoutInMilliSeconds) throws InterruptedException {
        synchronized (object) {
            object.wait(timeoutInMilliSeconds);
        }
    }

    public static void waitForAjax(WebDriver driver, int timeoutInSeconds) {
        TestBase.getLogger().info("Checking active ajax calls by calling jquery.active");
        try {
            if (driver instanceof JavascriptExecutor) {
                JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
                for (int i = 0; i < timeoutInSeconds; i++) {
                    Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
                    if (numberOfAjaxConnections instanceof Long) {
                        Long num = (Long) numberOfAjaxConnections;
                        TestBase.getLogger().info("Number of active jquery ajax calls: " + num);
                        if (num == 0L)
                            break;
                    }
                }
            } else {
                TestBase.getLogger().info("Web driver: " + driver + " cannot execute javascript");
            }
        } catch (Exception e) {
            TestBase.getLogger().error("Interrupted in ajax wait", e);
        }
    }

    public static void createFile(String path) {
        new File(path);
    }

    /**
     * Deletes all the files (gifs, logs, reports & screenshots) from the custom output directories
     */
    public static void deleteSnapsGifsReportFiles() throws IOException {
        if (deleteSnapsGifsReports) {
            FileUtils.cleanDirectory(new File(SCREENSHOTS_DIRECTORY));
            FileUtils.cleanDirectory(new File(REPORTS_DIRECTORY));
            TestBase.getLogger().info("Cleared all the existing screenshots, GIFs & reports from its respective directories");
        } else {
            TestBase.getLogger().info("Did not clear all the existing screenshots, GIFs & reports as per the configuration");
        }
    }

    public static void deleteLogFiles() throws IOException {
        if (deleteLogs) {
            File folder = new File(LOGS_DIRECTORY);
            File[] allFiles = folder.listFiles();
            for (int i = 0; i < (allFiles != null ? allFiles.length : 0); i++) {
                String currentLogFile = System.getProperty(ConfigKeyWords.LOG_CURRENT_DATE_TIME.toString());
                String eachFileName = allFiles[i].getName();
                if (!eachFileName.contains(currentLogFile)) {
                    deleteFile(allFiles[i].toPath());
                }
            }
            TestBase.getLogger().info("Cleared the existing execution logs");
        } else {
            TestBase.getLogger().info("Did not clear all the existing logs as per the configuration");
        }
    }

    public static void deleteFile(Path path) throws IOException {
        Files.delete(path);
    }

    public static void deleteFilesWithExtension(String directory, String extension) throws IOException {
        File folder = new File(directory);
        File[] allFiles = folder.listFiles();
        for (int i = 0; i < (allFiles != null ? allFiles.length : 0); i++) {
            if (allFiles[i].getName().endsWith(extension)) {
                Files.delete(allFiles[i].toPath());
            }
        }
    }
}
