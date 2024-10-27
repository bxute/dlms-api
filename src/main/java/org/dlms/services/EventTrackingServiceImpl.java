package org.dlms.services;

import io.grpc.stub.StreamObserver;

public class EventTrackingServiceImpl extends EventTrackingServiceGrpc.EventTrackingServiceImplBase {
    @Override
    public StreamObserver<ActionEvent> trackAction(StreamObserver<TrackEventResponse> responseObserver) {
        return new ActionObserver(responseObserver);
    }

    @Override
    public StreamObserver<ImpressionEvent> trackImpression(StreamObserver<TrackEventResponse> responseObserver) {
        return new ImpressionEventObserver(responseObserver);
    }
}
