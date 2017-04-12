package com.demo.sales.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.bean.MessageBean;
import com.demo.sales.exception.BusinessLogicException;
import com.demo.sales.exception.support.ErrorRest;
import com.demo.sales.services.ISalesService;

@RestController
@RequestMapping("/sales")
public class SalesRestEndpoint {

	private static final Logger logger = Logger.getLogger(SalesRestEndpoint.class);
	
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
    
    @PutMapping("/customer/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Integer customerId, RequestEntity<CustomerBean> reqCustomerBean) { 
    	
    	try {
    		
    		CustomerBean customerBeanUpdate = reqCustomerBean.getBody();
    		
    		CustomerBean customerBean = iSalesService.getCustomer(new CustomerBean(customerId));
    		customerBean.setFirstname(customerBeanUpdate.getFirstname());
    		
    		MessageBean messageBean = iSalesService.updateCustomer(customerBean);
    		
    		return new ResponseEntity<MessageBean>(messageBean, HttpStatus.OK);
    		
		} catch (BusinessLogicException e) {
			
			return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()),
                    HttpStatus.NOT_FOUND);
		}
    }
    
    @PutMapping("/customers")
    public ResponseEntity<?> updateCustomers(RequestEntity<List<CustomerBean>> reqLstCustomerBean) { 
    	
    	try {
    		
    		List<CustomerBean> lstCustomerBeanUpdate = reqLstCustomerBean.getBody();
    		
    		MessageBean messageBean = iSalesService.updateCustomers(lstCustomerBeanUpdate);
    		
    		return new ResponseEntity<MessageBean>(messageBean, HttpStatus.OK);
    		
		} catch (BusinessLogicException e) {
			
			return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()),
                    HttpStatus.NOT_FOUND);
		}
    }
    
    @PostMapping("/invoice")
    public ResponseEntity<?> createInvoice(RequestEntity<InvoiceBean> reqInvoiceBean) {
    	
    	try {
    		
    		InvoiceBean invoiceBean = reqInvoiceBean.getBody();
    		MessageBean messageBean = iSalesService.makePurchase(invoiceBean);
    		
    		return new ResponseEntity<MessageBean>(messageBean, HttpStatus.CREATED);
    		
		} catch (BusinessLogicException e) {
			
			return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()),
                    HttpStatus.CONFLICT);
		}
    }
}
