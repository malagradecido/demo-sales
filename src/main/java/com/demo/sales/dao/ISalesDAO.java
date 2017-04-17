package com.demo.sales.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.bean.ItemBean;

@Repository
public interface ISalesDAO {

	CustomerBean getCustomer(CustomerBean customerDTO);

	List<CustomerBean> getCustomers(CustomerBean customerDTO);

	List<String> getCustomerNamesByIds(Integer[] ids);

	void insertItem(ItemBean itemDTO);

	InvoiceBean insertInvoice(InvoiceBean invoiceDTO);

	List<InvoiceBean> getInvoiceDetail(Integer[] ids);

	int[][] batchCustomersUpdate(Collection<CustomerBean> customerBean);

	void updateCustomer(CustomerBean customerBean);
}