import grpc
import message_pb2
import message_pb2_grpc


def run():
    with grpc.insecure_channel('localhost:8080') as channel:
        stub = message_pb2_grpc.MessageServiceStub(channel)

        request = message_pb2.MessageRequest(message='Hello gRPC from Python')
        response = stub.SendMessage(request)

        print(f'Response from server: [{response.response}]')


if __name__ == '__main__':
    run()
