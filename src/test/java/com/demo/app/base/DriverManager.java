package com.demo.app.base;

import com.demo.app.enums.Browsers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static org.testng.Assert.fail;

public class DriverManager {

    private DriverManager() {
        throw new IllegalStateException("Driver manager class");
    }

    /**
     * Instantiate the browser based on the type given in config file.
     */
    public static WebDriver initializeDriver(String browser) {
        WebDriver driver = null;
        try {
            if (browser.equalsIgnoreCase(Browsers.CHROME.toString())) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase(Browsers.FIREFOX.toString())) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browser.equalsIgnoreCase(Browsers.EDGE.toString())) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } else if (browser.equalsIgnoreCase(Browsers.IE.toString())) {
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            } else {
                fail("Unsupported browser :: " + browser);
            }
        } catch (Exception e) {
            TestBase.getLogger().debug("Check whether the local browser is updated to the latest version");
            TestBase.getLogger().error("Unable to instantiate driver object ", e);
        }
        return driver;
    }
}
