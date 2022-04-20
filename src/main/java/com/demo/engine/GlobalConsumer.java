package com.demo.engine;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.demo.models.User;
import com.demo.models.UserAvro;

@Service
public class GlobalConsumer {

    private final Logger logger = LoggerFactory.getLogger(GlobalProducer.class);

    @KafkaListener(topics = "users", groupId = "group_1", containerFactory = "userKafkaListenerContainerFactory")
    public void consume(User message) throws IOException {
    	 logger.info(String.format("#### -> Consumer1 message -> %s", message.toString()));
    }
    
    @KafkaListener(topics = "users_a", groupId = "group_1", containerFactory = "useAvrorKafkaListenerContainerFactory")
    public void consumeAvrp(UserAvro message) throws IOException {
    	 logger.info(String.format("#### -> Consumer1Avro message -> %s", message.toString()));
    }
    
    @KafkaListener(topics = "users_b", groupId = "group_1", containerFactory = "useAvroSchemaListenerContainerFactory")
    public void consumeAvroSchema(UserAvro message) throws IOException {
    	 logger.info(String.format("#### -> Consumer1AvroSchema message -> %s", message.toString()));
    }
    
    @StreamListener(Processor.INPUT)
    public void consumeStreamAvro(UserAvro message) {
    	 logger.info(String.format("#### -> Consumer1StreamAvro message -> %s", message.toString()));
    }
}