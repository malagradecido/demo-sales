package com.demo.sales.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.endpoint.SalesEndpoint;
import com.demo.sales.exception.BusinessLogicException;
import com.demo.sales.exception.support.ErrorRest;
import com.demo.sales.services.ISalesService;

@RestController
@RequestMapping("/sales")
public class SalesController {

	private static final Logger logger = Logger.getLogger(SalesEndpoint.class);
	
	@Autowired
	private ISalesService iSalesService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer customerId) { 
    	
    	try {
    		
    		CustomerBean parameters = new CustomerBean(customerId);
    		CustomerBean customerBean = iSalesService.getCustomer(parameters);
    		logger.info("customerBean: " + customerBean);
    		
    		return new ResponseEntity<CustomerBean>(customerBean, HttpStatus.OK);
    		
		} catch (BusinessLogicException e) {
			
			return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()),
                    HttpStatus.NOT_FOUND);
		}
    }
}
