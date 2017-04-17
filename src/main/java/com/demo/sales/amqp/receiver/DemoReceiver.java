package com.demo.sales.amqp.receiver;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

@Component
public class DemoReceiver {
	
	private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
    	try {
			Thread.sleep(10000);
			System.out.println("Received <" + message + ">");
	        latch.countDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}