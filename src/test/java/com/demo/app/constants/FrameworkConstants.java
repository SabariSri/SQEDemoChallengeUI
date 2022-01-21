package com.demo.app.constants;

public interface FrameworkConstants {

    enum Browsers {
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

    enum ConfigKeyWords {
        CONFIG_PROP_PATH("//src/test//resources//properties//config.properties"),
        BROWSER("browser"),
        URL("url"),
        REPORT_OUTPUT("report.output"),
        REPORT_CONFIG("report.config"),
        LOG_PROP("log.properties"),
        APP_MSG_PROP("app.msg.properties"),
        DATA_CSV("data.csv"),
        SCREENSHOT_OUTPUT("screenshot.output"),
        LOG_OUTPUT("log.output"),
        LOG_CURRENT_DATE_TIME("log.current.date.time"),
        SCREENSHOT_EXTENSION("screenshot.type.ext"),
        LOGS_REMOVAL_BEFORE_RUN("logs.remove.before.run"),
        SNAPS_REPORTS_REMOVAL_BEFORE_RUN("screenshots.gifs.reports.remove.before.run"),
        SCREENSHOT_DEL_POST_GIF("screenshots.remove.post.gif"),
        SCREENSHOT_CAPTURE_ALL_STEPS("screenshot.capture.all.steps"),
        SCREENSHOT_GAP_MS("screenshot.gap.ms"),
        GIF_RECAP_CREATE("gif.recap.create"),
        GIF_SNAPS_INTERVAL_SECS("gif.snap.interval.seconds"),
        GIF_LOOP("gif.loop");

        private final String name;

        ConfigKeyWords(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    enum ReportStepStatus {
        STEP_PASS("StepPass"),
        STEP_FAIL("StepFail"),
        STEP_FAIL_ERROR("StepFailError"),
        STEP_WARNING("StepWarning");

        private final String name;

        ReportStepStatus(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    enum PageActionLogs {
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
}
