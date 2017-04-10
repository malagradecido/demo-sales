package com.demo.sales.sp.params;

public class GetInvoiceDetailParams {
	
	private int id;
	private Integer[] ids;

	public int getId() {
		return id;
	}
	
	public GetInvoiceDetailParams() {
		// TODO Auto-generated constructor stub
	}
	

	public GetInvoiceDetailParams(Integer[] ids) {
		super();
		this.ids = ids;
	}

	public GetInvoiceDetailParams(int id) {
		super();
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
}
