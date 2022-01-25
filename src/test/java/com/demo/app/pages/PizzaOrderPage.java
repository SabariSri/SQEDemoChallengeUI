package com.demo.app.pages;

import com.demo.app.base.PageBase;
import com.demo.app.base.TestBase;
import com.demo.app.enums.ColumnNames;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class PizzaOrderPage {

    @FindBy(id = "pizza1Pizza")
    WebElement selectPizza1DropDown;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings1']")
    WebElement selectToppings1DropDown;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings2']")
    WebElement selectToppings2DropDown;

    @FindBy(id = "pizza1Qty")
    WebElement pizza1QtyField;

    @FindBy(id = "name")
    WebElement nameField;

    @FindBy(id = "email")
    WebElement emailField;

    @FindBy(id = "phone")
    WebElement phoneField;

    @FindBy(id = "ccpayment")
    WebElement creditCardRadioButton;

    @FindBy(id = "cashpayment")
    WebElement cashRadioButton;

    @FindBy(id = "placeOrder")
    WebElement placeOrderButton;

    @FindBy(xpath = "//div[@id='dialog']/p")
    WebElement dialogPopupText;

    // Dynamic locators as String
    String elePizza1Text = "//select[@id='pizza1Pizza']/option[@value='TBU']";

    PageBase pageBase = new PageBase();

    public void chooseMenu(Map<String, String> data) {
        pageBase.selectOptionByValue(selectPizza1DropDown, data.get(ColumnNames.PIZZA1.toString()),
                "Pizza1 Dropdown");
        pageBase.selectOptionByValue(selectToppings1DropDown, data.get(ColumnNames.TOPPINGS1.toString()),
                "Toppings1 Dropdown");
        pageBase.selectOptionByValue(selectToppings2DropDown, data.get(ColumnNames.TOPPINGS2.toString()),
                "Toppings2 Dropdown");
        pageBase.clearAndSetText(pizza1QtyField, data.get(ColumnNames.QUANTITY.toString()),
                "Quantity Field");
    }

    public void addPickupInfo(Map<String, String> data) {
        pageBase.setText(nameField, data.get(ColumnNames.NAME.toString()), "Name Field");
        pageBase.setText(emailField, data.get(ColumnNames.EMAIL.toString()), "Email Field");
        pageBase.setText(phoneField, data.get(ColumnNames.PHONE.toString()), "Phone Field");
    }

    public void selectPayment(String payment) {
        try {
            if (payment.equalsIgnoreCase("Credit")) {
                pageBase.clickOn(creditCardRadioButton, "Credit Card - Radio Button");
            } else if (payment.equalsIgnoreCase("Cash")) {
                pageBase.clickOn(cashRadioButton, "Cash on Delivery - Radio Button");
            } else {
                TestBase.getReport().stepFail("Invalid payment type: " + payment);
            }
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to select payment", e);
        }
    }

    public String getPizza1Text(String pizza1Option) {
        return pageBase.getText(elePizza1Text.replace("TBU", pizza1Option), "Selected Pizza1 Text");
    }

    public void placeOrder() {
        pageBase.clickOn(placeOrderButton, "Place Order Button");
    }

    public String getOrderPopupText() {
        return pageBase.getText(dialogPopupText, "Order Popup");
    }
}
