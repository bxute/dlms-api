package org.dlms.services.livescores;

import io.grpc.stub.StreamObserver;
import org.dlms.services.LiveScoreServiceGrpc;
import org.dlms.services.ScoreRequest;
import org.dlms.services.ScoreResponse;

public class LiveScoreServiceImpl extends LiveScoreServiceGrpc.LiveScoreServiceImplBase {
    @Override
    public void getLiveScores(ScoreRequest request, StreamObserver<ScoreResponse> responseObserver) {
        new LiveScoreRequestObserver(responseObserver).handleRequest(request);
    }
}
