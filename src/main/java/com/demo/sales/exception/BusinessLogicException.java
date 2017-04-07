package com.demo.sales.exception;

public class BusinessLogicException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String description;

	public BusinessLogicException() {
	}
	
	public BusinessLogicException(String code, String description) {
		super(description);
		this.code = code;
		this.description = description;
	}
	
	public BusinessLogicException(String message) {
		super(message);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
