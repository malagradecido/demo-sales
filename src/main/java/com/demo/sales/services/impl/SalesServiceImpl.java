package com.demo.sales.services.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.sales.bean.CustomerBean;
import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.bean.ItemBean;
import com.demo.sales.bean.MessageBean;
import com.demo.sales.dao.ISalesDAO;
import com.demo.sales.exception.BusinessLogicException;
import com.demo.sales.services.ISalesService;
import com.demo.sales.util.BasicSupport;

@Component
public class SalesServiceImpl implements ISalesService {

	private static final Logger logger = Logger.getLogger(SalesServiceImpl.class);
	
	@Autowired
	ISalesDAO iSalesDAO;
	
	@Override
	public CustomerBean getCustomer(CustomerBean parameters) throws BusinessLogicException {
		
		if(parameters.getId() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"El ID ingresado es incorrecto.");
		}
		
		CustomerBean customer = iSalesDAO.getCustomer(parameters);
		
		if(customer == null) {
			throw new BusinessLogicException(
					"NOT_FOUND", 
					"Cliente con código: " + parameters.getId() + " no encontrado.");
		}
		
		return customer;
	}
	
	@Override
	public List<CustomerBean> getCustomers(CustomerBean parameters) throws BusinessLogicException {
		
		if(parameters.getFirstname().trim().equals("") || parameters.getLastname().trim().equals("") ) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un nombre o apellido.");
		}
		
		List<CustomerBean> customers = iSalesDAO.getCustomers(parameters);
		
		if(customers.size() == 0) {
			throw new BusinessLogicException(
					"NOT_FOUND", 
					"No se encontraron clientes con nombre: <<" + 
					parameters.getFirstname() + 
					">> o con apellido: <<" + parameters.getLastname() + ">>.");
		}
		
		return customers;
	}
	
	@Override
	public List<String> getFirstnames(Integer[] ids) throws BusinessLogicException {
		
		if(ids == null || ids.length == 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un id.");
		}
		
		Integer[] idsNoDuplicate = BasicSupport.deleteDuplicate(ids);
		
		List<String> firstnames = iSalesDAO.getCustomerNamesByIds(idsNoDuplicate);
		
		if(firstnames.size() == 0) {
			throw new BusinessLogicException(
					"NOT_FOUND", 
					"No se encontraron nombres de clientes con ids: " +
					Arrays.toString(ids) + ".");
		}
		
		return firstnames;
	}
	
	@Override
	public MessageBean makePurchase(InvoiceBean parameters) throws BusinessLogicException {
		
		if(parameters.getCustomerId() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar el id de cliente.");
		} else if(parameters.getTotal() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar un total mayor a cero.");
		} else if(parameters.getItems().size() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un item.");
		}
		
		for(ItemBean item : parameters.getItems()) {
			
			if(item.getProductId() <= 0) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"Debe ingresar el id de cliente.");
			} else if(item.getItem() <= 0) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"Debe ingresar un número de item válido.");
			} else if(item.getQuantity() <= 0) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"Debe ingresar una cantidad válida.");
			} else if(item.getCost() <= 0) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"Debe ingresar un costo válido.");
			}
		}
		
		try {
			
			int invoiceId = iSalesDAO.insertInvoice(parameters).getId();
			
			parameters.getItems().forEach( itemDTO -> {
				itemDTO.setInvoiceId( invoiceId );
				iSalesDAO.insertItem(itemDTO);
			});
			
		} catch (Exception e) {
			throw new BusinessLogicException(
					"ERROR_BD", 
					e.getMessage() );
		}

		MessageBean messageDTO = new MessageBean();
		messageDTO.setCode("OK");
		messageDTO.setDescription("Registrado correctamente");
		
		return messageDTO;
	}

	@Override
	public List<InvoiceBean> getInvoiceDetail(Integer[] ids) throws BusinessLogicException {
		
		if(ids == null || ids.length == 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un id.");
		}
		
		Integer[] idsNoDuplicate = BasicSupport.deleteDuplicate(ids);
		
		List<InvoiceBean> invoicesDTO = iSalesDAO.getInvoiceDetail(idsNoDuplicate);
		
		if(invoicesDTO.size() == 0) {
			throw new BusinessLogicException(
					"NOT_FOUND", 
					"No se encontraron facturas con ids: " +
					Arrays.toString(ids) + ".");
		}
		
		return invoicesDTO;
	}

	@Override
	public MessageBean updateCustomers(List<CustomerBean> lstCustomerBean) throws BusinessLogicException {
		
		if(lstCustomerBean == null || lstCustomerBean.size() == 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un id.");
		}
		
		for (CustomerBean customerBean : lstCustomerBean) {
			
			if(customerBean.getId() == 0) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"El id " + customerBean.getId() + " es inválido");
			} else if(customerBean.getFirstname() == null || customerBean.getFirstname().trim().equals("")) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"Debe ingresar un nombre correcto para el id: " + customerBean.getId());
			} else if(customerBean.getLastname() == null || customerBean.getLastname().trim().equals("")) {
				throw new BusinessLogicException(
						"ERROR_PARAMETER", 
						"Debe ingresar un apellido correcto para el id: " + customerBean.getId());
			}
		}
		
		try {
			
			int[][] count = iSalesDAO.batchCustomersUpdate(lstCustomerBean);
			logger.info("count batch update: " + count);
			
		} catch (Exception e) {
			throw new BusinessLogicException(
					"ERROR_BD", 
					e.getMessage() );
		}

		MessageBean messageDTO = new MessageBean();
		messageDTO.setCode("OK");
		messageDTO.setDescription("Información se actualizará progresivamente");
		
		return messageDTO;
	}
	
	@Override
	public MessageBean updateCustomer(CustomerBean parameters) throws BusinessLogicException {
		
		if(parameters.getId() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"El ID ingresado es incorrecto.");
		} else if(parameters.getFirstname() == null || parameters.getFirstname().trim().equals("")) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"El nombre ingresado es incorrecto.");
		}
		
		try {
			
			iSalesDAO.updateCustomer(parameters);
			
		} catch (Exception e) {
			throw new BusinessLogicException(
					"ERROR_BD", 
					e.getMessage() );
		}

		MessageBean messageDTO = new MessageBean();
		messageDTO.setCode("OK");
		messageDTO.setDescription("Actualizado correctamente");
		
		return messageDTO;
	}

}