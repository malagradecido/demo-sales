package com.demo.sales.scheduled.tasks;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.iban4j.Iban;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.sales.bean.PaymentOrderBean;
import com.demo.sales.config.RabbitConfig;

@SuppressWarnings("unused")
@Component
public class SalesScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(SalesScheduledTasks.class);
	private static final Random random = new Random();
	@Autowired
	private RabbitTemplate rabbitTemplate;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
//    @Scheduled(cron="*/5 * * * * MON-FRI")
    public void reportPartTime() {
    	log.info("The time is part-time {}", dateFormat.format(new Date()));
    }
    
//    @Scheduled(fixedRate=1000)
//    public void sendMyQueue() {
//    	String mensaje = "Mi cola simple!";
//    	log.info("Enviando mensaje a cola simple... ");
//    	rabbitTemplate.convertAndSend(
//    			RabbitConfig.myQueue, 
//    			(mensaje + " -> " + dateFormat.format(new Date())) );
//    }
    
//    @Scheduled(fixedRate=1000)
    public void sendMyQueue() {
    	PaymentOrderBean paymentOrder = new PaymentOrderBean(
                Iban.random().toFormattedString(),
                Iban.random().toFormattedString(),
                new BigDecimal(1D + random.nextDouble() * 100D).setScale(2, BigDecimal.ROUND_FLOOR));

        log.info("Sending payload \'{}\'", paymentOrder);
    	rabbitTemplate.convertAndSend(
    			RabbitConfig.myQueue, 
    			paymentOrder );
    }
    
    int dots = 0;
	int count = 0;
    
//	@Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void sendMyWorkQueue1() {
    	
    	StringBuilder builder = new StringBuilder("Hello");
		if (dots++ == 3) {
			dots = 1;
		}
		for (int i = 0; i < dots; i++) {
			builder.append('.');
		}
		builder.append(Integer.toString(++count));
		String message = builder.toString();
		rabbitTemplate.convertAndSend("my-work-queue-exchange", "my-work-queue" , message);
		log.info(" [x] Sent '" + message + "'");
    }
    
    @Autowired
    private FanoutExchange fanout;

//    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots++ == 3) {
            dots = 1;
        }
        for (int i = 0; i < dots; i++) {
            builder.append('.');
        }
        builder.append(Integer.toString(++count));
        String message = builder.toString();
        rabbitTemplate.convertAndSend(fanout.getName(), "", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
    
    @Autowired
    private DirectExchange direct;

    private int index;

    private final String[] keys = {"orange", "black", "green"};

//    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send2() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (++this.index == 3) {
            this.index = 0;
        }
        String key = keys[this.index];
        builder.append(key).append(' ');
        builder.append(Integer.toString(++this.count));
        String message = builder.toString();
        rabbitTemplate.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
    
}