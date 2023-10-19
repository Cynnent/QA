package com.cynnent.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jUtils {
    private static final Logger log = LogManager.getLogger(Log4jUtils.class);

    public static void setLogLevelToInfo() {
        org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.INFO);
    }

    public static void setLogLevelToDebug() {
        org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.DEBUG);
    }

    public static void setLogLevelToError() {
        org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(), org.apache.logging.log4j.Level.ERROR);
    }
}

