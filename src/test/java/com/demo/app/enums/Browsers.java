package com.demo.app.enums;

public enum Browsers {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge"),
    IE("IE");

    private final String name;

    Browsers(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
