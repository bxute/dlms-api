package org.dlms.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import static org.dlms.utils.DlmsLogger.logError;
import static org.dlms.utils.DlmsLogger.logInfo;

public class ActionObserver implements StreamObserver<ActionEvent> {
    final StreamObserver<TrackEventResponse> responseStreamObserver;

    public ActionObserver(StreamObserver<TrackEventResponse> responseObserver) {
        this.responseStreamObserver = responseObserver;
    }

    @Override
    public void onNext(ActionEvent actionEvent) {
        logInfo("Received Action: " + actionEvent.getActionType());
        logInfo("Returning Success " + Status.OK);
        responseStreamObserver.onNext(TrackEventResponse
                .newBuilder()
                .setSuccess(true)
                .setMessage("Success " + actionEvent.getEventId())
                .build());
        try {
            //simulate delay
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        logError("Error in action observer: " + throwable);
        responseStreamObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        logInfo("Completed action observer");
        responseStreamObserver.onCompleted();
    }
}
