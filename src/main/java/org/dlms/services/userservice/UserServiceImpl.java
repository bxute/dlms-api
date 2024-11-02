package org.dlms.services.userservice;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.dlms.services.UserRequest;
import org.dlms.services.UserResponse;
import org.dlms.services.UserServiceGrpc;

import static org.dlms.utils.DlmsLogger.logError;
import static org.dlms.utils.DlmsLogger.logInfo;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        String userId = request.getUserId();
        logInfo("Received request for user " + userId);
        try {
            responseObserver
                    .onNext(UserResponse.newBuilder()
                            .setUserId(request.getUserId())
                            .setEmail("email_" + userId + "@email.com")
                            .setName("Name_" + userId)
                            .build()
                    );
            responseObserver.onCompleted();
        } catch (Exception e) {
            logError(e.getLocalizedMessage());
            responseObserver.onError(e);
        }
    }
}
