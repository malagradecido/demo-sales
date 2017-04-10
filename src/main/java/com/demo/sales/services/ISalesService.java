package com.demo.sales.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.sales.dto.CustomerDTO;
import com.demo.sales.dto.InvoiceDTO;
import com.demo.sales.dto.MessageDTO;
import com.demo.sales.exception.BusinessLogicException;

@Service
public interface ISalesService {

	CustomerDTO getCustomer(CustomerDTO pcustomerDTO) throws BusinessLogicException;

	List<CustomerDTO> getCustomers(CustomerDTO pcustomerDTO) throws BusinessLogicException;

	List<String> getFirstnames(Integer[] ids) throws BusinessLogicException;

	MessageDTO makePurchase(InvoiceDTO invoice) throws BusinessLogicException;

}
