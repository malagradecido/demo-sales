package com.demo.sales.dto;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDTO {

	private Integer id; 
	private Integer customerId;
	private Double total;
	private List<ItemDTO> items;
	
	public InvoiceDTO() {
	}
	
	public InvoiceDTO(Integer id, Integer customerId, Double total, List<ItemDTO> items) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.total = total;
		this.items = items;
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

	public List<ItemDTO> getItems() {
		if(items == null)
			items = new ArrayList<>();
		
		return items;
	}

	public void setItems(List<ItemDTO> items) {
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
		InvoiceDTO other = (InvoiceDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
