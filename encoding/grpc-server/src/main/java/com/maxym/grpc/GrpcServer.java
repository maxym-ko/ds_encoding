package com.maxym.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                                     .addService(new MessageServiceImpl())
                                     .build();

        server.start();
        System.out.println("Server started, listening on " + 8080);

        server.awaitTermination();
    }

    static class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {

        @Override
        public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
            String message = request.getMessage();
            String responseMessage = "Java gRPC Server received: " + message;

            MessageResponse response = MessageResponse.newBuilder()
                                                      .setResponse(responseMessage)
                                                      .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
