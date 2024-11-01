package demo_client;

import io.grpc.stub.StreamObserver;
import org.dlms.services.TrackEventResponse;

public class TrackEventObserver implements StreamObserver<TrackEventResponse> {
    @Override
    public void onNext(TrackEventResponse value) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onCompleted() {

    }
}
