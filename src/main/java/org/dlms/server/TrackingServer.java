package org.dlms.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.dlms.interceptors.AuthInterceptor;
import org.dlms.interceptors.HeaderInterceptor;
import org.dlms.services.EventTrackingServiceImpl;
import org.dlms.services.livescores.LiveScoreServiceImpl;

public class TrackingServer {
    public static final int PORT = 50052;
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(PORT)
                .addService(new EventTrackingServiceImpl())
                .addService(new LiveScoreServiceImpl())
                .intercept(new HeaderInterceptor())
                .intercept(new AuthInterceptor())
                .build();

        System.out.println("Server starting on port " + PORT + "..");
        server.start();
        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server");
            server.shutdown();
        }));
    }
}
