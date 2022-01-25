package com.demo.app.utils;

import com.demo.app.base.TestBase;
import com.demo.app.enums.ConfigKeywords;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtility {

    private static final String SCREENSHOTS_DIRECTORY = System.getProperty("user.dir")
            + TestBase.getConfigProperty(ConfigKeywords.SCREENSHOT_OUTPUT.toString()).replaceAll("//", File.separator);
    private static final String REPORTS_DIRECTORY = System.getProperty("user.dir")
            + TestBase.getConfigProperty(ConfigKeywords.REPORT_OUTPUT.toString()).replaceAll("//", File.separator);
    private static final String LOGS_DIRECTORY = System.getProperty("user.dir")
            + TestBase.getConfigProperty(ConfigKeywords.LOG_OUTPUT.toString()).replaceAll("//", File.separator);
    static boolean deleteSnapsGifsReports = Boolean.parseBoolean(TestBase
            .getConfigProperty(ConfigKeywords.SNAPS_REPORTS_REMOVAL_BEFORE_RUN.toString()));
    static boolean deleteLogs = Boolean.parseBoolean(TestBase
            .getConfigProperty(ConfigKeywords.LOGS_REMOVAL_BEFORE_RUN.toString()));

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

    /**
     * Deletes all the files (gifs, logs, reports & screenshots) from the custom output directories
     */
    public static void deleteSnapsGifsReportFiles() {
        try {
            if (deleteSnapsGifsReports) {
                FileUtils.cleanDirectory(new File(SCREENSHOTS_DIRECTORY));
                FileUtils.cleanDirectory(new File(REPORTS_DIRECTORY));
                TestBase.getLogger().info("Cleared all the existing screenshots, GIFs & reports from its respective directories");
            } else {
                TestBase.getLogger().info("Did not clear all the existing screenshots, GIFs & reports as per the configuration");
            }
        } catch (Exception e) {
            TestBase.getLogger().info("");
        }
    }

    public static void deleteLogFiles() {
        try {
            if (deleteLogs) {
                File folder = new File(LOGS_DIRECTORY);
                File[] allFiles = folder.listFiles();
                for (int i = 0; i < (allFiles != null ? allFiles.length : 0); i++) {
                    String currentLogFile = System.getProperty(ConfigKeywords.LOG_CURRENT_DATE_TIME.toString());
                    String eachFileName = allFiles[i].getName();
                    if (!eachFileName.contains(currentLogFile)) {
                        Files.delete(allFiles[i].toPath());
                    }
                }
                TestBase.getLogger().info("Cleared the existing execution logs");
            } else {
                TestBase.getLogger().info("Did not clear all the existing logs as per the configuration");
            }
        } catch (Exception e) {
            TestBase.getLogger().error("Unable to delete existing log files ", e);
        }
    }

    public static void createDirectory(String directoryPath) {
        new File(directoryPath).mkdirs();
    }
}
