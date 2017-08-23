package com.demo.sales.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentOrderBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String from;
    String to;
    BigDecimal amount;
    
    

    public PaymentOrderBean() {
	}

	public PaymentOrderBean(String from,
                        String to,
                        BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
	@Override
    public String toString() {
        return "PaymentOrder{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }

}
