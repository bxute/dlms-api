package org.dlms.utils;

import java.util.logging.Level;

public class Logger {
    private static final String LOGGER_NAME = "org.dlms.utils.Logger";

    public static void logInfo(String msg)  {
        java.util.logging.Logger.getLogger(LOGGER_NAME).log(Level.INFO, msg);
    }

    public static void logError(String msg)  {
        java.util.logging.Logger.getLogger(LOGGER_NAME).log(Level.SEVERE, msg);
    }
}
