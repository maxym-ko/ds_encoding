package com.maxym.grpc;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
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

        private final static String QUEUE_NAME = "hello";

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

        @Override
        public void sendMessageToQueue(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
            String message = request.getMessage();
            sendToQueue(message);

            String responseMessage = "Message sent to queue by Java gRPC Server: " + message;
            MessageResponse response = MessageResponse.newBuilder()
                                                      .setResponse(responseMessage)
                                                      .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        private void sendToQueue(String message) {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
