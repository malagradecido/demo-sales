package com.demo.sales.amqp.receiver;

import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.demo.sales.amqp.exception.InsufficientFundsException;
import com.demo.sales.bean.PaymentOrderBean;
import com.demo.sales.config.RabbitConfig;

@Component
public class DemoReceiver {
	
	private static final Logger log = LoggerFactory.getLogger(DemoReceiver.class);

    public void receiveMessage(String message) {
    	System.out.println("Received <" + message + ">");
    }
    
    @RabbitListener(queues = RabbitConfig.myQueueName)
    public void reciveMessageMyQueue(PaymentOrderBean paymentOrderBean) throws InsufficientFundsException {
    	log.info("Processing at \'{}\' payload \'{}\'", new Date(), paymentOrderBean);

        if (new Random().nextBoolean()) {
            throw new InsufficientFundsException("insufficient funds on account " + paymentOrderBean.getFrom());
        }
    }
    
    @RabbitListener(queues = RabbitConfig.myWorkQueueName)
    public void reciveMessageMyWorkQueue1(String message) throws InterruptedException {
    	StopWatch watch = new StopWatch();
		watch.start();
		log.info("reciveMessageMyWorkQueue1 - Received '" + message + "'");
		doWork(message);
		watch.stop();
		log.info("reciveMessageMyWorkQueue1 - Done in " + watch.getTotalTimeSeconds() + "s");
    }
    
    @RabbitListener(queues = RabbitConfig.myWorkQueueName)
    public void reciveMessageMyWorkQueue2(String message) throws InterruptedException {
    	StopWatch watch = new StopWatch();
		watch.start();
		log.info("reciveMessageMyWorkQueue2 - Received '" + message + "'");
		doWork(message);
		watch.stop();
		log.info("reciveMessageMyWorkQueue2 - Done in " + watch.getTotalTimeSeconds() + "s");
    }
    
    private void doWork(String in) throws InterruptedException {
		for (char ch : in.toCharArray()) {
			if (ch == '.') {
				Thread.sleep(1000);
			}
		}
	}
}