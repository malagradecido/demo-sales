package com.demo.sales.sp;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.demo.sales.sp.params.GetInvoiceDetailParams;
import com.demo.sales.sp.rs.GetInvoiceDetailRs;

public class SpGetInvoiceDetail extends StoredProcedure {

	private static final String SQL = "SP_GET_INVOICE_DETAIL";
	
	public SpGetInvoiceDetail(DataSource dataSource) {
		setDataSource(dataSource);
		setFunction(true);
		setSql(SQL);
		declareParameter(new SqlOutParameter("id", Types.INTEGER));
		compile();
	}
	
	public List<GetInvoiceDetailRs> execute(GetInvoiceDetailParams parameters) {
		Map<String, Object> parametersMap = new HashMap<String, Object>(1);
		parametersMap.put("id", parameters.getId());
		Map<String, Object> results = execute(parametersMap);
		@SuppressWarnings("unchecked")
		List<GetInvoiceDetailRs> list = ((ArrayList<GetInvoiceDetailRs>) results.get("#result-set-1"));
		return list;
	}
	
}
