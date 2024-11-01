package org.dlms.services.livescores;

import io.grpc.stub.StreamObserver;
import org.dlms.services.ScoreRequest;
import org.dlms.services.ScoreResponse;

import static org.dlms.utils.DlmsLogger.logInfo;

public class LiveScoreRequestObserver {

    private final StreamObserver<ScoreResponse> responseObserver;

    public LiveScoreRequestObserver(StreamObserver<ScoreResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    public void handleRequest(ScoreRequest request) {
        try {
            // repeat 100 times
            for (int i = 0; i < 100; i++) {
                logInfo("Sending score: " + i);
                responseObserver.onNext(
                        ScoreResponse.newBuilder()
                                .setScoreDetail("Score " + i)
                                .setTeamA("Team A")
                                .setTeamB("Team B")
                                .setMatchId(request.getMatchId())
                                .build()
                );
                Thread.sleep(200);
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logInfo("LiveScoreRequestObserver: " + e);
            responseObserver.onError(e);
        }
    }
}
