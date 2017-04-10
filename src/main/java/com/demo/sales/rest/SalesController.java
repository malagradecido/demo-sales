package com.demo.sales.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.endpoint.SalesEndpoint;
import com.demo.sales.exception.BusinessLogicException;
import com.demo.sales.services.ISalesService;

@RestController
@RequestMapping("/sales")
public class SalesController {

	private static final Logger logger = Logger.getLogger(SalesEndpoint.class);
	
	@Autowired
	private ISalesService iSalesService;

    @GetMapping("/customer/{customerId}")
    public CustomerBean getCustomer(@PathVariable Integer customerId) {
        
    	CustomerBean customerBean = null; 
    	
    	try {
    		CustomerBean parameters = new CustomerBean(customerId);
    		customerBean = iSalesService.getCustomer(parameters);
    		logger.info("customerBean: " + customerBean);
		} catch (BusinessLogicException e) {
			e.printStackTrace();
		}
    	
    	return customerBean;
    }
}
