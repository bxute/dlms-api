package org.dlms.services.livescores;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.dlms.services.ScoreRequest;
import org.dlms.services.ScoreResponse;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.dlms.utils.DlmsLogger.logInfo;

public class LiveScoreRequestObserver {

    private final ServerCallStreamObserver<ScoreResponse> responseObserver;

    public LiveScoreRequestObserver(StreamObserver<ScoreResponse> responseObserver) {
        this.responseObserver = (ServerCallStreamObserver<ScoreResponse>) responseObserver;
    }

    public void handleRequest(ScoreRequest request) {
        AtomicBoolean canSend = new AtomicBoolean(false);
        responseObserver.setOnReadyHandler(new Runnable() {
            @Override
            public void run() {
                if (responseObserver.isReady()) {
                    canSend.set(true);
                    sendScores(request, responseObserver, canSend);
                }
            }
        });
    }

    private void sendScores(ScoreRequest request, ServerCallStreamObserver<ScoreResponse> responseObserver, AtomicBoolean canSend) {
        // repeat 100 times
        for (int i = 0; i < 100; i++) {
            if (canSend.get() && responseObserver.isReady()) {
                logInfo("Sending score: " + i);
                responseObserver.onNext(
                        ScoreResponse.newBuilder()
                                .setScoreDetail("Score " + i)
                                .setTeamA("Team A")
                                .setTeamB("Team B")
                                .setMatchId(request.getMatchId())
                                .build()
                );
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                logInfo("Client not ready!");
                canSend.set(false);
                break;
            }
        }
        responseObserver.onCompleted();
    }
}
