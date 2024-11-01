package org.dlms.interceptors;

import io.grpc.*;

import static org.dlms.utils.DlmsLogger.logInterceptor;

public class HeaderInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        logInterceptor("HeaderInterceptor", metadata, serverCall);
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
