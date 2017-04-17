package com.demo.sales.bean;

import java.util.ArrayList;
import java.util.List;

public class InvoiceBean {

	private Integer id; 
	private Integer customerId;
	private String customerName;
	private Double total;
	private List<ItemBean> items;
	//private Integer[] ids;
	
	/*public InvoiceBean(Integer[] ids) {
		super();
		this.ids = ids;
	}*/

	public InvoiceBean() {
	}
	
	public InvoiceBean(Integer id, Integer customerId, String customerName, Double total, List<ItemBean> items) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.total = total;
		this.items = items;
	}
	
	public InvoiceBean(Integer id, Integer customerId, Double total, List<ItemBean> items) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.total = total;
		this.items = items;
	}
	
	public InvoiceBean(Integer id, Integer customerId, String customerName, Double total) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.customerName = customerName;
		this.total = total;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	public List<ItemBean> getItems() {
		if(items == null)
			items = new ArrayList<>();
		
		return items;
	}

	public void setItems(List<ItemBean> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceBean other = (InvoiceBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}*/

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
