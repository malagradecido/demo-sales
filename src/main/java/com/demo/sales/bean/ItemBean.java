package com.demo.sales.bean;

/**
 * @author moises.veliz
 *
 */
public class ItemBean {
	
	private Integer invoiceId;
	private Integer item;
	private Integer productId;
	private String productName;
	private Integer quantity;
	private Double cost;
	
	public ItemBean() {
	}
	
	public ItemBean(Integer item, Integer productId, String productName, Integer quantity, Double cost) {
		super();
		this.item = item;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.cost = cost;
	}
	
	public ItemBean(Integer item, Integer productId, Integer quantity, Double cost) {
		super();
		this.item = item;
		this.productId = productId;
		this.quantity = quantity;
		this.cost = cost;
	}
	
	public Integer getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Integer getItem() {
		return item;
	}
	public void setItem(Integer item) {
		this.item = item;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
