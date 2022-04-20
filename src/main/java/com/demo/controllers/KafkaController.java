package com.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.engine.GlobalProducer;
import com.demo.models.User;
import com.demo.models.UserAvro;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final GlobalProducer sproducer;

    @Autowired
    KafkaController(GlobalProducer producer) {
        this.sproducer = producer;
    }

    //Kafka + zookeeper (Ser/Des: 'String/Json')  
    @PostMapping(value = "/first")
    public void firstExample(@RequestParam("name") String name, @RequestParam("age") int age) {
    	User message = new User(name, age);
        this.sproducer.sendMessage(message);
    }
    
    //Kafka + zookeeper + avro (Ser/Des: 'String/Avro')
    @PostMapping(value = "/second")
    public void secondExample(@RequestParam("name") String name, @RequestParam("age") int age) {
    	UserAvro message = new UserAvro(name, age);
        this.sproducer.sendAvroMessage(message);
    }
    
    //Kafka + zookeeper + avro + schemaregistry
    @PostMapping(value = "/third")
    public void thirdExample(@RequestParam("name") String name, @RequestParam("age") int age) {
    	UserAvro message = UserAvro.newBuilder()
    			.setName(name)
    			.setAge(age)
    			.build();
        this.sproducer.sendAvroSchemaMessage(message);
    }
    
    //Kafka Stream + zookeeper + avro + schemaregistry
    @PostMapping(value = "/fourth")
    public void fourthExample(@RequestParam("name") String name, @RequestParam("age") int age) {
    	UserAvro message = UserAvro.newBuilder()
    			.setName(name)
    			.setAge(age)
    			.build();
        this.sproducer.sendKafkaStreamMessage(message);
    }
}