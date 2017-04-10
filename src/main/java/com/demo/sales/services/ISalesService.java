package com.demo.sales.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.bean.MessageBean;
import com.demo.sales.exception.BusinessLogicException;

@Service
public interface ISalesService {

	CustomerBean getCustomer(CustomerBean pcustomerDTO) throws BusinessLogicException;

	List<CustomerBean> getCustomers(CustomerBean pcustomerDTO) throws BusinessLogicException;

	List<String> getFirstnames(Integer[] ids) throws BusinessLogicException;

	MessageBean makePurchase(InvoiceBean invoice) throws BusinessLogicException;

	List<InvoiceBean> getInvoiceDetail(InvoiceBean parameters) throws BusinessLogicException;

}
