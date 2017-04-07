package com.demo.sales.exception;

import com.demo.sales.exception.support.ServiceFault;

public class ServiceFaultException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ServiceFault serviceFault;

	public ServiceFaultException(String message, ServiceFault serviceFault) {
		super(message);
		this.serviceFault = serviceFault;
	}

	public ServiceFaultException(String message, Throwable e, ServiceFault serviceFault) {
		super(message, e);
		this.serviceFault = serviceFault;
	}

	public ServiceFault getServiceFault() {
		return serviceFault;
	}

	public void setServiceFault(ServiceFault serviceFault) {
		this.serviceFault = serviceFault;
	}

}
