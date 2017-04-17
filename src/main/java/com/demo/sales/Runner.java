package com.demo.sales;

import org.springframework.boot.CommandLineRunner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.demo.sales.amqp.receiver.DemoReceiver;
import com.demo.sales.config.RabbitConfig;

@Component
public class Runner implements CommandLineRunner {
	
	private static final Logger logger = Logger.getLogger(Runner.class);
	
	private final RabbitTemplate rabbitTemplate;
    private final DemoReceiver receiver;
//    private final ConfigurableApplicationContext context;

    public Runner(DemoReceiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
//        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        receiver.getLatch().await(10, TimeUnit.SECONDS);
        callQueue("Hola soy Moises!"); 
        callQueue("Hola soy Carlos!");
        callQueue("Hola soy Sebastian!");
        callQueue("Hola soy Mary!");
//        context.close();
    }
    
    public void callQueue(String message) {
    	rabbitTemplate.convertAndSend(RabbitConfig.queueName, message);
    }
    
    @RabbitListener(queues = RabbitConfig.queueName)
    public void processCall(String data) {
    	logger.info("finish process --> " + data);
    }

}