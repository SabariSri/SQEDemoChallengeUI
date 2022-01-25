package com.demo.app.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyInitiator {

    private PropertyInitiator() {
    }

    public static Properties readProperties(String propertyFilePath) {
        Logger print = Logger.getLogger(Properties.class);
        Properties property = new Properties();
        try (FileInputStream fileStream = new FileInputStream(propertyFilePath)) {
            property.load(fileStream);
            return property;
        } catch (Exception e) {
            print.log(Priority.ERROR, "Invalid path or file name: " + propertyFilePath, e);
            return new Properties();
        }
    }
}
