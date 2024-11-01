package org.dlms.interceptors;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import static org.dlms.utils.DlmsLogger.logInfo;

public class HeaderInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        logInfo("HeaderInterceptor intercepted " + metadata.toString());
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
