package com.demo.app.enums;

public enum ReportSteps {
    STEP_PASS("StepPass"),
    STEP_FAIL("StepFail"),
    STEP_FAIL_ERROR("StepFailError"),
    REPORT_STEP_DATA("Test data ::"),
    STEP_WARNING("StepWarning");

    private final String name;

    ReportSteps(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
