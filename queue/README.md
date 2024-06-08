## Run instructions

### 1. Start RabbitMQ
```shell
brew services start rabbitmq 
```

### 2. Start gRPC Server (Producer)
```shell
cd ../encoding/grpc-server
mvn clean install
java -jar target/grpc-server-1.0-SNAPSHOT.jar 
```

### 3. Start Consumer
```shell
pip install -r requirements.txt
python consumer.py
```

### 4. Run gRPC Client
```shell
cd ../encoding/grpc-client
pip install -r requirements.txt
python -m grpc_tools.protoc -I=../proto --python_out=. --grpc_python_out=. ../proto/message.proto
python grpc_client.py --queue=True
```
