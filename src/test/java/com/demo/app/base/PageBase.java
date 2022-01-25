package com.demo.app.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PageBase {

    private static final String ERROR_MSG = "ERROR MESSAGE ";
    private static final String CLICKED_ON = "Clicked on ";
    private static final String UNABLE_TO_CLICK = "Unable to click on ";
    private static final String TEXT_OF = "Text of '";
    private static final String UNABLE_TO_GET_TXT = "Unable to getText of ";
    private static final String TYPED = "Typed '";
    private static final String UNABLE_TO_TYPE = "Unable to type ";

    public void clickOn(WebElement element, String refKey) {
        try {
            element.click();
            TestBase.getReport().stepPass(CLICKED_ON + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(UNABLE_TO_CLICK + "\n\n" + ERROR_MSG, e);
        }
    }

    public String getText(String element, String refKey) {
        try {
            String text = findElement(element).getText().trim();
            TestBase.getReport().stepInfo("Text of " + refKey + " :: " + text);
            return text;
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(UNABLE_TO_GET_TXT + "\n\n" + ERROR_MSG, e);
            return null;
        }
    }

    public String getText(WebElement element, String refKey) {
        String text = null;
        try {
            text = element.getText().trim();
            TestBase.getReport().stepInfo(TEXT_OF + refKey + "' :: " + text);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(UNABLE_TO_GET_TXT + "\n\n" + ERROR_MSG, e);
        }
        return text;
    }

    public void setText(WebElement element, String value, String refKey) {
        try {
            element.sendKeys(value);
            TestBase.getReport().stepPass(TYPED + value + "' in " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack(UNABLE_TO_TYPE + value + " in " + refKey + "\n\n" + ERROR_MSG, e);
        }
    }

    public void clearAndSetText(WebElement element, String value, String refKey) {
        try {
            element.clear();
            element.sendKeys(value);
            TestBase.getReport().stepPass("Cleared & typed '" + value + "' in " + refKey);
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to clear & type " + value + " in " + refKey + "\n\n" + ERROR_MSG, e);
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
}