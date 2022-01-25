package com.demo.app.tests;

import com.demo.app.base.TestBase;
import com.demo.app.datadrivers.TestDataProvider;
import com.demo.app.enums.ColumnNames;
import com.demo.app.utils.AssertUtility;
import org.testng.annotations.Test;

import java.util.Map;

public class PizzaFormTest extends TestBase {

    @Test(dataProvider = "testData", dataProviderClass = TestDataProvider.class)
    public void testPlaceOrder(Map<String, String> data) {
        setTestCaseNameAndData(data);
        pizzaOrderPage.chooseMenu(data);
        String expectedTotalCost = getExpectedTotalCost(data);
        pizzaOrderPage.addPickupInfo(data);
        pizzaOrderPage.selectPayment(data.get(ColumnNames.PAYMENT.toString()));
        pizzaOrderPage.placeOrder();
        String orderPopup = pizzaOrderPage.getOrderPopupText();
        AssertUtility.assertContains(orderPopup,
                data.get(ColumnNames.EXPECTED_ORDER_POPUP_MSG.toString()), "Order Popup");
        validateTotalCostFromSuccessOrderPopup(orderPopup, expectedTotalCost);
    }

    private void validateTotalCostFromSuccessOrderPopup(String orderPopup, String expectedTotalCost) {
        if (orderPopup.contains(getAppMsgProperty("successOrderPopupMsg"))) {
            AssertUtility.assertEquals(orderPopup.split(":")[1].split(" ")[1],
                    expectedTotalCost, "Total cost from order popup");
        }
    }

    private String getExpectedTotalCost(Map<String, String> data) {
        String pizza1Text = pizzaOrderPage.getPizza1Text(data.get(ColumnNames.PIZZA1.toString()));
        return String.valueOf((Double.parseDouble(pizza1Text.split("\\$")[1].trim()) *
                Integer.parseInt(data.get(ColumnNames.QUANTITY.toString())))).replace("-", "");
    }
}
