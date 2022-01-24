package com.demo.app.enums;

public enum ColumnNames {

        SERIAL_NO("SerialNo"), TEST_NAME("TestName"), CATEGORY("Category"), TYPE("Type"),
        PIZZA1("Pizza1"), TOPPINGS1("Toppings1"), TOPPINGS2("Toppings2"), QUANTITY("Quantity"),
        NAME("Name"), EMAIL("Email"),
        PHONE("Phone"), PAYMENT("Payment");

        private final String name;

        ColumnNames(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }



