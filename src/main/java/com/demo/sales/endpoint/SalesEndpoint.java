package com.demo.sales.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.demo.sales.dto.CustomerDTO;
import com.demo.sales.services.ISalesService;
import com.demo.schema.sales.Customer;
import com.demo.schema.sales.Customers;
import com.demo.schema.sales.GetCustomerRequest;
import com.demo.schema.sales.GetCustomerResponse;
import com.demo.schema.sales.GetCustomersRequest;
import com.demo.schema.sales.GetCustomersResponse;

@Endpoint
public class SalesEndpoint {
private static final String NAMESPACE_URI = "http://demo.com/schema/sales";
		
	@Autowired
	private ISalesService iSalesService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
	@ResponsePayload
	public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
		
		GetCustomerResponse response = new GetCustomerResponse();
		
		CustomerDTO parameters = new CustomerDTO(request.getId());
		CustomerDTO customerDTO = iSalesService.getCustomer(parameters);
		
		Customer customer = new Customer();
		customer.setId(customerDTO.getId());
		customer.setFirstname(customerDTO.getFirstname());
		customer.setLastname(customerDTO.getLastname());
		customer.setStreet(customerDTO.getStreet());
		
		response.setCustomer(customer);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomersRequest")
	@ResponsePayload
	public GetCustomersResponse getCustomers(@RequestPayload GetCustomersRequest request) {
		
		GetCustomersResponse response = new GetCustomersResponse();
		
		CustomerDTO parameters = new CustomerDTO(request.getFirstname(), request.getLastname());
		List<CustomerDTO> customersDTO = iSalesService.getCustomers(parameters);
		
		Customers customers = new Customers();
		customersDTO.forEach( customerDTO ->  {
			Customer customer = new Customer();
			customer.setId(customerDTO.getId());
			customer.setFirstname(customerDTO.getFirstname());
			customer.setLastname(customerDTO.getLastname());
			customer.setStreet(customerDTO.getStreet());
			customers.getCustomer().add(customer);
		});
		
		response.setCustomers(customers);
		
		return response;
	}
}