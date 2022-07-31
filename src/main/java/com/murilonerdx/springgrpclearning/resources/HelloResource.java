package com.murilonerdx.springgrpclearning.resources;


import com.murilonerdx.springgrpclearning.HelloReq;
import com.murilonerdx.springgrpclearning.HelloRes;
import com.murilonerdx.springgrpclearning.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloResource extends HelloServiceGrpc.HelloServiceImplBase{

    @Override
    public void hello(HelloReq request, StreamObserver<HelloRes> responseObserver) {
        var response = HelloRes.newBuilder()
                .setMessage(request.getMessage())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
