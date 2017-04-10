package com.demo.sales.sp.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.bean.ItemBean;

public class GetInvoiceDetailExtractor implements ResultSetExtractor<List<InvoiceBean>>{

	@Override
	public List<InvoiceBean> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<InvoiceBean> invoicesDTO = new ArrayList<>();
		List<ItemBean> itemsDTO = new ArrayList<ItemBean>();
		
		while(rs.next()) {
		
			ItemBean newItemDTO = new ItemBean(
					rs.getInt("item"), rs.getInt("productId"), 
					rs.getString("productName"), rs.getInt("quantity"), 
					rs.getDouble("cost"));
			
			InvoiceBean newInvoiceDTO = new InvoiceBean(
					rs.getInt("id"), rs.getInt("customerId"), 
					rs.getString("customerName"), rs.getDouble("total"));
			
			if(invoicesDTO.contains(newInvoiceDTO)) {
				
				//Se agrega item a items
				itemsDTO.add(newItemDTO);
				
			} else {
				
				//Es un nuevo invoice
				
				if( !invoicesDTO.isEmpty() ) {
					
					//Ya existen invoice en la lista de invoices, entonces se tiene que agregar lista de items a invoice anterior					
					invoicesDTO.get( invoicesDTO.size() - 1  ).setItems( itemsDTO );
					
					//Se limpia lista de items
					itemsDTO.clear();
				}
				
				//Se agrega nuevo item a lista de items
				itemsDTO.add(newItemDTO);
				
				invoicesDTO.add(newInvoiceDTO);
			}
			
		}
						
		//Se agrega ultima lista de items a ultima lista de invoice agregada
		if( !invoicesDTO.isEmpty() ) {
			
			invoicesDTO.get( invoicesDTO.size() - 1  ).setItems( itemsDTO );
		}
		
		return invoicesDTO;
	}

	

}
