package org.dlms.services;

import io.grpc.stub.StreamObserver;

public class EventTrackingServiceImpl extends EventTrackingServiceGrpc.EventTrackingServiceImplBase {
    @Override
    public StreamObserver<ActionEvent> streamAction(StreamObserver<TrackEventResponse> responseObserver) {
        return new ActionObserver(responseObserver);
    }

    @Override
    public StreamObserver<ImpressionEvent> streamImpression(StreamObserver<TrackEventResponse> responseObserver) {
        return new ImpressionEventObserver(responseObserver);
    }
}
