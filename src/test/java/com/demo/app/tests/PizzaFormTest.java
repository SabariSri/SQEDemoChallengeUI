package com.demo.app.tests;

import com.demo.app.base.TestBase;
import com.demo.app.enums.ColumnNames;
import com.demo.app.enums.ReportSteps;
import com.demo.app.datadrivers.TestDataProvider;
import com.demo.app.utils.AssertUtility;
import org.testng.annotations.Test;

import java.util.Map;

public class PizzaFormTest extends TestBase {

    @Test(dataProvider = "testData", dataProviderClass = TestDataProvider.class)
    public void TC01_place_order(Map<String, String> data) {
        getReport().stepInfo(ReportSteps.REPORT_STEP_DATA + data.toString());
        pizzaOrderPage.chooseMenu(data);
        String expectedTotalCost = getExpectedTotalCost(data);
        pizzaOrderPage.addPickupInfo(data);
        pizzaOrderPage.selectPayment(data.get(ColumnNames.PAYMENT.toString()));
        pizzaOrderPage.placeOrder();
        AssertUtility.assertContains(pizzaOrderPage.getOrderPopupText(),
                getAppMsgProperty("validOrderPopupMsg"), "Order Popup");
        AssertUtility.assertEquals(pizzaOrderPage.getOrderPopupText().split(":")[1].split(" ")[1],
                expectedTotalCost, "Total cost from order popup");
    }

    @Test(dataProvider = "testData", dataProviderClass = TestDataProvider.class)
    public void TC02_place_order_negative_qty(Map<String, String> data) {
        getReport().stepInfo(ReportSteps.REPORT_STEP_DATA + data.toString());
        pizzaOrderPage.chooseMenu(data);
        String expectedTotalCost = getExpectedTotalCost(data);
        pizzaOrderPage.addPickupInfo(data);
        pizzaOrderPage.selectPayment(data.get(ColumnNames.PAYMENT.toString()));
        pizzaOrderPage.placeOrder();
        AssertUtility.assertContains(pizzaOrderPage.getOrderPopupText(),
                getAppMsgProperty("validOrderPopupMsg"), "Order Popup");
        AssertUtility.assertEquals(pizzaOrderPage.getOrderPopupText().split(":")[1].split(" ")[1],
                expectedTotalCost.substring(1), "Total cost from order popup");
    }

    @Test(dataProvider = "testData", dataProviderClass = TestDataProvider.class)
    public void TC03_place_order_without_name(Map<String, String> data) {
        getReport().stepInfo(ReportSteps.REPORT_STEP_DATA + data.toString());
        pizzaOrderPage.chooseMenu(data);
        pizzaOrderPage.addPickupInfo(data);
        pizzaOrderPage.selectPayment(data.get(ColumnNames.PAYMENT.toString()));
        pizzaOrderPage.placeOrder();
        AssertUtility.assertEquals(pizzaOrderPage.getOrderPopupText(),
                getAppMsgProperty("invalidOrderPopupMissingNameMsg"), "Invalid Order - Without Name");
    }

    private String getExpectedTotalCost(Map<String, String> data) {
        String pizza1Text = pizzaOrderPage.getPizza1Text(data.get(ColumnNames.PIZZA1.toString()));
        return String.valueOf((Double.parseDouble(pizza1Text.split("\\$")[1].trim()) *
                Integer.parseInt(data.get(ColumnNames.QUANTITY.toString()))));
    }
}
