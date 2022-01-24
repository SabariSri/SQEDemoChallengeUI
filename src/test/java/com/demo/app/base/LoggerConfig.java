package com.demo.app.base;

import com.demo.app.enums.ConfigKeywords;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerConfig {

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh.mm.ss");
        System.setProperty(ConfigKeywords.LOG_CURRENT_DATE_TIME.toString(), dateFormat.format(new Date()));
    }

    private LoggerConfig() {
        throw new IllegalStateException("Logger config class");
    }

    public static Logger loadLogProperties() {
        Logger print = Logger.getLogger(LoggerConfig.class);
        Logger logger = null;
        try {
            String log4jConfigFile = System.getProperty("user.dir")
                    + TestBase.getConfigProperty(ConfigKeywords.LOG_PROP.toString())
                    .replaceAll("//", File.separator);
            logger = Logger.getLogger(LoggerConfig.class);
            PropertyConfigurator.configure(log4jConfigFile);
        } catch (Exception e) {
            print.log(Priority.ERROR, "Unable to load log4j properties", e);
            e.printStackTrace();
        }
        return logger;
    }
}
