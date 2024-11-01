package demo_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.dlms.server.TrackingServer;
import org.dlms.services.*;

import static org.dlms.server.TrackingServer.PORT;

public class DemoTrackingClient {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", PORT)
                .usePlaintext().build();

        // create sub
        EventTrackingServiceGrpc.EventTrackingServiceStub stub = EventTrackingServiceGrpc.newStub(channel);
        StreamObserver<ActionEvent> actionStreamObserver = stub.trackAction(new TrackEventObserver());
        StreamObserver<ImpressionEvent> impressionEventStreamObserver = stub.trackImpression(new TrackEventObserver());

        //mock 10 events send
        for (int i = 0; i < 10; i++) {
            System.out.println("Sending Action Event " + i);
            actionStreamObserver.onNext(ActionEvent
                    .newBuilder()
                    .setEventId("EventId " + i)
                    .setActionType("Click")
                    .setUserId("UserId " + i)
                    .setTimestamp(System.currentTimeMillis())
                    .putMetadata("id", "id" + i)
                    .build()
            );
            impressionEventStreamObserver.onNext(ImpressionEvent
                    .newBuilder()
                    .setImpressionId("ImpressionId " + i)
                    .setItemType("Posts")
                    .setItemId(i * 100L)
                    .setTimestamp(System.currentTimeMillis())
                    .setUserId("UserId " + i)
                    .putMetadata("id", "id" + i)
                    .build()
            );
            Thread.sleep(1500);
        }

        //close client
        actionStreamObserver.onCompleted();
        impressionEventStreamObserver.onCompleted();
        channel.shutdownNow();
    }

    private static void startServer() {
        new Thread(() -> {
            String[] arg = new String[0];
            try {
                TrackingServer.main(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
