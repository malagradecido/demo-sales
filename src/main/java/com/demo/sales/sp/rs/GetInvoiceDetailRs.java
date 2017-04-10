package com.demo.sales.sp.rs;

public class GetInvoiceDetailRs {
	
	private int id;
	private int customerId;
	private String customerName;
	private double total;
	private int item;
	private int productId;
	private String productName;
	private int quantity;
	private Double cost;
	
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public GetInvoiceDetailRs() {
	}
	
	public GetInvoiceDetailRs(int id, int customerId, String customerName, double total, int item, int productId,
			String productName, int quantity, double cost) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.total = total;
		this.item = item;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.cost = cost;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
