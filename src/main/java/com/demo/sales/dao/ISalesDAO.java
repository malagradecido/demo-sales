package com.demo.sales.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.sales.dto.CustomerDTO;
import com.demo.sales.dto.InvoiceDTO;
import com.demo.sales.dto.ItemDTO;

@Repository
public interface ISalesDAO {

	CustomerDTO getCustomer(CustomerDTO customerDTO);

	List<CustomerDTO> getCustomers(CustomerDTO customerDTO);

	List<String> getCustomerNamesByIds(Integer[] ids);

	void insertItem(ItemDTO itemDTO);

	InvoiceDTO insertInvoice(InvoiceDTO invoiceDTO);
}