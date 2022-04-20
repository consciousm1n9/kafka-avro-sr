package com.demo.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.demo.models.User;
import com.demo.models.UserAvro;
import com.demo.util.AvroDeserializer;
import com.demo.util.AvroSerializer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
public class KafkaObjectFactory {

	//KafkaProducer String-JsonSerializer serialization 
	public static KafkaProducer<String, User> getSimpleProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.springframework.kafka.support.serializer.JsonSerializer");
		return new KafkaProducer<>(props);
	}

	//KafkaConsumer String-JsonSerializer deserialization
	public ConsumerFactory<String, User> userConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.demo.models");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,	"org.springframework.kafka.support.serializer.JsonDeserializer");
		return new DefaultKafkaConsumerFactory<>(props);
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}
	
	//KafkaProducer Avro Serialization
	public static KafkaProducer<String, UserAvro> getAvroProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
		return new KafkaProducer<>(props);
	}
	
	//KafkaConsumer Avro deserialization
	public ConsumerFactory<String, UserAvro> userAvroConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new AvroDeserializer<>(UserAvro.class));
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserAvro> useAvrorKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, UserAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userAvroConsumerFactory());
		return factory;
	}
	
	//KafkaProducer Avro SchemaRegistry
	public static KafkaProducer<String, UserAvro> getAvroSchemaProducer() {
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "localhost:29092");
		properties.setProperty("key.serializer", KafkaAvroSerializer.class.getName());
		properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
		properties.setProperty("schema.registry.url", "http://localhost:29096");
		
		return new KafkaProducer<>(properties);
	}
	
	//KafkaConsumer Avro SchemaRegistry
		public ConsumerFactory<String, UserAvro> userAvroSchemaConsumerFactory() {
			Map<String, Object> props = new HashMap<>();
			props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
			props.put("schema.registry.url", "http://localhost:29096");
			props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
			props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
			props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
			return new DefaultKafkaConsumerFactory<>(props);
		}
		@Bean
		public ConcurrentKafkaListenerContainerFactory<String, UserAvro> useAvroSchemaListenerContainerFactory() {
			ConcurrentKafkaListenerContainerFactory<String, UserAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(userAvroSchemaConsumerFactory());
			return factory;
		}
		
}
