package com.demo.sales.sp;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

import com.demo.sales.bean.InvoiceBean;
import com.demo.sales.sp.extractor.GetInvoiceDetailExtractor;
import com.demo.sales.sp.params.GetInvoiceDetailParams;

public class SpGetInvoiceDetail extends StoredProcedure {

	private static final String SQL = "SP_GET_INVOICE_DETAIL";
	
	public SpGetInvoiceDetail(DataSource dataSource) {
		setDataSource(dataSource);
		setSql(SQL);
		declareParameter(new SqlParameter("ids", Types.ARRAY));
		declareParameter(new SqlReturnResultSet("result-set-1", new GetInvoiceDetailExtractor()));
		compile();
	}
	
	public List<InvoiceBean> execute(GetInvoiceDetailParams parameters) {
		Map<String, Object> parametersMap = new HashMap<String, Object>(1);
		parametersMap.put("ids", parameters.getIds());
		Map<String, Object> results = execute(parametersMap);
		@SuppressWarnings("unchecked")
		List<InvoiceBean> list = 
			((ArrayList<InvoiceBean>) results.get("result-set-1"));
		
		return list;
	}
	
}
