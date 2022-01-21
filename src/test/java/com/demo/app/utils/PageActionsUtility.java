package com.demo.app.utils;

import com.demo.app.base.TestBase;
import com.demo.app.constants.FrameworkConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PageActionsUtility implements FrameworkConstants {
    
    public void clickOn(WebElement element, String refKey) {
        try {
            element.click();
            TestBase.getReport().stepPass(PageActionLogs.CLICKED_ON + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_CLICK + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void clickOn(String element, String refKey) {
        try {
            findElement(element).click();
            TestBase.getReport().stepPass(PageActionLogs.CLICKED_ON + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_CLICK + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public String getText(String element, String refKey) {
        try {
            String text = findElement(element).getText().trim();
            TestBase.getReport().stepInfo("Text of " + refKey + " :: " + text);
            return text;
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_GET_TXT + "\n\n" + PageActionLogs.ERROR_MSG, e);
            return null;
        }
    }

    public String getText(WebElement element, String refKey) {
        String text = null;
        try {
            text = element.getText().trim();
            TestBase.getReport().stepInfo(PageActionLogs.TEXT_OF + refKey + "' :: " + text);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_GET_TXT + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
        return text;
    }

    public void setText(String element, String value, String refKey) {
        try {
            findElement(element).sendKeys(value);
            TestBase.getReport().stepPass(PageActionLogs.TYPED + value + "' in " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_TYPE + value + " in " + refKey + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void setText(WebElement element, String value, String refKey) {
        try {
            element.sendKeys(value);
            TestBase.getReport().stepPass(PageActionLogs.TYPED + value + "' in " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_TYPE + value + " in " + refKey + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void clearAndSetText(WebElement element, String value, String refKey) {
        try {
            element.clear();
            element.sendKeys(value);
            TestBase.getReport().stepPass("Cleared & typed '" + value + "' in " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to clear & type " + value + " in " + refKey + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void setTextAndClickEnter(WebElement element, String value, String refKey) {
        try {
            element.sendKeys(value);
            element.sendKeys(Keys.ENTER);
            TestBase.getReport().stepPass(PageActionLogs.TYPED + value + "' in " + refKey + PageActionLogs.CLICKED_ENTER);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_TYPE + value + " in " + refKey + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void setTextAndClickEnter(String element, String value, String refKey) {
        try {
            findElement(element).sendKeys(value);
            findElement(element).sendKeys(Keys.ENTER);
            TestBase.getReport().stepPass(PageActionLogs.TYPED + value + "' in " + refKey + PageActionLogs.CLICKED_ENTER);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(PageActionLogs.UNABLE_TO_TYPE + value + " in " + refKey + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void selectOptionByValue(WebElement element, String value, String refKey) {
        try {
            Select select = new Select(element);
            select.selectByValue(value);
            TestBase.getReport().stepPass("Selected '" + value + "' from " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to select " + value + " from " + refKey, e);
        }
    }

    public WebElement findElement(String element) {
            return TestBase.getDriver().findElement(By.xpath(element));
    }

    public void switch_to_iframe(String iframe) {
        try {
            TestBase.getDriver().switchTo().frame(iframe);
            TestBase.getReport().stepPass("Switched to iFrame " + iframe);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to switch to iFrame " + iframe + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }

    public void scroll_to_element(WebElement element, String refKey) {
        try {
            ((JavascriptExecutor) TestBase.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
            TestBase.getReport().stepPass("Scrolled to " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to scroll to " + refKey + "\n\n" + PageActionLogs.ERROR_MSG, e);
        }
    }
}
