package com.demo.sales.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.sales.dao.ISalesDAO;
import com.demo.sales.dto.CustomerDTO;
import com.demo.sales.services.ISalesService;

@Component
public class SalesServiceImpl implements ISalesService {

	@Autowired
	ISalesDAO iSalesDAO;
	
	@Override
	public CustomerDTO getCustomer(CustomerDTO pcustomerDTO) {
		CustomerDTO customer = null;
		
		if(pcustomerDTO.getId() > 0)
			customer = iSalesDAO.getCustomer(pcustomerDTO);
		
		return customer;
	}
	
	@Override
	public List<CustomerDTO> getCustomers(CustomerDTO pcustomerDTO) {
		
		List<CustomerDTO> customers = iSalesDAO.getCustomers(pcustomerDTO);
		
		return customers;
	}
	
}