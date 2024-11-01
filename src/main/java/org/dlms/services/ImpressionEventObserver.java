package org.dlms.services;

import io.grpc.stub.StreamObserver;

import static org.dlms.utils.DlmsLogger.logError;
import static org.dlms.utils.DlmsLogger.logInfo;

public class ImpressionEventObserver implements StreamObserver<ImpressionEvent> {
    final StreamObserver<TrackEventResponse> responseStreamObserver;

    public ImpressionEventObserver(StreamObserver<TrackEventResponse> responseObserver) {
        this.responseStreamObserver = responseObserver;
    }

    @Override
    public void onNext(ImpressionEvent impressionEvent) {
        logInfo("Received impression for: " + impressionEvent.getItemType());
        responseStreamObserver.onNext(TrackEventResponse
                .newBuilder()
                .setMessage("Ok")
                .setSuccess(true)
                .build()
        );
    }

    @Override
    public void onError(Throwable throwable) {
        logError("Error in ImpressionEventObserver: " + throwable);
        responseStreamObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        logInfo("ImpressionEventObserver completed");
        responseStreamObserver.onCompleted();
    }
}
