package com.demo.sales.endpoint;

import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.demo.sales.dto.CustomerDTO;
import com.demo.sales.exception.BusinessLogicException;
import com.demo.sales.exception.ServiceFaultException;
import com.demo.sales.exception.support.ServiceFault;
import com.demo.sales.services.ISalesService;
import com.demo.sales.util.BasicSupport;
import com.demo.sales.schema.Customer;
import com.demo.sales.schema.Customers;
import com.demo.sales.schema.Firstnames;
import com.demo.sales.schema.GetCustomerRequest;
import com.demo.sales.schema.GetCustomerResponse;
import com.demo.sales.schema.GetCustomersRequest;
import com.demo.sales.schema.GetCustomersResponse;
import com.demo.sales.schema.GetFirstnamesRequest;
import com.demo.sales.schema.GetFirstnamesResponse;
import com.demo.sales.schema.MakePurchaseRequest;
import com.demo.sales.schema.MakePurchaseResponse;

@Endpoint
public class SalesEndpoint {

	private static final String NAMESPACE_URI = "http://demo.com/sales/schema";
	private static final Logger logger = Logger.getLogger(SalesEndpoint.class);
		
	@Autowired
	private ISalesService iSalesService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
	@ResponsePayload
	public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
		
		GetCustomerResponse response = new GetCustomerResponse();
		
		CustomerDTO parameters = new CustomerDTO(request.getId());
		CustomerDTO customerDTO = null;
		
		try {
			customerDTO = iSalesService.getCustomer(parameters);
		} catch (BusinessLogicException e) {
			throw new ServiceFaultException("ERROR", 
					new ServiceFault( e.getCode() , e.getDescription() ));
		}
		
		Customer customer = null;
		
		if(customerDTO != null) {
			customer = new Customer();
			customer.setId(customerDTO.getId());
			customer.setFirstname(customerDTO.getFirstname());
			customer.setLastname(customerDTO.getLastname());
			customer.setStreet(customerDTO.getStreet());
			response.setCustomer(customer);
		}
		
		return response;
	}
	
	@PayloadRoot(namespace= NAMESPACE_URI, localPart = "getFirstnamesRequest")
	@ResponsePayload
	public GetFirstnamesResponse getFirstnames(@RequestPayload GetFirstnamesRequest request) {
		
		GetFirstnamesResponse response = new GetFirstnamesResponse();		
				
		Integer[] ids = 
				request.getIds() == null ? 
						null : request.getIds().getId().toArray(new Integer[request.getIds().getId().size()]);
		
		
		Firstnames firstnames = new Firstnames();
		try {			
			List<String> lstFirstnames = iSalesService.getFirstnames(ids);
			firstnames.getFirstname().addAll(lstFirstnames);
		} catch (BusinessLogicException e) {
			throw new ServiceFaultException("ERROR", 
					new ServiceFault( e.getCode() , e.getDescription() ));
		}
		response.setFirstnames(firstnames);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomersRequest")
	@ResponsePayload
	public GetCustomersResponse getCustomers(@RequestPayload GetCustomersRequest request) {
				
		GetCustomersResponse response = new GetCustomersResponse();
		
		CustomerDTO parameters = new CustomerDTO(request.getFirstname(), request.getLastname());
		List<CustomerDTO> customersDTO;
		try {
			customersDTO = iSalesService.getCustomers(parameters);
		} catch (BusinessLogicException e) {
			throw new ServiceFaultException("ERROR", 
					new ServiceFault( e.getCode() , e.getDescription() ));
		}
		
		Customers customers = new Customers();
		
		if(customersDTO != null) {
			
			customersDTO.forEach( customerDTO ->  {
				Customer customer = new Customer();
				customer.setId(customerDTO.getId());
				customer.setFirstname(customerDTO.getFirstname());
				customer.setLastname(customerDTO.getLastname());
				customer.setStreet(customerDTO.getStreet());
				customers.getCustomer().add(customer);
			});
			
			response.setCustomers(customers);
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "makePurchaseRequest")
	@Namespace(prefix="rn", uri=NAMESPACE_URI)
	@ResponsePayload
	public MakePurchaseResponse makePurchase(@RequestPayload MakePurchaseRequest request, @XPathParam("/rn:makePurchaseRequest/rn:purchase") NodeList purchaseNodeList) {
		
		MakePurchaseResponse response = new MakePurchaseResponse();
		try {
			String purchaseXMLString = BasicSupport.convertNodeListToString(purchaseNodeList);			
			logger.info("purchaseXMLString: " + purchaseXMLString);						
			
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new ServiceFaultException("ERROR", 
					new ServiceFault( "ERROR_CONVERT" , e.getMessage() ));
		}
		
		
		
		/*try {
			customerDTO = iSalesService.getCustomer(parameters);
		} catch (BusinessLogicException e) {
			throw new ServiceFaultException("ERROR", 
					new ServiceFault( e.getCode() , e.getDescription() ));
		}*/
		
		return response;
	}
		
}