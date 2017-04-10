package com.demo.sales.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.sales.dao.ISalesDAO;
import com.demo.sales.dto.CustomerDTO;
import com.demo.sales.dto.InvoiceDTO;
import com.demo.sales.dto.ItemDTO;
import com.demo.sales.dto.MessageDTO;
import com.demo.sales.exception.BusinessLogicException;
import com.demo.sales.services.ISalesService;
import com.demo.sales.util.BasicSupport;

@Component
public class SalesServiceImpl implements ISalesService {

	@Autowired
	ISalesDAO iSalesDAO;
	
	@Override
	public CustomerDTO getCustomer(CustomerDTO objParameter) throws BusinessLogicException {
		
		if(objParameter.getId() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"El ID ingresado es incorrecto.");
		}
		
		CustomerDTO customer = iSalesDAO.getCustomer(objParameter);
		
		if(customer == null) {
			throw new BusinessLogicException(
					"NOT_FOUND", 
					"Cliente con código: " + objParameter.getId() + " no encontrado.");
		}
		
		return customer;
	}
	
	@Override
	public List<CustomerDTO> getCustomers(CustomerDTO objParameter) throws BusinessLogicException {
		
		if(objParameter.getFirstname().trim().equals("") || objParameter.getLastname().trim().equals("") ) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un nombre o apellido.");
		}
		
		List<CustomerDTO> customers = iSalesDAO.getCustomers(objParameter);
		
		if(customers.size() == 0) {
			throw new BusinessLogicException(
					"NOT_FOUND", 
					"No se encontraron clientes con nombre: <<" + 
					objParameter.getFirstname() + 
					">> o con apellido: <<" + objParameter.getLastname() + ">>.");
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
	public MessageDTO makePurchase(InvoiceDTO invoiceDTO) throws BusinessLogicException {
		
		if(invoiceDTO.getCustomerId() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar el id de cliente.");
		} else if(invoiceDTO.getTotal() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar un total mayor a cero.");
		} else if(invoiceDTO.getItems().size() <= 0) {
			throw new BusinessLogicException(
					"ERROR_PARAMETER", 
					"Debe ingresar al menos un item.");
		}
		
		for(ItemDTO item : invoiceDTO.getItems()) {
			
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
			
			int invoiceId = iSalesDAO.insertInvoice(invoiceDTO).getId();
			
			invoiceDTO.getItems().forEach( itemDTO -> {
				itemDTO.setInvoiceId( invoiceId );
				iSalesDAO.insertItem(itemDTO);
			});
			
		} catch (Exception e) {
			throw new BusinessLogicException(
					"ERROR_BD", 
					e.getMessage() );
		}

		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setCode("OK");
		messageDTO.setDescription("Registrado correctamente");
		
		return messageDTO;
	}

}