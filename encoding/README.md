## Run instructions

### 1. Start gRPC Server
```shell
cd grpc-server
mvn clean install
java -jar target/grpc-server-1.0-SNAPSHOT.jar 
```

### 2. Run gRPC Client
```shell
cd grpc-client
pip install -r requirements.txt
python -m grpc_tools.protoc -I=../proto --python_out=. --grpc_python_out=. ../proto/message.proto
python grpc_client.py
```