package com.demo.app.pages;

import com.demo.app.utils.PageActionsUtility;
import com.demo.app.base.TestBase;
import com.demo.app.constants.TestDataConstants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class PizzaOrderPage extends PageActionsUtility {

    @FindBy(id = "pizza1Pizza")
    WebElement selectPizza1DropDown;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings1']")
    WebElement selectToppings1DropDown;

    @FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings2']")
    WebElement selectToppings2DropDown;

    @FindBy(id = "pizza1Qty")
    WebElement pizza1QtyField;

    @FindBy(id = "pizza1Cost")
    WebElement pizza1TotalCostField;

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

    @FindBy(id = "reset")
    WebElement resetButton;

    @FindBy(id = "dialog")
    WebElement dialogPopup;

    @FindBy(xpath = "//div[@id='dialog']/p")
    WebElement dialogPopupText;

    // Dynamic locators as String
    String elePizza1Text = "//select[@id='pizza1Pizza']/option[@value='TBU']";

    public void chooseMenu(Map<String, String> data) {
        selectOptionByValue(selectPizza1DropDown, data.get(TestDataConstants.ColumnName.PIZZA1.toString()),
                "Pizza1 Dropdown");
        selectOptionByValue(selectToppings1DropDown, data.get(TestDataConstants.ColumnName.TOPPINGS1.toString()),
                "Toppings1 Dropdown");
        selectOptionByValue(selectToppings2DropDown, data.get(TestDataConstants.ColumnName.TOPPINGS2.toString()),
                "Toppings2 Dropdown");
        clearAndSetText(pizza1QtyField, data.get(TestDataConstants.ColumnName.QUANTITY.toString()),
                "Quantity Field");
    }

    public void addPickupInfo(Map<String, String> data) {
        setText(nameField, data.get(TestDataConstants.ColumnName.NAME.toString()), "Name Field");
        setText(emailField, data.get(TestDataConstants.ColumnName.EMAIL.toString()), "Email Field");
        setText(phoneField, data.get(TestDataConstants.ColumnName.PHONE.toString()), "Phone Field");
    }

    public void selectPayment(String payment) {
        try {
            if (payment.equalsIgnoreCase("Credit")) {
                clickOn(creditCardRadioButton, "Credit Card - Radio Button");
            } else if (payment.equalsIgnoreCase("Cash")) {
                clickOn(cashRadioButton, "Cash on Delivery - Radio Button");
            } else {
                TestBase.getReport().stepFail("Invalid payment type: " + payment);
            }
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Unable to select payment", e);
        }
    }

    public String getTotalCost(){
        return getText(pizza1TotalCostField, "Total Cost Field");
    }

    public String getPizza1Text(String pizza1Option){
        return getText(elePizza1Text.replace("TBU", pizza1Option), "Selected Pizza1 Text");
    }

    public void placeOrder() {
        clickOn(placeOrderButton, "Place Order Button");
    }

    public void resetOrder() {
        clickOn(resetButton, "Reset Order Button");
    }

    public String getOrderPopupText(){
        return getText(dialogPopupText, "Order Popup");
    }
}
