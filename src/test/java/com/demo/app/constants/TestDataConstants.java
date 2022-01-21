package com.demo.app.constants;

public interface TestDataConstants {

    enum ColumnName {
        SERIAL_NO("SerialNo"), TEST_NAME("TestName"), CATEGORY("Category"), TYPE("Type"),
        PIZZA1("Pizza1"), TOPPINGS1("Toppings1"), TOPPINGS2("Toppings2"), QUANTITY("Quantity"),
        NAME("Name"), EMAIL("Email"),
        PHONE("Phone"), PAYMENT("Payment");

        private final String name;

        ColumnName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    enum OtherConstants {
        REPORT_STEP_DATA("Test data ::");

        private final String name;

        OtherConstants(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }


}
