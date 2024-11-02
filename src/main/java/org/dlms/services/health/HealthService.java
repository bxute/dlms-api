package org.dlms.services.health;

import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthGrpc;
import io.grpc.stub.StreamObserver;

public class HealthService extends HealthGrpc.HealthImplBase {
    private HealthCheckResponse.ServingStatus currentStatus = HealthCheckResponse.ServingStatus.SERVING;

    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        HealthCheckResponse.Builder builder = HealthCheckResponse.newBuilder().setStatus(currentStatus);
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void watch(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        responseObserver.onNext(HealthCheckResponse.newBuilder().setStatus(currentStatus).build());
    }
}
