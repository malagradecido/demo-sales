package com.demo.sales.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.sales.dto.CustomerDTO;

@Service
public interface ISalesService {

	CustomerDTO getCustomer(CustomerDTO pcustomerDTO);

	List<CustomerDTO> getCustomers(CustomerDTO pcustomerDTO);

}
