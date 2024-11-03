package org.dlms.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import org.dlms.interceptors.AuthInterceptor;
import org.dlms.interceptors.HeaderInterceptor;
import org.dlms.services.EventTrackingServiceImpl;
import org.dlms.services.health.HealthService;
import org.dlms.services.livescores.LiveScoreServiceImpl;
import org.dlms.services.userservice.UserServiceImpl;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class GrpcServer {
    public static final int PORT = 50052;
    static boolean sslEnabled = true;

    static List<ServerInterceptor> interceptors = Arrays.asList(
            new AuthInterceptor(),
            new HeaderInterceptor()
    );

    static List<BindableService> services = Arrays.asList(
            new EventTrackingServiceImpl(),
            new LiveScoreServiceImpl(),
            new UserServiceImpl(),
            new HealthService()
    );


    public static void main(String[] args) throws Exception {
        Server server;
        if (sslEnabled) {
            server = buildSecureServer();
            System.out.println("[Secure] Server starting on port " + PORT + "..");
        } else {
            server = buildUnSecureServer();
            System.out.println("[UnSecure] Server starting on port " + PORT + "..");
        }
        server.start();
        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server");
            server.shutdown();
        }));
    }

    private static Server buildUnSecureServer() {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(PORT)
                .intercept(new HeaderInterceptor())
                .executor(Executors.newFixedThreadPool(5));

        for (ServerInterceptor interceptor : interceptors) {
            serverBuilder.intercept(interceptor);
        }
        for (BindableService service : services) {
            serverBuilder.addService(service);
        }
        return serverBuilder.build();
    }

    private static Server buildSecureServer() throws Exception {
        InputStream caCertInputStream = GrpcServer.class.getClassLoader().getResourceAsStream("ca.crt");
        InputStream serverCertInputStream = GrpcServer.class.getClassLoader().getResourceAsStream("server.crt");
        InputStream serverKeyInputStream = GrpcServer.class.getClassLoader().getResourceAsStream("server.key");

        ServerBuilder<NettyServerBuilder> serverBuilder = NettyServerBuilder.forPort(PORT)
                .sslContext(GrpcSslContexts
                        .forServer(serverCertInputStream, serverKeyInputStream)
                        .trustManager(caCertInputStream)
                        .build()
                );

        for (ServerInterceptor interceptor : interceptors) {
            serverBuilder.intercept(interceptor);
        }
        for (BindableService service : services) {
            serverBuilder.addService(service);
        }
        return serverBuilder.build();
    }
}
