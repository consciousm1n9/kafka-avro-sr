package com.demo.engine;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.demo.models.User;
import com.demo.models.UserAvro;

@Service
public class GlobalProducer {
	
	@Autowired
	private Processor processor;

    private static final Logger logger = LoggerFactory.getLogger(GlobalProducer.class);
    private static final String TOPIC = "users";
    private static final String TOPIC_A = "users_a";
    private static final String TOPIC_B = "users_b";

	public void sendMessage(User message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        KafkaObjectFactory.getSimpleProducer().send(new ProducerRecord<String, User>(TOPIC, message));
    }
	
	public void sendAvroMessage(UserAvro message) {
		logger.info(String.format("#### -> Producing avro message -> %s", message));
		KafkaObjectFactory.getAvroProducer().send(new ProducerRecord<String, UserAvro>(TOPIC_A, message));
	}
	
	public void sendAvroSchemaMessage(UserAvro message) {
		logger.info(String.format("#### -> Producing AvroSchema message -> %s", message));
		KafkaObjectFactory.getAvroSchemaProducer().send(new ProducerRecord<String, UserAvro>(TOPIC_B, message));
	}
	
	public void sendKafkaStreamMessage(UserAvro message) {
		logger.info(String.format("#### -> Producing StreamAvro message -> %s", message));
		Message<UserAvro> msg = MessageBuilder.withPayload(message).build();
		processor.output().send(msg);
	}
}