package org.dlms.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DlmsLogger {
    private static final String LOGGER_NAME = "org.dlms.utils.Logger";
    private static final Logger logger = Logger.getLogger(LOGGER_NAME);

    public static void logInfo(String msg)  {
       logger.log(Level.INFO, msg);
    }

    public static void logError(String msg)  {
        java.util.logging.Logger.getLogger(LOGGER_NAME).log(Level.SEVERE, msg);
    }
}
