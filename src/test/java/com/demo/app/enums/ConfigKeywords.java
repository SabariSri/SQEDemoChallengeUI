package com.demo.app.enums;

public enum ConfigKeywords {
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

        ConfigKeywords(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
