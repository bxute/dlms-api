package org.dlms.utils;

import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DlmsLogger {
    private static final String LOGGER_NAME = "org.dlms.utils.Logger";
    private static final Logger logger = Logger.getLogger(LOGGER_NAME);

    public static void logInfo(String msg)  {
       logger.log(Level.INFO, msg);
    }

    public static void logError(String msg)  {
        logger.log(Level.SEVERE, msg);
    }

    public static void logInterceptor(String interceptor, Metadata metadata, ServerCall serverCall) {
        String metadataString = metadata.toString();
        String methodName = serverCall.getMethodDescriptor().getFullMethodName();
        MethodDescriptor.MethodType methodType = serverCall.getMethodDescriptor().getType();
        logInfo(String.format("""
                ======
                Interceptor: %s
                metadata: %s,
                method: %s,
                type: %s
                ======
                """, interceptor, metadataString, methodName, methodType.toString()));
    }
}
