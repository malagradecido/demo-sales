package com.demo.sales.amqp.exception;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;

public class InsufficientFundsException extends AmqpRejectAndDontRequeueException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
        super(message);
    }
}
