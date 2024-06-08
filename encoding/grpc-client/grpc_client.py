import argparse
import ast

import grpc
import message_pb2
import message_pb2_grpc


def run():
    with grpc.insecure_channel('localhost:8080') as channel:
        stub = message_pb2_grpc.MessageServiceStub(channel)

        request = message_pb2.MessageRequest(message='Hello gRPC from Python')
        response = stub.SendMessage(request)

        print(f'Response from server: [{response.response}]')


def run_with_queue():
    with grpc.insecure_channel('localhost:8080') as channel:
        stub = message_pb2_grpc.MessageServiceStub(channel)

        request = message_pb2.MessageRequest(message='Hello gRPC from Python')
        response = stub.SendMessageToQueue(request)

        print(f'Response from server: [{response.response}]')


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--queue', metavar='path')
    args = parser.parse_args()

    with_queue = False if not args.queue else ast.literal_eval(args.queue)

    if with_queue:
        run_with_queue()
    else:
        run()
