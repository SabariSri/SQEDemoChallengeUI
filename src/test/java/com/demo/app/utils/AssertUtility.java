package com.demo.app.utils;

import com.demo.app.base.TestBase;
import org.testng.Assert;

public class AssertUtility {

    private AssertUtility() {
        throw new IllegalStateException("Assert utility class");
    }

    public static void assertEquals(String actual, String expected, String context) {
        try {
            if (actual == null && expected == null)
                throw new CustomException("Both actual and expected are null");
            else if (actual == null)
                throw new CustomException("Actual value is null and expected value is " + expected);
            else if (expected == null)
                throw new CustomException("Expected value is null and actual value is " + actual);
            else if (actual.equals(expected)) {
                TestBase.getReport().stepPass("Successfully validated the " + context + ": " + expected);
            } else
                throw new CustomException("Actual '" + actual + "' & Expected '" + expected + "' values are different");
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Actual '" + context + "' is '" + actual +
                    "' & Expected '" + context + "' is ' " + expected + "'", e);
            Assert.fail(e.getMessage());
        }
    }

    public static void assertContains(String completeString, String subString, String context) {
        try {
            if (completeString == null && subString == null)
                throw new CustomException("Both complete string and sub string are null");
            else if (completeString == null)
                throw new CustomException("Complete string is null and sub string is " + subString);
            else if (subString == null)
                throw new CustomException("Sub string is null and Complete string is " + completeString);
            else if (completeString.contains(subString)) {
                TestBase.getReport().stepPass(
                        "Successfully validated the " + context + " '" + completeString + "' contains '" + subString + "'");
            } else
                throw new CustomException("'" + completeString + "' doesn't contain '" + subString + "'");
        } catch (Exception e) {
            TestBase.getReport().stepFailErrorStack("Complete string is '" + completeString
                    + "' and sub string is '" + subString + "'", e);
            Assert.fail(e.getMessage());
        }
    }

}
