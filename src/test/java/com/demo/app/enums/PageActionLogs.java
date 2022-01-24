package com.demo.app.enums;

public enum PageActionLogs {
    ERROR_MSG("ERROR MESSAGE "),
    CLICKED_ON("Clicked on "),
    UNABLE_TO_CLICK("Unable to click on "),
    TEXT_OF("Text of '"),
    UNABLE_TO_GET_TXT("Unable to getText of "),
    TYPED("Typed '"),
    UNABLE_TO_TYPE("Unable to type "),
    CLICKED_ENTER(" and clicked Enter");

    private final String name;

    PageActionLogs(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
