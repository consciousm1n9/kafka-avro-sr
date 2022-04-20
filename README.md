# kafka-avro-sr
Based on simplest kafka docker example but now adding schema registry and avro serialization.

This example consists in Kafka and zookeeper docker containers and a producer/consumer springboot proyect for test. A simple object model called "User" is produced/consumed using default String-json serialization.

Instruction:
1. For execute Kafka and zookeeper containers run next command into root path of this repository: 
                    "docker-compose up"
For first time wait for complete download and execution.


2. Open this java proyect with your favorite IDE, execute a 'maven update' for download libraries needed and run application.

3. For produce message to kafka send a post request like this:
     - Example using Kafka + zookeeper (Ser/Des: 'String/Json')  
                  http://localhost:8080/kafka/first?name=Name&age=40
     - Example using Kafka + zookeeper + avro (Ser/Des: 'String/Avro')
                  http://localhost:8080/kafka/second?name=Name&age=40
     - Example using Kafka + zookeeper + avro + schemaregistry
                  http://localhost:8080/kafka/third?name=Name&age=40
     - Example using Default Kafka Stream + zookeeper + avro + schemaregistry
                  http://localhost:8080/kafka/fourth?name=Name&age=40
     - Example using KStream, KTable Kafka Stream + zookeeper + avro + schemaregistry
     ... working
     - Example using Kafka by log4j appender
     ... working

You will see producer and consumer messages in console.
![image](https://user-images.githubusercontent.com/67773113/162332090-faee582f-aa28-4d95-8f0e-9c880abee509.png)

For GUI view you can use Kafka tool\Offset Explorer (https://www.kafkatool.com/download.html) and connect with next configuration:
![image](https://user-images.githubusercontent.com/67773113/162332572-b2e13564-4449-4110-9dfe-33971bc8fd4e.png)

![image](https://user-images.githubusercontent.com/67773113/162332606-263473bb-af89-4166-bd02-6953690cd411.png)

![image](https://user-images.githubusercontent.com/67773113/162332686-c534bc3f-7a6a-4f0c-8097-90681d038c4c.png)

Documentation
- https://www.baeldung.com/ops/kafka-docker-setup
