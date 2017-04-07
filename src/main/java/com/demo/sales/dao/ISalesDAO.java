package com.demo.sales.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.sales.dto.CustomerDTO;

@Repository
public interface ISalesDAO {

	CustomerDTO getCustomer(CustomerDTO customerDTO);

	List<CustomerDTO> getCustomers(CustomerDTO customerDTO);

	List<String> getCustomerNamesByIds(Integer[] ids);
}